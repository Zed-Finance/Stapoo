package com.orion.stapoo.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        ImageView avatar1 = findViewById(R.id.avatar_1);
        ImageView avatar2 = findViewById(R.id.avatar_2);
        ImageView avatar3 = findViewById(R.id.avatar_3);
        ImageView avatar4 = findViewById(R.id.avatar_4);


        ImageView speaker=findViewById(R.id.speaker);

        avatar1.setOnClickListener(this);
        avatar2.setOnClickListener(this);
        avatar4.setOnClickListener(this);
        avatar3.setOnClickListener(this);

        Toast.makeText(this, prefManager.getChosenAvatar() + "", Toast.LENGTH_LONG).show();

    }

    private void navigateFurther() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.avatar_1:
                prefManager.setAvatar(0);
                prefManager.setIsAvatarChosen(true);
                navigateFurther();
                break;
            case R.id.avatar_2:
                prefManager.setAvatar(1);
                prefManager.setIsAvatarChosen(true);
                navigateFurther();
                break;
            case R.id.avatar_3:
                prefManager.setAvatar(2);
                prefManager.setIsAvatarChosen(true);
                navigateFurther();
                break;
            case R.id.avatar_4:
                prefManager.setAvatar(3);
                prefManager.setIsAvatarChosen(true);
                navigateFurther();
                break;
        }
    }
}