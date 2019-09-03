package com.example.wordstudy;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

import static com.example.wordstudy.MainActivity.mainActivity;

public class DownloadData extends AppCompatActivity{

    private DatabaseHelper db;
    boolean downloading = true;
    Thread thread;

    String url = "https://seongyongshin.github.io/ssystarsky/index.html";
    //Csvget(url);
    public DownloadData(){
        db = new DatabaseHelper(mainActivity);
        db.onCreate(db.getWritableDatabase());
        new Thread() {
                    @Override
                    public void run() {
                        System.out.println("다운로드 시작");
                        Csvget(url);
                    }
                }.start();
        //System.out.println("DB 생성 완료");
    }
    public static void Csvget(String requestURL) {
        try {
 //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

            AsyncTask<String, Void, HttpResponse> asyncTask = new AsyncTask<String, Void, HttpResponse>() {
                @Override
                protected HttpResponse doInBackground(String... requestURL) {
                    HttpGet request = new HttpGet(requestURL[0]);
                    HttpResponse response = null;
                    try {
                        response = new DefaultHttpClient().execute(request);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return response;
                }
            };



            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpGet getRequest = new HttpGet(requestURL); //GET 메소드 URL 생성

           // HttpResponse response = client.execute(getRequest);
            HttpResponse response = asyncTask.execute(requestURL).get();
            //6763 단어
            //Response 출력
            if (response.getStatusLine().getStatusCode() == 200) {
                ArrayList<WordData> list = new ArrayList<>();
                WordData tempData = new WordData();
                BasicResponseHandler handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                String arr[] = body.split("@");
                for(int i=1; ;i++) {
                    if(arr[i].equals("9999")){
                        System.out.println(i/2 + "개");
                        break;
                    }
                    if(i%2 == 1) {
                        tempData.setName(arr[i]);
                        System.out.println(arr[i]);
                    }
                    else {
                        tempData.setMean(arr[i]);
                        list.add(tempData);
                    }
                }
                DatabaseHelper db1 = new DatabaseHelper(mainActivity);
                db1.add(db1.getWritableDatabase(),list);
            } else {
                System.out.println("response is error : " + response.getStatusLine().getStatusCode());
            }
//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        } catch (Exception e){
            System.err.println(e.toString());
        }
        System.out.println("finish");
    }

}
