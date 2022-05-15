rm release\native.dll
copy nat-side\cmake-build-release-visual-studio\native.dll release

rm -rf j-side\lib
mkdir j-side\lib
copy nat-side\cmake-build-release-visual-studio\native.dll j-side\lib