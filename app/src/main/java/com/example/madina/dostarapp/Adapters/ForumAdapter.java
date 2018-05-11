package com.example.madina.dostarapp.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.madina.dostarapp.Items.ForumTopic;
import com.example.madina.dostarapp.R;

import java.util.List;

/**
 * Created by The Great on 4/22/2018.
 */

public class ForumAdapter  extends RecyclerView.Adapter<CousesAdapter.CourseViewHolder>{
    private List<ForumTopic> topics;
    public ForumAdapter(List<ForumTopic> topics){
        this.topics = topics;
    }

    @Override
    public CousesAdapter.CourseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        CousesAdapter.CourseViewHolder pvh = new CousesAdapter.CourseViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CousesAdapter.CourseViewHolder courseViewHolder, int i) {
        courseViewHolder.course_name.setText(topics.get(i).name);
        courseViewHolder.course_desc.setText(topics.get(i).desc);
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class TopicViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView topic_name;
        TextView topic_desc;
        TopicViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            topic_name = itemView.findViewById(R.id.item_name);
            topic_desc = itemView.findViewById(R.id.item_desc);
        }
    }
}
