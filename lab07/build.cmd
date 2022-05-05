call mvn clean
call mvn package
rm -rf release
mkdir release
copy target\lab7*.jar release
ren release\lab7*.jar lab7.jar