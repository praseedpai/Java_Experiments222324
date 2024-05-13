/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class NativeClass */

#ifndef _Included_NativeClass
#define _Included_NativeClass
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     NativeClass
 * Method:    createStrs
 * Signature: (I)[Ljava/lang/String;
 */
JNIEXPORT jobjectArray JNICALL Java_NativeClass_createStrs
  (JNIEnv *, jobject, jint);

/*
 * Class:     NativeClass
 * Method:    createVals
 * Signature: (I)[I
 */
JNIEXPORT jintArray JNICALL Java_NativeClass_createVals
  (JNIEnv *, jobject, jint);

/*
 * Class:     NativeClass
 * Method:    sortStrs
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_NativeClass_sortStrs
  (JNIEnv *, jobject);

/*
 * Class:     NativeClass
 * Method:    reverseArray
 * Signature: ([I)V
 */
JNIEXPORT void JNICALL Java_NativeClass_reverseArray
  (JNIEnv *, jobject, jintArray);

/*
 * Class:     NativeClass
 * Method:    genException
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_NativeClass_genException
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
