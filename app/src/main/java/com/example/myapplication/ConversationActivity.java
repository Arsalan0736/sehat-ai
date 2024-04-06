package com.example.myapplication;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zegocloud.zimkit.common.ZIMKitRouter;
import com.zegocloud.zimkit.common.enums.ZIMKitConversationType;

public class ConversationActivity extends AppCompatActivity {

    FloatingActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        actionButton = findViewById(R.id.floating_btn);

        actionButton.setOnClickListener(v -> {
            showPopupMenu();

        });
    }
    void showPopupMenu(){
        PopupMenu popupMenu = new PopupMenu(this,actionButton);
        popupMenu.getMenuInflater().inflate(R.menu.menu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {

            if(menuItem.getItemId()==R.id.new_chat){
                shownewChatDialog();
                return true;
            }
            if(menuItem.getItemId()==R.id.logout){
                return true;
            }

            return false;
        });
        popupMenu.show();
    }

    void shownewChatDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Chat");

        EditText editText = new EditText(this);
        editText.setHint("User ID");
        builder.setView(editText);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ZIMKitRouter.toMessageActivity(ConversationActivity.this, editText.getText().toString(), ZIMKitConversationType.ZIMKitConversationTypePeer);
            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.create().show();
    }

}