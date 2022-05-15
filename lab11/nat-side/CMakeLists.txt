cmake_minimum_required(VERSION 3.20)
project(nat_side)

string(REPLACE "\\" "/" JDK $ENV{JDK})
message("JDK home ${JDK}")
include_directories(${JDK}/include)

if(WIN32)
    include_directories(${JDK}/include/win32)
endif(WIN32)

set(CMAKE_CXX_STANDARD 20)

add_library(native SHARED pl_edu_pwr_java_lab11_NativeSortingMachine.cpp SortingMachine.cpp)