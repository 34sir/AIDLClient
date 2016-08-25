package com.wuyunxing.vae.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wuyunxing.vae.aidlserver.IOnNewPersonArrivedListener;
import com.wuyunxing.vae.aidlserver.IPersonManager;
import com.wuyunxing.vae.aidlserver.Person;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    private Button mButtonCheck;
    private Button mButtonRead;

    private IPersonManager myService = null;
    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            myService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            myService = IPersonManager.Stub.asInterface(service);
//
//            List<Person> list = null;
//            try {
//                list = myService.getAllPerson();
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//            if (list != null) {
//                StringBuilder text = new StringBuilder();
//
//                for (Person person : list) {
//                    text.append("\n联系人:");
//                    text.append(person.getName());
//                    text.append("\n             年龄:");
//                    text.append(person.getAge());
//                    text.append("\n 电话:");
//                    text.append(person.getTelNumber());
//                }
//                Log.d("vae_tag", text.toString());
//            } else {
//                Toast.makeText(MainActivity.this, "get data error",
//                        Toast.LENGTH_SHORT).show();
//            }
            try {
                myService.registerListener(mOnNewPersonArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.d("vae_tag", "bind success");
        }
    };

    private IOnNewPersonArrivedListener mOnNewPersonArrivedListener= new IOnNewPersonArrivedListener.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void onNewPersonArrivedListener(Person person) throws RemoteException {
            Log.d("vae_tag", "receive new person"+person.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonCheck = (Button) findViewById(R.id.check);
        mButtonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != myService) {
                    Person person = new Person();
                    person.setName("Person" + 1);
                    person.setAge(20);
                    person.setTelNumber("123456");
                    try {
                        myService.savePersonInfo(person);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("vae_tag", "eororororor");
                }
            }
        });
        mButtonRead = (Button) findViewById(R.id.read);
        mButtonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Person> list = null;
                try {
                    list = myService.getAllPerson();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (list != null) {
                    StringBuilder text = new StringBuilder();

                    for (Person person : list) {
                        text.append("\n联系人:");
                        text.append(person.getName());
                        text.append("\n             年龄:");
                        text.append(person.getAge());
                        text.append("\n 电话:");
                        text.append(person.getTelNumber());
                    }
                    Log.d("vae_tag", text.toString());
                } else {
                    Toast.makeText(MainActivity.this, "get data error",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Intent intent = new Intent("cn.com.karl.aidl.RemoteService");
        bindService(intent, conn, BIND_AUTO_CREATE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
