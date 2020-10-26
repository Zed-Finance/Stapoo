package com.orion.stapoo.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.snapshot.LeafNode;
import com.orion.stapoo.R;
import com.orion.stapoo.utils.PrefManager;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final PrefManager prefManager = new PrefManager(this);

        CardView cardViewLearn = findViewById(R.id.card_view_learn);
        CardView cardViewToolkit = findViewById(R.id.card_view_toolkit);

        cardViewLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LearnActivity.class));

            }
        });

        cardViewToolkit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ToolkitActivity.class));
            }
        });

        ImageView imgLogout = findViewById(R.id.logout);
        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefManager.setIsLoggedIn(false);
                prefManager.setIsAvatarChosen(false);
                prefManager.setUsername(null);
                prefManager.setAvatar(0);

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }
}