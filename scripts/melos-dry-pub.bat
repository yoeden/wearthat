@echo off

echo =========
echo Activate melos
echo =========
call dart pub global activate melos 
call melos version --yes -p

echo =========
echo Bootstrap melos
echo =========
call melos bs
if %errorlevel% neq 0 exit /b %errorlevel%

echo =========
echo Publish
echo =========
call melos publish --yes --dry-run --ignore="*pokemon*"
if %errorlevel% neq 0 exit /b %errorlevel%