@echo off
echo 'Will decrease Major parts of version numbers in pom.xml files by 1'

::
:: get current_version in parent pom
::
 
:: set the working directory of a command in windows batch file
pushd ..\pcm

:: get current version of the top level pom
:: call mvn help:evaluate -Dexpression=project.version
:: for /f %%i in ('"mvn help:evaluate -Dexpression=project.version | grep -v '\[.*'"') do set current_version=%%i
:: run the following line first to have downloading done before next line
call mvn help:evaluate -Dexpression=project.version
:: The following link couldn't excluding Downloading: line
for /f %%i in ('"mvn help:evaluate -Dexpression=project.version | grep -v '\[.*'"') do set current_version=%%i

echo current_version is %current_version%


::
:: split the maven version into major, minor and build number with qualifier
::

echo.-- split the maven version into major, minor and build number with qualifier, using dot as delimiter
for /f "tokens=1,2,3 delims=." %%a in ("%current_version%") do set major=%%a&set minor=%%b&set bq=%%c
echo.Major: %major%
echo.Minor: %minor%
echo.BuildNumberAndQualifier: %bq%


::
:: decrease the major version to get the new version
::
echo.-- decrease the major version to get the new version
set /a sum1=%major%-1
set new_major=%sum1%
echo new_major is %new_major%

set new_version=%new_major%.%minor%.%bq%

echo new_version is %new_version%

:: Sets the current projects version, updating the details of any child modules as necessary.
call mvn versions:set -DgenerateBackupPoms=false -DnewVersion=%new_version%

git commit -a -m "Decrease major parts of version numbers in pom.xml files by 1"

:: reset the working directory
popd

:end