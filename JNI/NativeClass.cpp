#include <stdio.h>
#include "jni.h"
#include "NativeClass.h"
void insertSort(JNIEnv *env, jobjectArray valsArray, jmethodID compID);
////////////////////////////////////////////////
///*
// * Class:     NativeClass
// * Method:    createVals
// * Signature: (I)[I
// */
//
//

JNIEXPORT jintArray JNICALL Java_NativeClass_createVals
  (JNIEnv *env, jobject DemoObj, jint len)
{
    jintArray RetArray;
    int x;
    jint *localArray;

    RetArray = env->NewIntArray(len);
    localArray = env->GetIntArrayElements(RetArray, NULL);
    for ( x = 0; x < len; x++)
        localArray[x] = len - x - 1;
    env->ReleaseIntArrayElements(RetArray, localArray, 0);
    return RetArray;
}


/*
 * Class:     NativeClass
 * Method:    reverseArray
 * Signature: ([I)V
 */
JNIEXPORT void JNICALL Java_NativeClass_reverseArray
  (JNIEnv *env, jobject DemoObj, jintArray vals)
{
        jint x, temp;
        jsize len;
        jboolean isCopy;
        jint *localArray;

        len = env->GetArrayLength(vals);
        localArray = env->GetIntArrayElements(vals, &isCopy);
        for (x = 0; x < len/2; x++)
        {
            temp = localArray[x];
            localArray[x] = localArray[len - x - 1];
            localArray[len - x - 1] = temp;
        }
        env->ReleaseIntArrayElements(vals, localArray, 0);

} 

/*
 * Class:     NativeClass
 * Method:    createStrs
 * Signature: (I)[Ljava/lang/String;
 */
JNIEXPORT jobjectArray JNICALL Java_NativeClass_createStrs
  (JNIEnv *env, jobject DemoObj, jint len)
{
    jobjectArray RetArray;
    jobject StringObj;
    jclass StringClass;
    int x;
    char str[80];

    StringClass = env->FindClass("java/lang/String");
    RetArray = env->NewObjectArray(len, StringClass, NULL);
    for (x = 0; x < len; x++)
    {
        sprintf(str, "This is string #%04d", len - x - 1);
        StringObj = env->NewStringUTF(str);
        env->SetObjectArrayElement(RetArray, x, StringObj);
    }
    return RetArray;

}

/*
 * Class:     NativeClass
 * Method:    sortStrs
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_NativeClass_sortStrs
  (JNIEnv *env, jobject DemoObj)
{
    jobjectArray valsArray;
    jclass demoClass;
    jfieldID valsArrayID;
    jclass StringClass;
    jmethodID compID;

    demoClass = env->GetObjectClass(DemoObj);
    valsArrayID = env->GetFieldID(demoClass, "strs", "[Ljava/lang/String;");
    if ( valsArrayID == NULL ) return;

    valsArray = (jobjectArray)env->GetObjectField(DemoObj, valsArrayID);
    if ( valsArray == NULL ) return;

    StringClass = env->FindClass("java/lang/String");
    if (StringClass == NULL) return;

    compID = env->GetMethodID(StringClass, "compareTo", "(Ljava/lang/String;)I");
    if (compID == NULL) return;

    insertSort(env, valsArray, compID);
}

void insertSort(JNIEnv *env, jobjectArray valsArray, jmethodID compID)
{
    int i, j, len;
    jobject tmp, cobj, pobj;

    len = env->GetArrayLength(valsArray);

    for ( i = 1; i < len; i++ )
    {
        cobj = env->GetObjectArrayElement(valsArray, i);
        pobj = env->GetObjectArrayElement(valsArray, i - 1);
        if (env->CallIntMethod(cobj, compID, pobj) < 0)
        {
            for ( j = i - 1; j >= 0; j-- )
            {
                tmp = env->GetObjectArrayElement(valsArray, j);
                env->SetObjectArrayElement(valsArray, j + 1, tmp);
                if ( j == 0 ) break;
                tmp = env->GetObjectArrayElement(valsArray, j - 1);
                if ( env->CallIntMethod(tmp, compID, cobj) < 0 ) break;
            }
            env->SetObjectArrayElement(valsArray, j, cobj);
        }
    }

}

/*
 * Class:     NativeClass
 * Method:    genException
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_NativeClass_genException
  (JNIEnv *env, jobject DemoObj)
{
    jclass exceptClass;

    exceptClass = env->FindClass("java/lang/Exception");
    env->ThrowNew(exceptClass, "genException()");

}