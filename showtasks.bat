call runcrud.bat
if "%ERRORLEVEL%" == "0" goto explorer
echo.
echo I can't run this script.
goto fail

:explorer
explorer "http://localhost:8080/crud/v1/task/getTasks"

if "%ERRORLEVEL%" == "0" goto end
echo.
echo I think your browser is broken.
goto fail

:fail
echo.
echo Ups. We have error

:end
echo.
echo Work is finished