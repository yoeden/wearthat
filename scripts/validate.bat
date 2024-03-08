@echo off

echo =========
echo Analyze package
echo =========
call flutter analyze --no-fatal-warnings
if %errorlevel% neq 0 exit /b %errorlevel%
