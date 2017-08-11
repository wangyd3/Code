package com.wangyd.firstcode.menu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangyd.firstcode.R;

import java.util.List;

import static com.wangyd.firstcode.R.id.textView;

/**
 * Created by wangyd on 2017/6/9.
 */

public class MenuAdapter extends ArrayAdapter<MenuItem> {
    private int resourceId;

    public MenuAdapter(Context context, int resource, List<MenuItem> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MenuItem menu = getItem(position);
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            holder = new ViewHolder();
            holder.layout = (LinearLayout) convertView.findViewById(R.id.submenuLayout);
            holder.text = (TextView) convertView.findViewById(textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(menu.getTitle());
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setOpen(!menu.isOpen());
                if (menu.isOpen()) {
                    holder.layout.setVisibility(View.VISIBLE);
                    holder.layout.removeAllViews();
                    for (final MenuItem.Item item : menu.getItems()) {
                        Button button = (Button) LayoutInflater.from(getContext()).
                                inflate(R.layout.item_button, holder.layout, false);
                        button.setText(item.getName());
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), item.getCls());
                                getContext().startActivity(intent);
                            }
                        });
                        holder.layout.addView(button);
                    }
                } else {
                    holder.layout.setVisibility(View.GONE);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView text;
        LinearLayout layout;
    }

}
