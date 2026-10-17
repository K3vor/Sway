cmake_minimum_required(VERSION 3.22.1)

project("sway_native" LANGUAGES C CXX)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

include(FetchContent)

FetchContent_Declare(
    juce
    GIT_REPOSITORY https://github.com/juce-framework/JUCE.git
    GIT_TAG        8.0.4
    GIT_SHALLOW    TRUE
)

FetchContent_GetProperties(juce)
if(NOT juce_POPULATED)
    FetchContent_Populate(juce)
endif()

set(JUCE_MODULES_DIR "${juce_SOURCE_DIR}/modules")

add_library(
    sway_native
    SHARED
    native-lib.cpp
    "${JUCE_MODULES_DIR}/juce_core/juce_core.cpp"
    "${JUCE_MODULES_DIR}/juce_events/juce_events.cpp"
    "${JUCE_MODULES_DIR}/juce_audio_basics/juce_audio_basics.cpp"
    "${JUCE_MODULES_DIR}/juce_audio_devices/juce_audio_devices.cpp"
    "${JUCE_MODULES_DIR}/juce_audio_formats/juce_audio_formats.cpp"
    "${JUCE_MODULES_DIR}/juce_dsp/juce_dsp.cpp"
    "${ANDROID_NDK}/sources/android/cpufeatures/cpu-features.c"
)

target_include_directories(
    sway_native
    PRIVATE
    "${CMAKE_CURRENT_SOURCE_DIR}"
    "${JUCE_MODULES_DIR}"
    "${ANDROID_NDK}/sources/android/cpufeatures"
)

target_compile_definitions(
    sway_native
    PRIVATE
    JUCE_ANDROID=1
    JUCE_GLOBAL_MODULE_SETTINGS_INCLUDED=1
    JUCE_DONT_DECLARE_PROJECTINFO=1
)

target_compile_options(
    sway_native
    PRIVATE
    $<$<COMPILE_LANGUAGE:CXX>:-include "${CMAKE_CURRENT_SOURCE_DIR}/AppConfig.h">
)

target_link_libraries(
    sway_native
    PRIVATE
    log
    android
    OpenSLES
    aaudio
)
