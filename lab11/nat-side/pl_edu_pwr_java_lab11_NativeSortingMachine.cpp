//
// Created by aeki on 14.05.2022.
//
#include "pl_edu_pwr_java_lab11_NativeSortingMachine.h"
#include <iostream>

JNIEXPORT jobjectArray JNICALL Java_pl_edu_pwr_java_lab11_NativeSortingMachine_sort01
        (JNIEnv *, jobject, jobjectArray, jobject){
    std::cout << "Hello there from method sort03\n";
    return nullptr;
};

/*
 * Class:     pl_edu_pwr_java_lab11_NativeSortingMachine
 * Method:    sort02
 * Signature: ([Ljava/lang/Double;)[Ljava/lang/Double;
 */
JNIEXPORT jobjectArray JNICALL Java_pl_edu_pwr_java_lab11_NativeSortingMachine_sort02
        (JNIEnv *env, jobject obj, jobjectArray){
    std::cout << "Hello there from method sort03\n";

    return nullptr;
};

/*
 * Class:     pl_edu_pwr_java_lab11_NativeSortingMachine
 * Method:    sort03
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_pl_edu_pwr_java_lab11_NativeSortingMachine_sort03
(JNIEnv *env, jobject obj){
    std::cout << "Hello there from method sort03\n";
    jclass cls = env->GetObjectClass(obj);
    jmethodID mid = env->GetMethodID(cls, "showDialog","()V");
    if (mid != nullptr) {
        env->CallVoidMethod(obj, mid);
    } else {
        std::cout << "Not found the method!\n";
    }
};