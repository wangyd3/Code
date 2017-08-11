package com.wangyd.firstcode.ch09.webview;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wangyd.firstcode.R;
import com.wangyd.firstcode.Utils.BaseActivity;
import com.wangyd.firstcode.Utils.DBG;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ParseActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "ParseActivity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse);
        findViewById(R.id.pull).setOnClickListener(this);
        findViewById(R.id.sax).setOnClickListener(this);
        findViewById(R.id.json).setOnClickListener(this);
        findViewById(R.id.gson).setOnClickListener(this);

        textView = (TextView) findViewById(R.id.tvContent);
    }

    private int mode = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pull:
                mode = 0;
                sendRequestWithOkHttp();
                break;
            case R.id.sax:
                mode = 1;
                sendRequestWithOkHttp();
                break;
            case R.id.json:
                mode = 2;
                sendRequestWithOkHttp();
                break;
            case R.id.gson:
                mode = 3;
                sendRequestWithOkHttp();
                break;
        }
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "http://192.168.1.102:8080/myweb/get_data.xml";
                    if (mode == 2 || mode == 3)
                        url = "http://192.168.1.102:8080/myweb/get_data.json";

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                    showResponse(responseData);

                    if (mode == 0)
                        parseXMLWithPull(responseData);
                    else if (mode == 1)
                        parseXMLWithSax(responseData);
                    else if (mode == 2)
                        parseJSONWithJSONObject(responseData);
                    else if (mode == 3)
                        parseJSONWithGSON(responseData);
                } catch (Exception e) {
                    DBG.log(TAG, "", e);
                }
            }
        }).start();
    }


    private void showResponse(final String response) {
        DBG.log(TAG, DBG._FUNC_() + " " + response);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(response);
            }
        });
    }

    private void parseXMLWithSax(String xmlData) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader reader = factory.newSAXParser().getXMLReader();
            SaxHandler handler = new SaxHandler();
            reader.setContentHandler(handler);
            reader.parse(new InputSource(new StringReader(xmlData)));
        } catch (Exception e) {
            DBG.log(TAG, "", e);
        }
    }


    private void parseXMLWithPull(String xmlData) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if ("app".equals(nodeName)) {
                            DBG.log(TAG, "id=" + id);
                            DBG.log(TAG, "name=" + name);
                            DBG.log(TAG, "version=" + version);
                        }
                        break;
                }
                eventType = xmlPullParser.next();
            }

        } catch (Exception e) {
            DBG.log(TAG, "", e);
        }
    }


    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.getString("id");
                String name = object.getString("name");
                String version = object.getString("version");
                DBG.log(TAG, "JSON.id=" + id);
                DBG.log(TAG, "JSON.name=" + name);
                DBG.log(TAG, "JSON.version=" + version);
            }
        } catch (Exception e) {
            DBG.log(TAG, "", e);
        }

    }

    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        List<App> appList = gson.fromJson(jsonData, new TypeToken<List<App>>() {
        }.getType()); /**fromJson主要是传入数据类型。类或者 List<app>*/

        for (App app : appList) {
            DBG.log(TAG, "GSON.id=" + app.getId());
            DBG.log(TAG, "GSON.name=" + app.getName());
            DBG.log(TAG, "GSON.version=" + app.getVersion());
        }
    }
}
