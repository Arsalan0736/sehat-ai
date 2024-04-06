package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.zegocloud.zimkit.services.ZIMKit;

import im.zego.zim.enums.ZIMErrorCode;

public class LoginMockActivity extends AppCompatActivity {

    EditText call_token;

    Button call_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mock);

        call_token = findViewById(R.id.call_token);
        call_btn = findViewById(R.id.call_btn);

        call_btn.setOnClickListener(v -> {
            String userId = call_token.getText().toString();
            connectUser(userId,userId,"");
        });
    }
    public void connectUser(String userId, String userName,String userAvatar) {
        // Logs in.
        ZIMKit.connectUser(userId,userName,userAvatar, errorInfo -> {
            if (errorInfo.code == ZIMErrorCode.SUCCESS) {
                // Operation after successful login. You will be redirected to other modules only after successful login. In this sample code, you will be redirected to the conversation module.
                toConversationActivity();
            } else {

            }
        });
    }
    private void toConversationActivity() {
        // Redirect to the conversation list (Activity) you created.
        Intent intent = new Intent(this,ConversationActivity.class);
        startActivity(intent);
    }
}