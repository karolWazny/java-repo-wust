call mvn clean -f source
call mvn package -f source
rm -rf release
mkdir release
copy source\app\target\app*.jar release
ren release\app*.jar app.jar
copy source\library\target\library*.jar release
ren release\library*.jar library.jar