@echo off
setlocal
set "SCRIPT_DIR=%~dp0"
call "%SCRIPT_DIR%..\ms-catalogo\ms-catalogo\mvnw.cmd" -f "%SCRIPT_DIR%..\pom.xml" -pl ms-audit %*
exit /b %ERRORLEVEL%
