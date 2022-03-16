IF exist classes\ (rm -r -f classes)
mkdir classes
dir source /s /B | findstr /e /r /I ".java"  > lib_sources.txt
javac -d classes @lib_sources.txt
rm lib_sources.txt

IF exist jars\ (rm -r -f jars)
mkdir jars

jar cfe jars/record-viewer.jar com.app.Main -C classes .

rm -r -f classes

REM cleaning up
IF exist release\ (rm -r -f release)
mkdir release
move jars release

REM add run script inside build folder
REM echo bin\java.exe --module applicationModule/main.App > release\build\run.cmd
