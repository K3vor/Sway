cmake_minimum_required(VERSION 3.22.1)

project(sway-audio CXX C)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Support 16 KB page sizes required for Android 15+
add_link_options("-Wl,-z,max-page-size=16384")

include(FetchContent)

# Fetch JUCE 8.0.4 headlessly
FetchContent_Declare(
    juce
    GIT_REPOSITORY https://github.com/juce-framework/JUCE.git
    GIT_TAG        8.0.4
)

# Disable unnecessary JUCE features for headless DSP library
set(JUCE_BUILD_HELPER_TARGETS OFF CACHE BOOL "" FORCE)
set(JUCE_ENABLE_MODULE_SOURCE_GROUPS OFF CACHE BOOL "" FORCE)

FetchContent_MakeAvailable(juce)

# Native shared library
add_library(
    sway-audio
    SHARED
    native-lib.cpp
)

find_library(
    log-lib
    log
)

target_link_libraries(
    sway-audio
    PRIVATE
    juce::juce_audio_basics
    juce::juce_audio_devices
    juce::juce_audio_formats
    juce::juce_dsp
    juce::juce_core
    ${log-lib}
)

target_link_options(sway-audio PRIVATE "-Wl,-z,max-page-size=16384")
