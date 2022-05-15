//
// Created by aeki on 14.05.2022.
//

#include "SortingMachine.h"
#include <algorithm>

void SortingMachine::sort(JNIEnv *env, jobject obj) {

};

jobjectArray SortingMachine::sort(JNIEnv *env,
                                  jobject obj,
                                  jobjectArray inputArray,
                                  jobject order) {
    initializeMembers(env, obj, inputArray, order);
    sortValues();
    return buildResultArray();
}

jobjectArray SortingMachine::buildResultArray() {
    jclass doubleClass = env->FindClass("java/lang/Double");
    jmethodID doubleConstructor = env->GetMethodID(doubleClass, "<init>", "(D)V");

    auto resultArray = env->NewObjectArray(arrayLength, doubleClass, nullptr);

    for(jsize i = 0; i < arrayLength; i++){
        jobject element = env->NewObject(doubleClass, doubleConstructor, array[i]);
        env->SetObjectArrayElement(resultArray, i, element);
        env->DeleteLocalRef(element);
    }
    return resultArray;
}

void SortingMachine::initializeMembers(JNIEnv *env,
                                       jobject obj,
                                       jobjectArray inputArray,
                                       jobject order) {
    this->env = env;
    this->obj = obj;
    this->inputArray = inputArray;

    digestInputArray();

    if(order == nullptr){
        this->order = extractOrderFromObject();
    } else {
        this->order = boolFromBooleanObject(order);
    }
}

void SortingMachine::digestInputArray() {
    arrayLength = env->GetArrayLength(this->inputArray);
    array = std::make_unique<jdouble[]>(arrayLength);

    {
        jclass doubleClass = env->FindClass("java/lang/Double");
        jmethodID double_getDoubleValueID = env->GetMethodID(doubleClass, "doubleValue", "()D");
        for (jsize i = 0; i < arrayLength; i++) {
            jobject doubleObject = env->GetObjectArrayElement(inputArray, i);
            array[i] = env->CallDoubleMethod(doubleObject, double_getDoubleValueID);
            env->DeleteLocalRef(doubleObject);
        }
    }
}

bool SortingMachine::extractOrderFromObject(){
    jclass nativeSortingMachine_class = env->GetObjectClass(this->obj);
    jmethodID nsm_getOrder = env->GetMethodID(nativeSortingMachine_class, "getOrder", "()Ljava/lang/Boolean;");
    jobject orderBooleanObject = env->CallObjectMethod(obj, nsm_getOrder);
    return boolFromBooleanObject(orderBooleanObject);
}

void SortingMachine::sortValues() {
    if(order == ASCENDING)
        std::sort(array.get(), array.get() + arrayLength, std::less<jdouble>());
    else
        std::sort(array.get(), array.get() + arrayLength, std::greater<jdouble>());
}

bool SortingMachine::boolFromBooleanObject(jobject booleanObject) {
    jclass boolean_class = env->FindClass("java/lang/Boolean");
    jmethodID boolean_booleanValue = env->GetMethodID(boolean_class, "booleanValue", "()Z");
    jboolean value = env->CallBooleanMethod(booleanObject, boolean_booleanValue);
    return value;
}
