package com.wangyd.firstcode.menu;

import android.os.Bundle;
import android.widget.ListView;

import com.wangyd.firstcode.R;
import com.wangyd.firstcode.Utils.BaseActivity;
import com.wangyd.firstcode.ch02.lifecycle.FirstActivity;
import com.wangyd.firstcode.ch03.customview.CustomActivity;
import com.wangyd.firstcode.ch03.listview.FruitActivity;
import com.wangyd.firstcode.ch03.message.MessageActivity;
import com.wangyd.firstcode.ch03.recycler.RecyclerActivity;
import com.wangyd.firstcode.ch06.litepal.LitepalActivity;
import com.wangyd.firstcode.ch07.permission.CallActivity;
import com.wangyd.firstcode.ch08.camera.CameraAlbumActivity;
import com.wangyd.firstcode.ch08.notification.NotificationActivity;
import com.wangyd.firstcode.ch09.webview.HttpActivity;
import com.wangyd.firstcode.ch09.webview.ParseActivity;
import com.wangyd.firstcode.ch09.webview.WebViewActivity;
import com.wangyd.firstcode.ch10.service.DownloadActivity;
import com.wangyd.firstcode.ch10.service.ServiceActivity;
import com.wangyd.firstcode.ch11.LBSActivity;
import com.wangyd.firstcode.ch11.UsbActivity;
import com.wangyd.firstcode.ch12.MaterialActivity;
import com.wangyd.firstcode.zz.FileActivity;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends BaseActivity {

    private List<MenuItem> menuItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ListView listView = (ListView) findViewById(R.id.listView);
        setMenus();
        MenuAdapter adapter = new MenuAdapter(this, R.layout.item_menu, menuItems);
        listView.setAdapter(adapter);
    }


    private void setMenus() {
        List<MenuItem.Item> items = new ArrayList<>();
        items.add(new MenuItem.Item("LifeCycle", FirstActivity.class));
        MenuItem menuItem = new MenuItem("CH02-", items);
        menuItems.add(menuItem);

        items = new ArrayList<>();
        items.add(new MenuItem.Item("ListView", FruitActivity.class));
        items.add(new MenuItem.Item("RecyclerView", RecyclerActivity.class));
        items.add(new MenuItem.Item("QQMessage", MessageActivity.class));
        items.add(new MenuItem.Item("CustomView", CustomActivity.class));
        menuItem = new MenuItem("CH03-", items);
        menuItems.add(menuItem);

        items = new ArrayList<>();
        items.add(new MenuItem.Item("LitePal", LitepalActivity.class));
        menuItem = new MenuItem("CH06-", items);
        menuItems.add(menuItem);

        items = new ArrayList<>();
        items.add(new MenuItem.Item("permission", CallActivity.class));
        menuItem = new MenuItem("CH07-", items);
        menuItems.add(menuItem);

        items = new ArrayList<>();
        items.add(new MenuItem.Item("notification", NotificationActivity.class));
        items.add(new MenuItem.Item("camera_album", CameraAlbumActivity.class));
        menuItem = new MenuItem("CH08-", items);
        menuItems.add(menuItem);

        items = new ArrayList<>();
        items.add(new MenuItem.Item("WebView", WebViewActivity.class));
        items.add(new MenuItem.Item("Http", HttpActivity.class));
        items.add(new MenuItem.Item("parse", ParseActivity.class));
        menuItem = new MenuItem("CH09-network", items);
        menuItems.add(menuItem);

        items = new ArrayList<>();
        items.add(new MenuItem.Item("Service", ServiceActivity.class));
        items.add(new MenuItem.Item("Download", DownloadActivity.class));
        menuItem = new MenuItem("CH10-service", items);
        menuItems.add(menuItem);

        items = new ArrayList<>();
        items.add(new MenuItem.Item("LBS", LBSActivity.class));
        menuItem = new MenuItem("CH11-LBS", items);
        menuItems.add(menuItem);


        items = new ArrayList<>();
        items.add(new MenuItem.Item("Material", MaterialActivity.class));
        menuItem = new MenuItem("CH12-MaterialDesign", items);
        menuItems.add(menuItem);

        items = new ArrayList<>();
        items.add(new MenuItem.Item("USB", UsbActivity.class));
        items.add(new MenuItem.Item("FILE", FileActivity.class));
        menuItem = new MenuItem("CHZZ-OTHER", items);
        menuItems.add(menuItem);

    }


}
