//
// Created by aeki on 14.05.2022.
//
#include "pl_edu_pwr_java_lab11_NativeSortingMachine.h"
#include <iostream>
#include "SortingMachine.h"

/*
 * Class:     pl_edu_pwr_java_lab11_NativeSortingMachine
 * Method:    sort01
 * Signature: ([Ljava/lang/Double;Ljava/lang/Boolean;)[Ljava/lang/Double;
 */
JNIEXPORT jobjectArray JNICALL Java_pl_edu_pwr_java_lab11_NativeSortingMachine_sort___3Ljava_lang_Double_2Ljava_lang_Boolean_2
        (JNIEnv *env, jobject obj, jobjectArray inputArray, jobject order){
    SortingMachine machine;
    return machine.sort(env, obj, inputArray, order);
};

/*
 * Class:     pl_edu_pwr_java_lab11_NativeSortingMachine
 * Method:    sort02
 * Signature: ([Ljava/lang/Double;)[Ljava/lang/Double;
 */
JNIEXPORT jobjectArray JNICALL Java_pl_edu_pwr_java_lab11_NativeSortingMachine_sort___3Ljava_lang_Double_2
        (JNIEnv *env, jobject obj, jobjectArray inputArray){
    SortingMachine machine;
    return machine.sort(env, obj, inputArray);
};

/*
 * Class:     pl_edu_pwr_java_lab11_NativeSortingMachine
 * Method:    sort03
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_pl_edu_pwr_java_lab11_NativeSortingMachine_getUserInputAndSort
(JNIEnv *env, jobject obj){
    jclass dialogService_class = env->FindClass("pl/edu/pwr/java/lab11/inputservice/UserInputService");
    jmethodID dialogService_getInstance =
            env->GetStaticMethodID(dialogService_class,"getInstance",
                                   "()Lpl/edu/pwr/java/lab11/inputservice/UserInputService;");
    jobject dialogServiceInstance = env->CallStaticObjectMethod(dialogService_class, dialogService_getInstance);
    jmethodID dialogService_getInput =
            env->GetMethodID(dialogService_class, "getInput",
                             "()Lpl/edu/pwr/java/lab11/inputservice/UserInputService$Data;");
    jobject userInputObject = env->CallObjectMethod(dialogServiceInstance, dialogService_getInput);
    env->DeleteLocalRef(dialogServiceInstance);
    jclass dialogServiceData_class = env->GetObjectClass(userInputObject);

    jfieldID inputDataValues_field = env->GetFieldID(dialogServiceData_class,"values", "[Ljava/lang/Double;");
    auto inputDataValues = static_cast<jobjectArray>(env->GetObjectField(userInputObject, inputDataValues_field));

    jfieldID inputDataOrder_field = env->GetFieldID(dialogServiceData_class, "order", "Lpl/edu/pwr/java/lab11/Order;");
    jobject order_object = env->GetObjectField(userInputObject, inputDataOrder_field);

    env->DeleteLocalRef(userInputObject);

    jclass nativeSortingMachine_class = env->GetObjectClass(obj);
    jmethodID sortingMachine_booleanFromOrderObject_method =
            env->GetMethodID(nativeSortingMachine_class, "setOrder", "(Lpl/edu/pwr/java/lab11/Order;)V");
    env->CallVoidMethod(obj, sortingMachine_booleanFromOrderObject_method, order_object);
    env->DeleteLocalRef(order_object);

    jfieldID nativeSortingMachine_input_field = env->GetFieldID(nativeSortingMachine_class, "input", "[Ljava/lang/Double;");
    env->SetObjectField(obj, nativeSortingMachine_input_field, inputDataValues);
    env->DeleteLocalRef(inputDataValues);

    jmethodID nativeSortingMachine_sort_method = env->GetMethodID(nativeSortingMachine_class, "sort", "()V");
    env->CallVoidMethod(obj, nativeSortingMachine_sort_method);
};