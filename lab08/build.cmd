call mvn clean
call mvn package
rm -rf release
mkdir release
copy target\lab*.jar release
ren release\lab*.jar lab8.jar