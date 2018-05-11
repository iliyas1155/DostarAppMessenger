package com.example.madina.dostarapp.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.madina.dostarapp.Items.Course;
import com.example.madina.dostarapp.R;

import java.util.List;

public class CousesAdapter extends RecyclerView.Adapter<CousesAdapter.CourseViewHolder>{

    private List<Course> courses;
    public CousesAdapter(List<Course> courses){
        this.courses = courses;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        CourseViewHolder pvh = new CourseViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CourseViewHolder courseViewHolder, int i) {
        courseViewHolder.course_name.setText(courses.get(i).name);
        courseViewHolder.course_desc.setText(courses.get(i).desc);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView course_name;
        TextView course_desc;
        CourseViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            course_name = itemView.findViewById(R.id.item_name);
            course_desc = itemView.findViewById(R.id.item_desc);
        }
    }
}