package com.wangyd.firstcode.ch12;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wangyd.firstcode.R;
import com.wangyd.firstcode.ch03.listview.Fruit;

import java.util.List;

/**
 * Created by wangyd on 2017/6/26.
 */

public class FruitCAdapter extends RecyclerView.Adapter<FruitCAdapter.ViewHolder> {

    private List<Fruit> mList;
    private Context mCxt;

    public FruitCAdapter(List<Fruit> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mCxt == null)
            mCxt = parent.getContext();

        View v = LayoutInflater.from(mCxt)
                .inflate(R.layout.item_fruit_cardview, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Fruit fruit = mList.get(position);
                Intent intent = new Intent(mCxt, FruitCActivity.class);
                intent.putExtra(FruitCActivity.FRUIT_NAME, fruit.getName());
                intent.putExtra(FruitCActivity.FRUIT_IMAGE_ID, fruit.getImageId());
                mCxt.startActivity(intent);
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = mList.get(position);
        //holder.image.setImageResource(fruit.getImageId());
        Glide.with(mCxt).load(fruit.getImageId()).into(holder.image);
        holder.name.setText(fruit.getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView image;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            image = (ImageView) view.findViewById(R.id.fruit_image);
            name = (TextView) view.findViewById(R.id.fruit_name);
        }
    }
}
