cmake_minimum_required(VERSION 3.17)
project(untitled)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++1y -Wall -Wextra -pthread")

add_executable(untitled main.cpp)