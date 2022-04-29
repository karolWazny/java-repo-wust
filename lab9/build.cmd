@echo off
set app_version=1.0-SNAPSHOT
set lib_version=1.0-SNAPSHOT

call mvn clean -f source
call mvn package -f source
rm -rf release
mkdir release

copy source\app\target\app*.jar release
ren release\app*.jar app.jar
set app_lib_dir=source\app\target\app-%app_version%.lib
mkdir release\app-%app_version%.lib
xcopy %app_lib_dir% release

copy source\library\target\library*.jar release
ren release\library*.jar library.jar