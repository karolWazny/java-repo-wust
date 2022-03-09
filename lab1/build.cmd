IF exist classes\ (rm -r -f classes)
mkdir classes
dir source\libraryModule /s /B | findstr /e /r /I ".java"  > lib_sources.txt
javac -d classes @lib_sources.txt
rm lib_sources.txt

IF exist jars\ (rm -r -f jars)
mkdir jars
jar --create --file jars/library.jar -C classes .
rm -r -f classes

mkdir classes
dir source\applicationModule /s /B | findstr /e /r /I ".java"  > app_sources.txt
javac --module-path jars -d classes @app_sources.txt
rm app_sources.txt

jar --create --file jars/snapshooter.jar --main-class main.App -C classes .
rm -r -f classes