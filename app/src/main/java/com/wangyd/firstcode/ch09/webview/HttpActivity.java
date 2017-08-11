package com.wangyd.firstcode.ch09.webview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wangyd.firstcode.R;
import com.wangyd.firstcode.Utils.BaseActivity;
import com.wangyd.firstcode.Utils.DBG;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HttpActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "HttpActivity";
    TextView textView;
    Button Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        findViewById(R.id.btnHttp).setOnClickListener(this);
        findViewById(R.id.btnOkHttp0).setOnClickListener(this);
        findViewById(R.id.btnOkHttp1).setOnClickListener(this);
        textView = (TextView) findViewById(R.id.tvHttp);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnHttp:
                sendRequestWithHttpURLConnection();
                break;
            case R.id.btnOkHttp0:
                sendRequestWithOkHttp();
                break;
            case R.id.btnOkHttp1:
                sendRequestWithOkHttp1();
                break;
        }
    }

    private void sendRequestWithHttpURLConnection() {
        DBG.log(TAG, DBG._FUNC_());
        final long t = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                showResponse(DBG._FUNC_());
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {

                    URL url = new URL("http://www.baidu.com");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    DBG.log(TAG, "t=" + (System.currentTimeMillis() - t));

                    /** 下面是POST的方法，把GET语句改为下面的就行了
                     connection.setRequestMethod("POST");
                     DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                     out.writeBytes("username=admin&password=123456");
                     */

                    int code = connection.getResponseCode();
                    DBG.log(TAG, "code=" + code);
                    DBG.log(TAG, "t0=" + (System.currentTimeMillis() - t));
                    InputStream in = connection.getInputStream();
                    DBG.log(TAG, "t1=" + (System.currentTimeMillis() - t));
                    reader = new BufferedReader(new InputStreamReader(in));/**大部分的都是有BufferedReader这个类*/
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    DBG.log(TAG, "t2=" + (System.currentTimeMillis() - t));
                    showResponse(response.toString());
                } catch (Exception e) {
                    DBG.log(TAG, "Exception:", e);
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }


    private void sendRequestWithOkHttp() {
        DBG.log(TAG, DBG._FUNC_());

        HttpUtil.sendOkHttpRequest("http://www.baidu.com", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                showResponse(data);
            }
        });
    }


    private void sendRequestWithOkHttp1() {
        DBG.log(TAG, DBG._FUNC_());
        final long t = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /**http的一个疑问是返回的response应该不是所有的内容,如果是一个4G的文件呢*/
                    /**client 调用一个 request*/
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://www.baidu.com")
                            .build();
                    Response response = client.newCall(request).execute();
                    DBG.log(TAG, "response.code()=" + response.code());
                    String responseData = response.body().string();
                    showResponse(responseData);
                } catch (Exception e) {
                    DBG.log(TAG, "Exception:", e);
                }
            }
        }).start();
    }

    private void sendRequestWithOkHttpPost() {
        DBG.log(TAG, DBG._FUNC_());
        final long t = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /**http的一个疑问是返回的response应该不是所有的内容,如果是一个4G的文件呢*/
                    /**client 调用一个 request*/
                    OkHttpClient client = new OkHttpClient();

                    RequestBody body = new FormBody.Builder()
                            .add("username", "admin")
                            .add("password", "123456")
                            .build();

                    Request request = new Request.Builder()
                            .url("http://www.baidu.com")
                            .post(body)
                            .build();

                    Response response = client.newCall(request).execute();
                    DBG.log(TAG, "response.code()=" + response.code());
                    String responseData = response.body().string();
                    showResponse(responseData);
                } catch (Exception e) {
                    DBG.log(TAG, "Exception:", e);
                }
            }
        }).start();
    }


    private void showResponse(final String response) {
        DBG.log(TAG, DBG._FUNC_() + " " + response);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //textView.setText(response);
                textView.append(response + "\n");
            }
        });
    }


}
