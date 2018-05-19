package com.example.madina.dostarapp.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.madina.dostarapp.R;

import java.util.List;

public class AdminSupportAdapter extends RecyclerView.Adapter<AdminSupportAdapter.EmailViewHolder> {
    private List<String> emails;

    public AdminSupportAdapter(List<String> emails){
        this.emails = emails;
    }

    @Override
    public EmailViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        EmailViewHolder pvh = new EmailViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(EmailViewHolder TopicViewHolder, int i) {
        TopicViewHolder.email.setText(emails.get(i));
    }

    @Override
    public int getItemCount() {
        return emails.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class EmailViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView email;
        EmailViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            email = itemView.findViewById(R.id.item_name);
        }
    }
}
