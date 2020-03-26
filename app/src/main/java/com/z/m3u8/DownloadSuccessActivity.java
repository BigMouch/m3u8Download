package com.z.m3u8;

import androidx.appcompat.app.AppCompatActivity;
import jaygoo.library.m3u8downloader.M3U8Downloader;
import jaygoo.m3u8downloader.FullScreenActivity;
import jaygoo.m3u8downloader.VideoListAdapter;
import jaygoo.m3u8downloader.VideoSuccessListAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

public class DownloadSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_success);

        File file=new File(Constant.PATH);
        final String[] filelist=file.list();

        VideoSuccessListAdapter adapter = new VideoSuccessListAdapter(this, R.layout.list_item, filelist);
        ListView listView=findViewById(R.id.listSuccess);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = Constant.PATH+filelist[i]+"/local.m3u8";
                if (new File(url).exists()) {
                    Toast.makeText(getApplicationContext(), "本地文件已下载，正在播放中！！！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DownloadSuccessActivity.this, FullScreenActivity.class);
                    intent.putExtra("M3U8_URL", url);
                    startActivity(intent);
                }
            }
        });
    }
}
