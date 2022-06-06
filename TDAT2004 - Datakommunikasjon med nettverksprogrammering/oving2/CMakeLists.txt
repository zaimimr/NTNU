cmake_minimum_required(VERSION 2.8)

project(oving2)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++1y -Wall -Wextra")

find_package(Threads REQUIRED)

add_executable(oving2 oving2.cpp)
target_link_libraries(oving2 ${CMAKE_THREAD_LIBS_INIT})