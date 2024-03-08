@echo off

echo =========
echo Publish
echo =========
call dart pub publish --dry-run
if %errorlevel% neq 0 exit /b %errorlevel%