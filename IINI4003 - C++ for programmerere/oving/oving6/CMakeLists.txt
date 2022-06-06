cmake_minimum_required(VERSION 3.17)
project(oving6_1)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++1y -Wall -Wextra -pthread")

add_executable(oving6_1 main.cpp)

find_package(Boost 1.55.0 COMPONENTS system REQUIRED)
include_directories(${Boost_INCLUDE_DIR})

add_executable(webserver webserver.cpp)
target_link_libraries(webserver ${Boost_LIBRARIES})