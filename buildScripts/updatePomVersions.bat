@echo off
echo 'Will change the versions in pom.xml files based on git branch name when doing Jenkins CI build...'

::
:: get git branch_name
::

:: get current git branch name
:: Jenkins will set GIT_BRANCH environment variable if this batch is running in a Jenkins job
goto getbranch_name-2

:getbranch_name-1
if %GIT_BRANCH%==""  (
	echo GIT_BRANCH is NOT defined
	for /f %%i in ('"git rev-parse --abbrev-ref HEAD"') do set branch_name=%%i
) else (
 	echo GIT_BRANCH is defined 
 	set branch_name=%GIT_BRANCH%
)

:getbranch_name-2
if defined GIT_BRANCH (
 	echo GIT_BRANCH is defined 
 	set branch_name=%GIT_BRANCH%
) else (
 	echo GIT_BRANCH is NOT defined
 	for /f %%i in ('"git rev-parse --abbrev-ref HEAD"') do set branch_name=%%i
)

:: replace origin/ with empty if have
set branch_name=%branch_name:origin/=%

echo branch_name is %branch_name%

if %branch_name%==dev (
	echo It is dev branch build. Do nothing
	goto end 
)

::
:: get current_version in parent pom
::

:: get the pathname of the batch script itself with %0
REM echo %0
:: parameter extensions can be applied to this so %~dp0 will return the Drive and Path to the batch script 
REM echo %~dp0
:: %~f0 will return the full pathname 
REM echo %~f0
:: You can refer to other files in the same folder as the batch script by using this syntax
:: echo %0\..\SecondBatch.cmd
REM echo %~dp0\access-control-service
:: set the working directory of a command in windows batch file
REM pushd %~dp0\access-control-service

:: WORKSPACE  The absolute path of the workspace if running in a Jenkins job.  
:: set the working directory of a command in windows batch file
pushd %WORKSPACE%\pcm

:: get current version of the top level pom
:: call mvn help:evaluate -Dexpression=project.version
:: for /f %%i in ('"mvn help:evaluate -Dexpression=project.version | grep -v '\[.*'"') do set current_version=%%i
:: run the following line first to have downloading done before next line
call mvn help:evaluate -Dexpression=project.version
:: The following link couldn't excluding Downloading: line
for /f %%i in ('"mvn help:evaluate -Dexpression=project.version | grep -v '\[.*'"') do set current_version=%%i

echo current_version is %current_version%

::
:: get Jenkins BUILD_NUMBER
::

:: Jenkins will set BUILD_NUMBER environment variable if this batch is running in a Jenkins job
if defined BUILD_NUMBER (
 	echo BUILD_NUMBER is defined 
) else (
 	set BUILD_NUMBER=0
)

::
:: build new version
::

if %branch_name%==dev (
	echo It is dev branch build
	goto end
) else if %branch_name%==master (
	echo It is master branch build
	:: replace .0-SNAPSHOT with empty and append build number
	set new_version=%current_version:.0-SNAPSHOT=%.%BUILD_NUMBER%
) else (
	echo It is not master/dev branch
	echo Put the branch name in the qualifier
	:: replace .0-SNAPSHOT with empty, append build number and add branch name as quilifier
	set new_version=%current_version:.0-SNAPSHOT=%.%BUILD_NUMBER%-%branch_name%
)

echo new_version is %new_version%

:: Sets the current projects version, updating the details of any child modules as necessary.
call mvn versions:set -DgenerateBackupPoms=false -DnewVersion=%new_version%

:: Scans the current projects child modules, updating the versions of any which use the current project to the version of the current project.
::call mvn versions:update-child-modules -DgenerateBackupPoms=false -DnewVersion=%new_version%

::
:: generate a version properties file to be used by Jenkins Enviorenment Injector Plugin to generate a Jenkins PROJECT_VERSION environment variable
::

echo PROJECT_VERSION=%new_version% > version.properties

::
:: generate a commit properties file to be used by Jenkins Enviorenment Injector Plugin to generate a Jenkins GIT_COMMIT_ID environment variable
::
for /f %%i in ('"git rev-parse HEAD"') do set commit_id=%%i
echo GIT_COMMIT_ID=%commit_id% > commit.properties

:: reset the working directory
popd

:end