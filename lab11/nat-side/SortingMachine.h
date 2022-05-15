//
// Created by aeki on 14.05.2022.
//

#ifndef NAT_SIDE_SORTINGMACHINE_H
#define NAT_SIDE_SORTINGMACHINE_H

#include <jni.h>
#include <memory>

class SortingMachine {
public:
    static const bool ASCENDING = true;
    static const bool DESCENDING = false;
    jobjectArray sort(JNIEnv *env,
                        jobject obj,
                        jobjectArray inputArray,
                        jobject order = nullptr);
    void sort(JNIEnv *env,
              jobject obj);
private:
    bool extractOrderFromObject();
    jobjectArray buildResultArray();
    void initializeMembers(JNIEnv *env,
                           jobject obj,
                           jobjectArray inputArray,
                           jobject order = nullptr);
    void sortValues();
    bool boolFromBooleanObject(jobject booleanObject);
    void digestInputArray();

    JNIEnv* env{};
    jobject obj{};
    jobjectArray inputArray{};
    jsize arrayLength{};
    bool order{};
    std::unique_ptr<jdouble[]> array{};
};

#endif //NAT_SIDE_SORTINGMACHINE_H
