cmake_minimum_required(VERSION 3.10.2)
project("breakoutgame")

add_library(
        breakoutgame
        SHARED
        GameElement/GameObject/Ball.cpp
        GameElement/GameObject/Paddle.cpp
        GameElement/GameObject/Brick.cpp
        GameEngine/Breakoutgame.cpp
)

find_library(
        log-lib
        log
)

target_link_libraries(
        breakoutgame
        ${log-lib}
)
