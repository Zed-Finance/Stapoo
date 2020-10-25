package com.orion.stapoo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orion.stapoo.R;
import com.orion.stapoo.interfaces.ClickInterface;
import com.orion.stapoo.models.Course;


import java.util.List;

public class CourseMaterialAdapter extends RecyclerView.Adapter<CourseMaterialAdapter.CourseMaterialViewHolder> {
    List<Course> courseList;
    ClickInterface clickInterface;

    public CourseMaterialAdapter(List<Course> courseList, ClickInterface clickInterface) {
        this.courseList = courseList;
        this.clickInterface = clickInterface;
    }

    @NonNull
    @Override
    public CourseMaterialAdapter.CourseMaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CourseMaterialViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CourseMaterialAdapter.CourseMaterialViewHolder holder, int position) {
        final Course course = courseList.get(position);

        holder.title.setText(course.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickInterface.cardClickInterface(course.getMaterialId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class CourseMaterialViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public CourseMaterialViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
}
