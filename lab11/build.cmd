call mvn clean -f j-side
call mvn package -f j-side

cd nat-side
call build.cmd
cd ..

rm -rf release
mkdir release
copy nat-side\Release\native.dll release
copy j-side\target\j-side-1.0-SNAPSHOT.jar release
