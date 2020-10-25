package com.orion.stapoo.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orion.stapoo.R;
import com.orion.stapoo.adapters.CourseMaterialAdapter;
import com.orion.stapoo.adapters.YoutubeVideoAdapter;
import com.orion.stapoo.interfaces.ClickInterface;
import com.orion.stapoo.models.Course;
import com.orion.stapoo.models.ToolKitItem;

import java.util.ArrayList;
import java.util.List;

public class CourseMaterialActivity extends AppCompatActivity {
    List<Course> linkList;
    private ClickInterface clickInterface;
    String day, subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_material);

        subject = getIntent().getStringExtra("subject");
        day = getIntent().getStringExtra("day");

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        linkList = new ArrayList<>();
        clickInterface = new ClickInterface() {
            @Override
            public void cardClickInterface(String materialId) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(materialId));
                startActivity(i);
            }
        };

        FirebaseDatabase.getInstance().getReference().child("subjects").child(subject).child(day).child("materials").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                linkList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    linkList.add(dataSnapshot.getValue(Course.class));
                }
                recyclerView.setAdapter(new CourseMaterialAdapter(linkList, clickInterface));
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}