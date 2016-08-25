// IMyService.aidl
package com.wuyunxing.vae.aidlserver;

import com.wuyunxing.vae.aidlserver.Person;// Person.aidl
import com.wuyunxing.vae.aidlserver.IOnNewPersonArrivedListener;// IOnNewPersonArrivedListener.aidl
// Declare any non-default types here with import statements

interface IPersonManager {
    void savePersonInfo(in Person person);
    List<Person> getAllPerson();
    void registerListener(IOnNewPersonArrivedListener listener);
    void unregisterListener(IOnNewPersonArrivedListener listener);
}
