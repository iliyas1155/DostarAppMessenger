package com.example.madina.dostarapp.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.madina.dostarapp.Items.Course;
import com.example.madina.dostarapp.R;

import java.util.ArrayList;
import java.util.List;

public class CousesAdapter extends RecyclerView.Adapter<CousesAdapter.CourseViewHolder>
        implements Filterable {

    private List<Course> courses;
    private List<Course> filteredCourses;

    public CousesAdapter(List<Course> courses){
        this.courses = courses;
        this.filteredCourses = courses;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        CourseViewHolder pvh = new CourseViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CourseViewHolder courseViewHolder, int i) {
        courseViewHolder.course_name.setText(filteredCourses.get(i).name);
        courseViewHolder.course_desc.setText(filteredCourses.get(i).desc);
    }

    @Override
    public int getItemCount() {
        return filteredCourses.size();
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();
                List<Course> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered = courses;
                } else {
                    for (Course course : courses) {
                        if (course.name.contains(query)) {
                            filtered.add(course);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.count = filtered.size();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence query, FilterResults filterResults) {
                filteredCourses = (ArrayList<Course>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}