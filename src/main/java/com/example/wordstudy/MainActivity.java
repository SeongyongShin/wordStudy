package com.example.wordstudy;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{
    static MainActivity mainActivity;
    final DatabaseHelper db = new DatabaseHelper(this);
    DownloadData dd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = MainActivity.this;
        System.out.println("앱 시작");
        setContentView(R.layout.intro);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.intro1 :
                setContentView(R.layout.activity_main);
                break;
            case R.id.downLoad:
                dd = new DownloadData();
                break;
            case R.id.delete:
                db.dropTable(db.getWritableDatabase());
                break;

        }

    }
}
