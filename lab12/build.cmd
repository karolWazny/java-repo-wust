call mvn -f source/cell-inator clean
call mvn -f source/cell-inator verify

rm -rf release
mkdir release
cp source/cell-inator/target/cell-inator-1.0-SNAPSHOT.jar release
cp source/cell-inator/target/cell-inator-1.0-SNAPSHOT.lib/* release