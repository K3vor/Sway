#include <jni.h>
#include <string>
#include <juce_core/juce_core.h>

namespace juce {
    extern const char* juce_compilationDate = __DATE__;
    extern const char* juce_compilationTime = __TIME__;
}

extern "C" JNIEXPORT jstring JNICALL Java_com_example_sway_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Sway Audio Engine (JUCE " + juce::SystemStats::getJUCEVersion().toStdString() + ") Loaded!";
    return env->NewStringUTF(hello.c_str());
}
