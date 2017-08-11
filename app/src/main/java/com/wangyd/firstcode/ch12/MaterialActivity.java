package com.wangyd.firstcode.ch12;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.wangyd.firstcode.Utils.BaseActivity;
import com.wangyd.firstcode.R;
import com.wangyd.firstcode.ch03.listview.Fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MaterialActivity extends BaseActivity {

    private DrawerLayout drawerLayout;

    private Fruit[] fruits = {
            new Fruit("apple", R.drawable.apple),
            new Fruit("banana", R.drawable.banana),
            new Fruit("orange", R.drawable.orange),
            new Fruit("watermelon", R.drawable.watermelon),
            new Fruit("pear", R.drawable.pear),
            new Fruit("grape", R.drawable.grape),
            new Fruit("pineapple", R.drawable.pineapple),
            new Fruit("strawberry", R.drawable.strawberry),
            new Fruit("cherry", R.drawable.cherry),
            new Fruit("mango", R.drawable.mango),
    };

    private List<Fruit> fruitList = new ArrayList<>();
    private FruitCAdapter adapter;

    private void initFruits() {
        fruitList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }


    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle("title");//这个无效
        //getSupportActionBar().setTitle("title");//这个有效果

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                return true;
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Data deleted", Snackbar.LENGTH_SHORT).setAction(
                        "undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                toast("Data restored");
                            }
                        }
                ).show();


            }
        });


        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        adapter = new FruitCAdapter(fruitList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initFruits();
                                adapter.notifyDataSetChanged();
                                swipeRefresh.setRefreshing(false);
                            }
                        });

                    }
                }).start();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toobar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                toast("backup");
                break;
            case R.id.delete:
                toast("delete");
                break;
            case R.id.settings:
                toast("settings");
                break;
            case android.R.id.home:
                toast("home");
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
        //return super.onOptionsItemSelected(item);
    }

    public void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

}
