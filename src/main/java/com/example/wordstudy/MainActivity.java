package com.example.wordstudy;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{
    static MainActivity mainActivity;
    TextView downPercent;
    static int currentDownloadStatus = 0;

    final DatabaseHelper db = new DatabaseHelper(this);
    final int max = 3085;

    DownloadData dd;
    Dialog dialog;

    boolean downper = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = MainActivity.this;
        System.out.println("msg : 앱 시작");
        setContentView(R.layout.intro);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.loading);
        downPercent = (TextView)findViewById(R.id.downPercent);
        final Handler handler;
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                dialog.show();
            }
        };
        Message msg = new Message();
        handler.handleMessage(msg);
//        downPercent.setText("hihi");
//        new Thread( new Runnable(){
//
//            @Override
//            public void run() {
//
//            }
//        }).start();
//

    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.intro1 :
                setContentView(R.layout.activity_main);
                break;
            case R.id.downLoad:
                boolean a = true;
                dialog.show();
                downPercent.setText("asd");
               // dialog.setCancelable(false);
                try{
                    downPercent.setText("asda");
                }catch (Exception e){
                    e.printStackTrace();
                }

//                while(downper){
//                    if(a){dd = new DownloadData(); a = false;}
//                    downPercent.setText(""+(int)(((double)currentDownloadStatus)/(double)max*100));
//                    if(downPercent.getText().equals("100%")){
//                        break;
//                    }
//                }
                break;
            case R.id.delete:
                db.dropTable(db.getWritableDatabase());
                break;

        }

    }
}
