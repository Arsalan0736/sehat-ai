package com.example.myapplication;




import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.KeyConstant;
import com.example.myapplication.LoginMockActivity;
import com.example.myapplication.R;
import com.zegocloud.zimkit.services.ZIMKit;

public class Chat_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initZegocloud();

        startActivity(new Intent(this, LoginMockActivity.class));
        finish();
    }

    public  void initZegocloud(){
        ZIMKit.initWith(this.getApplication(), KeyConstant.appID,KeyConstant.appSign);
        // Online notification for the initialization (use the following code if this is needed).
        ZIMKit.initNotifications();
    }
}