// IOnNewPersonArrivedListener.aidl
package com.wuyunxing.vae.aidlserver;

// Declare any non-default types here with import statements
import com.wuyunxing.vae.aidlserver.Person;// Person.aidl
interface IOnNewPersonArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    void onNewPersonArrivedListener(in Person person);
}
