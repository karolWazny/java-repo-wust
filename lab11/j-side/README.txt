This project was built on Windows. Since it uses Java native mechanisms (JNI), it may need some tweaks to run on other OS.

I used MSVC compiler for compiling native side, MingW had problems with dependencies in generated dll;
haven't tested other compilers.

For the CMake to work properly with CMakeLists.txt, your JDK home should be in your environment variables (named JDK).
(example: "C:\Program Files\Microsoft\jdk-11.0.12.7-hotspot")

nat-side/build.cmd may not work unless you use MSVC from VS2022 (that's what I used, haven't tested anything else).

copy-dll.cmd is a little helper script I used so I did not have to delete old dll and copy new one by hand. It may
not work depending on where your compiler or IDE places build dll.

When running unit tests or NativeSortingMachine.main() it is crucial you add following to virtual machine options:
-Djava.library.path="lib;%PATH%"
Otherwise you will get an error from virtual machine not finding the library.
You may need to adjust the <lib> part of above option, depending on from where you run the program.
The above probably only works on Windows, I did not feel the need to tweak it to work on other OSs.

If you encounter library-not-found errors, make sure you compile your dll for a proper architecture;
e.g. you may have problems using x86 compiled dll with amd64 JVM (I had that problem, switching compiler options
from x86 to AMD64 soved the issue).