package com.wgl.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wgl.b.IMyAidlInterface;
import com.wgl.b.MyData;

public class MainActivity extends AppCompatActivity {

    private IMyAidlInterface iMyAidlInterface;
    private boolean isBind = false;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMyAidlInterface = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //捆绑Service
        Intent intent = new Intent();
        intent.setAction("com.wgl.aidl");
        intent.setPackage("com.wgl.b");
        isBind = bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void doClick(View view) {
        if(isBind) {
            try {
                MyData data = iMyAidlInterface.getData();
                Toast.makeText(MainActivity.this,data.toString(),Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {

        }
    }
}
