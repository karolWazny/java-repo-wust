call mvn -f source/jsfx clean
call mvn -f source/jsfx verify

rm -rf release
mkdir release
cp source/jsfx/target/jsfx-1.0-SNAPSHOT.jar release
cp source/jsfx/target/jsfx-1.0-SNAPSHOT.lib/* release

