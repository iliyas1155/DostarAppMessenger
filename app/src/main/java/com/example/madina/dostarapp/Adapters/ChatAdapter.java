package com.example.madina.dostarapp.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.madina.dostarapp.Items.ChatMessage;
import com.example.madina.dostarapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder>{
    private List<ChatMessage> messagesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView messageForm;
        public TextView title, time, user;

        public MyViewHolder(View view) {
            super(view);
            messageForm = view.findViewById(R.id.cm);
            title = view.findViewById(R.id.message_text);
            time = view.findViewById(R.id.message_time);
            user = view.findViewById(R.id.message_user);
        }
    }


    public ChatAdapter(List<ChatMessage> messagesList) {
        this.messagesList = messagesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_form, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ChatMessage message = messagesList.get(position);
        Date date = new Date(message.getMessageTime());
        holder.title.setText(message.getMessageText());
        holder.user.setText(message.getMessageUser());
        holder.time.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date));
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }
}
