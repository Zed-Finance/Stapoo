package com.orion.stapoo.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.orion.stapoo.R;
import com.orion.stapoo.utils.PrefManager;

public class AvatarActivity extends AppCompatActivity implements View.OnClickListener {

    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        prefManager = new PrefManager(this);

        ImageView avatar1=findViewById(R.id.avatar_1);
        ImageView avatar2=findViewById(R.id.avatar_2);
        ImageView avatar3=findViewById(R.id.avatar_3);
        ImageView avatar4=findViewById(R.id.avatar_4);

        avatar1.setOnClickListener(this);
        avatar2.setOnClickListener(this);
        avatar4.setOnClickListener(this);
        avatar3.setOnClickListener(this);

        Toast.makeText(this, prefManager.getChosenAvatar() + "", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.avatar_1:
                prefManager.setAvatar(0);
                break;
            case R.id.avatar_2:
                prefManager.setAvatar(1);
                break;
            case R.id.avatar_3:
                prefManager.setAvatar(2);
                break;
            case R.id.avatar_4:
                prefManager.setAvatar(3);
                break;
        }
    }
}