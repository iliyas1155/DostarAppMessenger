package com.diplomnikiiitu.dostarapp.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.diplomnikiiitu.dostarapp.Items.ForumTopic;
import com.diplomnikiiitu.dostarapp.R;

import java.util.ArrayList;
import java.util.List;

public class ForumAdapter  extends RecyclerView.Adapter<CousesAdapter.CourseViewHolder>
        implements Filterable {
    private List<ForumTopic> topics;
    private List<ForumTopic> filteredTopics;

    public ForumAdapter(List<ForumTopic> topics){
        this.topics = topics;
        this.filteredTopics = topics;
    }

    @Override
    public CousesAdapter.CourseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        CousesAdapter.CourseViewHolder pvh = new CousesAdapter.CourseViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CousesAdapter.CourseViewHolder courseViewHolder, int i) {
        courseViewHolder.course_name.setText(filteredTopics.get(i).name);
        courseViewHolder.course_desc.setText(filteredTopics.get(i).desc);
    }

    @Override
    public int getItemCount() {
        return filteredTopics.size();
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();
                List<ForumTopic> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered = topics;
                } else {
                    for (ForumTopic topic : topics) {
                        if (topic.name.contains(query)) {
                            filtered.add(topic);
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
                filteredTopics = (ArrayList<ForumTopic>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
