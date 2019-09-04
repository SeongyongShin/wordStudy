package com.example.wordstudy;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.LinkedList;

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
    String url1 = "https://seongyongshin.github.io/ssystarsky/index1.html";
    //Csvget(url);
    public DownloadData(){
        db = new DatabaseHelper(mainActivity);
        db.onCreate(db.getWritableDatabase());
        new Thread() {
                    @Override
                    public void run() {
                        System.out.println("msg : 다운로드 시작");
                        Csvget(url);
                        System.out.println("msg : 첫 번째 완료 두 번째 시작");
                        Csvget(url1);
                    }
                }.start();
        //System.out.println("DB 생성 완료");
    }
    public static void Csvget(String requestURL) {
        try {
 //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
//asd
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

                //LinkedList<WordData> arr1 = new LinkedList<>();
                WordData tempData = new WordData();
                BasicResponseHandler handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                String arr[] = body.split("@");
//                WordData temp = new WordData();
//                for(int i=1;body.split("@")[i] != "9999";i += 2){
//                    temp.setName(body.split("@")[i]);
//                    temp.setMean(body.split("@")[i+1]);
//                   // System.out.println(temp.getName() + " " + temp.getMean());
//                        System.out.println(i);
//                    arr1.push(temp);
//                }
                LinkedList<WordData> list = new LinkedList<>();
                for(int i=1;!arr[i].equals("9999") ;i++) {
                    if(i%2 == 1) {
                        tempData.setName(arr[i]);
                    } else {
                        tempData.setMean(arr[i]);
                        list.add(tempData);
                    }
                }

                System.out.println("msg : 반복문 작업 완료.");
                try{
                    DatabaseHelper db1 = new DatabaseHelper(mainActivity);
                    db1.add(db1.getWritableDatabase(),list);
                }catch (Exception e){
                    System.err.println("msg : 무언가 문제 발생");
                    e.printStackTrace();
                }
            } else {
                System.out.println("msg : response is error : " + response.getStatusLine().getStatusCode());
            }
//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        } catch (Exception e){
            System.err.println(e.toString());
            e.printStackTrace();
            System.err.println("msg : 오류 발생");
        }
        System.out.println("msg : 다운 및 insert 작업 완료.");
    }

}
