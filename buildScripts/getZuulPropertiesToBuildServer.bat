@ECHO off

:: ********************************Please pay attention for following configurations******************
:: NOTE: DO NOT change variable names
:: Jenkins will set PROPS_HOME environment variable if this batch is running in a Jenkins job
:: SET INITIAL_GIT_BRANCH=
:: INITIAL_PROJECT_VERSION get from jenkins
:: SET INITIAL_PROJECT_VERSION=
:: ***************************************************************************************************

:: Get running branch name
CALL :GETBRANCH_NAME
:: Declare variables start
:: Set constant part of Zuul URL
SET zuul_url=http://bhitsbuild03:8080/zuul/settings
:: Set working environment of each configurations
SET environment_name=dev,qa,prod
:: Set each property name
SET property_name=pcm-config
:: Set log name for Zuul downloading information
SET log_file_name=%branch_name%_pcm_zuul_%date:~4,2%-%date:~7,2%-%date:~10,4%
:: Set save path for all properties downloading from Zuul
SET properties_save_path=%PROPS_HOME%\%branch_name%\%INITIAL_PROJECT_VERSION%

SET log_file_save_path=%properties_save_path%\%log_file_name%
:: Declare variables end

:: Start running script
2>NUL CALL :DOWNLOAD_%branch_name% 
IF ERRORLEVEL 1 CALL :DEFAULT_CASE

CALL :VERIFYZUUL
ECHO Done.
EXIT

:: Declare methods start

:: Get current git branch name
:: Jenkins will set INITIAL_GIT_BRANCH environment variable if this batch is running in a Jenkins job
:GETBRANCH_NAME
  IF DEFINED INITIAL_GIT_BRANCH (
 	  ECHO INITIAL_GIT_BRANCH is defined 
 	  SET branch_name=%INITIAL_GIT_BRANCH%
  ) ELSE (
 	  ECHO INITIAL_GIT_BRANCH is NOT defined
 	  FOR /f %%i IN ('"git rev-parse --abbrev-ref HEAD"') DO SET branch_name=%%i
  )
  GOTO END_CASE
  
:CREATE_SAVE_PATH
  IF NOT EXIST "%properties_save_path%" MKDIR %properties_save_path%
  GOTO END_CASE

:CREATE_ALL_SAVE_PATH
  SET dev_properties_save_path=%PROPS_HOME%\dev\%INITIAL_PROJECT_VERSION%
  SET qa_properties_save_path=%PROPS_HOME%\qa\%INITIAL_PROJECT_VERSION%
  SET prod_properties_save_path=%PROPS_HOME%\prod\%INITIAL_PROJECT_VERSION%
  
  FOR %%n IN (%dev_properties_save_path% %qa_properties_save_path% %prod_properties_save_path%) do (
	IF NOT EXIST %%n (
	MD %%n
	)
  )
  GOTO END_CASE
:: Download all dev properties from Zuul  
:DOWNLOAD_Dev
  :: Create save path if it is not existing.
  CALL :CREATE_SAVE_PATH
  SET url=%zuul_url%/dev/{%property_name%}.properties
  :: --trace-ascii trace all downloading status and generate a log
  :: -s doing with silence way, do not print in console
  curl -k --trace-ascii %log_file_save_path%.log -s -o %properties_save_path%\#1.properties %url%
  GOTO END_CASE
:: Download all qa properties from Zuul 
:DOWNLOAD_Qa
  :: Create save path if it is not existing.
  CALL :CREATE_SAVE_PATH
  SET url=%zuul_url%/qa/{%property_name%}.properties
  curl -k --trace-ascii %log_file_save_path%.log -s -o %properties_save_path%\#1.properties %url%
  GOTO END_CASE 
:: Download all prod properties from Zuul 
:DOWNLOAD_Prod
  :: Create save path if it is not existing.
  CALL :CREATE_SAVE_PATH
  SET url=%zuul_url%/prod/{%property_name%}.properties
  curl -k --trace-ascii %log_file_save_path%.log -s -o %properties_save_path%\#1.properties %url%
  GOTO END_CASE
:: Download all properties from Zuul   
:DOWNLOAD_All
  CALL :CREATE_ALL_SAVE_PATH
  SET url=%zuul_url%/{%environment_name%}/{%property_name%}.properties
  SET all_log_file_save_path=%PROPS_HOME%\%log_file_name%
  curl -k --trace-ascii %all_log_file_save_path%.log -s -o %PROPS_HOME%\#1\%INITIAL_PROJECT_VERSION%\#2.properties %url%
  CALL :VERIFYZUUL_All
  GOTO END_CASE
  
:VERIFYZUUL
  FOR /f %%f IN ('findstr /c:"Send header" "%log_file_save_path%.log"') DO SET/a totalDownloads+=1
  FOR /f %%f IN ('findstr /c:"HTTP/1.1 200" "%log_file_save_path%.log"') DO SET/a totalSuccess+=1
  (ECHO The number of download files is: %totalDownloads% && ECHO The number of successfully download is: %totalSuccess%) > %properties_save_path%\%branch_name%_pcm_verifyZuul.log
  IF (%totalDownloads%) NEQ (%totalSuccess%) (
      CALL :REMOVE-PROPERTIES-VERIFY-FAILED
	  EXIT 2
  )
  IF %totalDownloads% EQU 0 (
	  EXIT 3
  )
  GOTO END_CASE
  
:VERIFYZUUL_All
  FOR /f %%f IN ('findstr /c:"Send header" "%all_log_file_save_path%.log"') DO SET/a totalDownloads+=1
  FOR /f %%f IN ('findstr /c:"HTTP/1.1 200" "%all_log_file_save_path%.log"') DO SET/a totalSuccess+=1
  (ECHO The number of download files is: %totalDownloads% && ECHO The number of successfully download is: %totalSuccess%) > %PROPS_HOME%\all_verifyZuul.log
  IF (%totalDownloads%) NEQ (%totalSuccess%) (
      DEL /F /S /Q %PROPS_HOME%\*.properties >NUL
	  EXIT 2
  )
  IF %totalDownloads% EQU 0 (
	  EXIT 3
  )
  GOTO END_CASE

:REMOVE-PROPERTIES-VERIFY-FAILED
  DEL /F /S /Q %properties_save_path%\*.properties >NUL 
  GOTO END_CASE
  
:DEFAULT_CASE
  ECHO Unknown Environment
  GOTO END_CASE
  
:END_CASE
  VER > NUL
  GOTO :EOF
:: Declare methods end