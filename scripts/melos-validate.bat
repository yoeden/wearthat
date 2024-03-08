@echo off

echo =========
echo Activate melos
echo =========
call dart pub global activate melos 

echo =========
echo Bootstrap melos
echo =========
call melos bs
if %errorlevel% neq 0 exit /b %errorlevel%

echo =========
echo Analyze package
echo =========
call melos exec -- flutter analyze --no-fatal-infos
if %errorlevel% neq 0 exit /b %errorlevel%
