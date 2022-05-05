@echo off
set app_version=1.0-SNAPSHOT

call mvn clean -f source
call mvn package -f source
rm -rf release
mkdir release

copy source\app\target\app*.jar release
set app_lib_dir=source\app\target\app-%app_version%.lib
xcopy %app_lib_dir% release

copy source\library\target\library*.jar release