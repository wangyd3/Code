package com.wangyd.firstcode.ch03.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wangyd.firstcode.R;
import com.wangyd.firstcode.ch03.listview.Fruit;

import java.util.List;

/**
 * Created by wangyd on 2017/6/10.
 */

public class XAdapter extends RecyclerView.Adapter<XAdapter.ViewHolder> {
    List<Fruit> mListFruit;

    public XAdapter(List<Fruit> mListFruit) {
        this.mListFruit = mListFruit;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fruit, parent, false);
        final ViewHolder holder = new ViewHolder(view);


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                String name = mListFruit.get(pos).getName();
                Toast.makeText(v.getContext(), "click image\n" + name, Toast.LENGTH_SHORT).show();
            }
        });

        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                String name = mListFruit.get(pos).getName();
                Toast.makeText(v.getContext(), "click name\n" + name, Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = mListFruit.get(position);
        holder.text.setText(fruit.getName());
        holder.image.setImageResource(fruit.getImageId());
    }


    @Override
    public int getItemCount() {
        return mListFruit.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView image;
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            image = (ImageView) itemView.findViewById(R.id.imageView);
            text = (TextView) itemView.findViewById(R.id.textView);
        }
    }

}
