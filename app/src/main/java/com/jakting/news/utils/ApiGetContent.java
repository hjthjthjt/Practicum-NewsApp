package com.jakting.news.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jakting.news.adapter.NewsContent;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ApiGetContent {

    public NewsContent newsContent;

    public ApiGetContent(String url, final Handler myHandler) {
        Log.d(TAG, "ApiGetContent: 到了okhttp");
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            //请求失败执行的方法
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: 失败了嗷");
            }

            //请求成功执行的方法
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: 成功了嗷");
                String data = response.body().string();
                data = data.replace("data-src","src");
                Log.d("debug", "onResponse: 获得数据"+data);
                JsonObject obj = new JsonParser().parse(data).getAsJsonObject();
                Gson gson = new Gson();
                newsContent = gson.fromJson(data,NewsContent.class);

                Message msg = Message.obtain();
                msg.what = 23333;
                myHandler.sendMessage(msg);
            }
        });
    }
}
