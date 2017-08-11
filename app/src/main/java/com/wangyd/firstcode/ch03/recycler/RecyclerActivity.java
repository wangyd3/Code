package com.wangyd.firstcode.ch03.recycler;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wangyd.firstcode.Utils.BaseActivity;
import com.wangyd.firstcode.R;
import com.wangyd.firstcode.ch03.listview.Fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wangyd on 2017/6/10.
 */

public class RecyclerActivity extends BaseActivity {
    private List<Fruit> fruitList = new ArrayList<>();
    RecyclerAdapter adapter = null;
    XAdapter xAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        StaggeredGridLayoutManager layoutManager = new
//                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

//        StaggeredGridLayoutManager layoutManager = new
//                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(fruitList);
        xAdapter = new XAdapter(fruitList);
        recyclerView.setAdapter(xAdapter);

//        new Thread() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 1; i++) {
//                    try {
//                        sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    fruitList.remove(0);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            xAdapter.notifyDataSetChanged();
//                        }
//                    });
//                }
//            }
//        }.start();


    }

    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            Fruit apple = new Fruit(getRandomLengthName("0Apple"), R.drawable.apple_pic);
            fruitList.add(apple);
            Fruit banana = new Fruit(getRandomLengthName("1Banana"), R.drawable.banana_pic);
            fruitList.add(banana);
            Fruit orange = new Fruit(getRandomLengthName("2Orange"), R.drawable.orange_pic);
            fruitList.add(orange);
            Fruit watermelon = new Fruit(getRandomLengthName("3Watermelon"), R.drawable.watermelon_pic);
            fruitList.add(watermelon);
            Fruit pear = new Fruit(getRandomLengthName("4Pear"), R.drawable.pear_pic);
            fruitList.add(pear);
            Fruit grape = new Fruit(getRandomLengthName("5Grape"), R.drawable.grape_pic);
            fruitList.add(grape);
            Fruit pineapple = new Fruit(getRandomLengthName("6Pineapple"), R.drawable.pineapple_pic);
            fruitList.add(pineapple);
            Fruit strawberry = new Fruit(getRandomLengthName("7Strawberry"), R.drawable.strawberry_pic);
            fruitList.add(strawberry);
            Fruit cherry = new Fruit(getRandomLengthName("8Cherry"), R.drawable.cherry_pic);
            fruitList.add(cherry);
            Fruit mango = new Fruit(getRandomLengthName("9Mango"), R.drawable.mango_pic);
            fruitList.add(mango);
        }
    }

    private String getRandomLengthName(String name) {
        boolean b = true;
        if (b)
            return name;

        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(name);
        }
        return builder.toString();
    }
}
