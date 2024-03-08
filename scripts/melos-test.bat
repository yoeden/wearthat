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
echo Run Tests
echo =========
call melos exec --dir-exists=test -- flutter test
if %errorlevel% neq 0 exit /b %errorlevel%