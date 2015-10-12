@ECHO off

:: ********************************Please set the following configuration Path************************
:: NOTE: DO NOT change variable names
:: PROPS_HOME and CONFIGS_DELIVERY_HOME get from environment variable
:: Following value of variables will get from jenkins
:: Jenkins will set variables if this batch is running in a Jenkins job
:: SET INITIAL_GIT_BRANCH=
:: SET INITIAL_PROJECT_VERSION=
:: SET CONFIG_NAME=
:: SET JOB_NAME=

:: SET FTPS_SERVER_HOST=
:: SET FTPS_SERVER_DESTINATION
:: SET TRANSFER_KEY=
:: SET CIPHER_USERNAME=
:: SET CIPHER_PASSWORD=
:: ***************************************************************************************************

:: Get running branch name
CALL :GETBRANCH_NAME

:: Declare variables start
:: Set configuration path for target config
SET target_config_path=%PROPS_HOME%\%branch_name%\%INITIAL_PROJECT_VERSION%
:: Set configuration path for destination
SET destination=%CONFIGS_DELIVERY_HOME%\%JOB_NAME%

:: Set log name for configurations uploading information
SET log_file_name=pcm_ftps_%date:~4,2%-%date:~7,2%-%date:~10,4%
SET log_file_save_path=%destination%\%log_file_name%
:: Declare variables end

:: Start running script
CALL :COPY_CONFIG_TO_DESTINATION
CALL :DECRYPT_ACCOUNT_AUTHENTICATION
CALL :UPLOAD_CONFIGS_TO_DESTINATION
CALL :CHECK_UPLOADING_STATUS
EXIT %ERRORLEVEL%

:: Declare methods start
:DELAY_EXECUTION_IN_MILLISECONDS
  PING 1.1.1.1 -n 1 -w 100 >NUL
  GOTO :EOF
  
:GETBRANCH_NAME
  IF DEFINED INITIAL_GIT_BRANCH (
 	  ECHO INITIAL_GIT_BRANCH is defined 
 	  SET branch_name=%INITIAL_GIT_BRANCH%
  ) ELSE (
 	  ECHO INITIAL_GIT_BRANCH is NOT defined
 	  FOR /f %%i IN ('"git rev-parse --abbrev-ref HEAD"') DO SET branch_name=%%i
  )
  GOTO :EOF 
  
:COPY_CONFIG_TO_DESTINATION
  ::SET specifies_the_file=/COPYALL /B /SEC /MIR *.properties
  SET specifies_the_file=/MIR /XX %CONFIG_NAME%.properties
  SET copy_options=/R:3 /W:5 /LOG:%destination%\pcm_movingStatus.log /NS /NC /NDL
  ROBOCOPY %target_config_path% %destination% %specifies_the_file% %copy_options% >NUL
  SET/A errlev="%ERRORLEVEL% & 24"
  IF %errlev% NEQ 0 (
	  ECHO Delivery %CONFIG_NAME% Failed!
	  EXIT %errlev%
  )
  EXIT/B %errlev%
  
:DECRYPT_ACCOUNT_AUTHENTICATION
  FOR /F %%i IN ('"ECHO %CIPHER_USERNAME% | openssl enc -d -aes-256-cbc -a -salt -pass pass:%TRANSFER_KEY%"') DO SET plain_username=%%i
  FOR /F %%i IN ('"ECHO %CIPHER_PASSWORD% | openssl enc -d -aes-256-cbc -a -salt -pass pass:%TRANSFER_KEY%"') DO SET plain_password=%%i
  GOTO :EOF

:UPLOAD_CONFIGS_TO_DESTINATION
  CALL :DELAY_EXECUTION_IN_MILLISECONDS
  ::SET specifies_the_file=/COPYALL /B /SEC /MIR *.properties
  SET specifies_the_file=-T %destination%\%CONFIG_NAME%.properties
  SET upload_options=-k --ftp-create-dirs --trace-ascii %log_file_save_path%.log -u %plain_username%:%plain_password%
  CURL %specifies_the_file% %upload_options% %FTPS_SERVER_HOST%/%FTPS_SERVER_DESTINATION%/
  GOTO :EOF
  
:CHECK_UPLOADING_STATUS
  FOR /f %%f IN ('findstr /c:"We are completely uploaded and fine" "%log_file_save_path%.log"') DO SET/a totalSuccess+=1
  ECHO The number of successfully uploading is: %totalSuccess% > %destination%\pcm_uploadingStatus_%log_file_name%.log
  ECHO CURL ERROR CODE: %ERRORLEVEL% >>%destination%\pcm_uploadingStatus_%log_file_name%.log
  GOTO :EOF
:: Declare methods end