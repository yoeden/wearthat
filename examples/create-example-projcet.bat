@echo off
set /p "name=Project name: "

flutter create %name% --org com.example --platforms android --android-language java
cd %name%