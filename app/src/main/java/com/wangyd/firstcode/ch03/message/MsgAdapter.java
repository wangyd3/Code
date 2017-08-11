package com.wangyd.firstcode.ch03.message;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangyd.firstcode.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyd on 2017/6/13.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    List<Message> messages = new ArrayList<>();

    public MsgAdapter(List<Message> messages) {
        this.messages = messages;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView sendView;
        TextView recvView;
        LinearLayout sLayout;
        LinearLayout rLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            sLayout = (LinearLayout) view.findViewById(R.id.sLinearLayout);
            rLayout = (LinearLayout) view.findViewById(R.id.rLinearLayout);
            sendView = (TextView) view.findViewById(R.id.textSend);
            recvView = (TextView) view.findViewById(R.id.textReceive);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = messages.get(position);
        if (message.getType() == Message.MSG_TYPE_RECV) {
            //holder.sendView.setVisibility(View.GONE);
            holder.sLayout.setVisibility(View.GONE);
            holder.recvView.setText(message.getMsg());
        } else {
            //holder.recvView.setVisibility(View.GONE);
            holder.rLayout.setVisibility(View.GONE);
            holder.sendView.setText(message.getMsg());
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
