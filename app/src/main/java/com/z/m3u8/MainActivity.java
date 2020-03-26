package com.z.m3u8;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jaygoo.library.m3u8downloader.M3U8Downloader;
import jaygoo.library.m3u8downloader.M3U8DownloaderConfig;
import jaygoo.library.m3u8downloader.OnM3U8DownloadListener;
import jaygoo.library.m3u8downloader.bean.M3U8Task;
import jaygoo.library.m3u8downloader.utils.AES128Utils;
import jaygoo.library.m3u8downloader.utils.M3U8Log;

import jaygoo.library.m3u8downloader.utils.MUtils;
import jaygoo.m3u8downloader.FullScreenActivity;
import jaygoo.m3u8downloader.StorageUtils;
import jaygoo.m3u8downloader.VideoListAdapter;

public class MainActivity extends AppCompatActivity {
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };


    List<M3U8Task> m3U8TaskList = new ArrayList<>();
    private VideoListAdapter adapter;
    private String dirPath;
    private String encryptKey = "63F06F99D823D33AAB89A0A93DECFEE0"; //get the key by AES128Utils.getAESKey()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestAppPermissions();
        try {
            M3U8Log.d("AES BASE64 Random Key:" + AES128Utils.getAESKey());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        dirPath = Constant.PATH;
       // dirPath = this.getExternalFilesDir(null).getPath() ;
        Log.d("M3U8Log", "exists: " + (new File(dirPath).exists()));
        Log.d("M3U8Log", dirPath);
        //common config !
        M3U8DownloaderConfig
                .build(getApplicationContext())
                .setSaveDir(dirPath)
                .setDebugMode(true);

        // add listener
        M3U8Downloader.getInstance().setOnM3U8DownloadListener(onM3U8DownloadListener);
        M3U8Downloader.getInstance().setEncryptKey(encryptKey);
        initData();
        initListView();

        findViewById(R.id.clear_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MUtils.clearDir(new File(dirPath));
                adapter.notifyDataSetChanged();
            }
        });

        final EditText editText = findViewById(R.id.editText);

        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if (text.isEmpty()) return;

                m3U8TaskList.add(new M3U8Task(text));

                adapter.notifyDataSetChanged();

            }
        });

        findViewById(R.id.btnDownloadSuccess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DownloadSuccessActivity.class));
            }
        });
    }


    private void initListView(){
        adapter = new VideoListAdapter(this, R.layout.list_item, m3U8TaskList);
        ListView listView =  findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = m3U8TaskList.get(position).getUrl();
                if (M3U8Downloader.getInstance().checkM3U8IsExist(url)) {
                    Toast.makeText(getApplicationContext(), "本地文件已下载，正在播放中！！！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, FullScreenActivity.class);
                    intent.putExtra("M3U8_URL", M3U8Downloader.getInstance().getM3U8Path(url));
                    startActivity(intent);
                } else {

                    M3U8Downloader.getInstance().download(url);
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                m3U8TaskList.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });


    }

    private void initData() {

        M3U8Task bean1 = new M3U8Task("https://www3.laqddc.com/hls/2018/04/07/BQ2cqpyZ/playlist.m3u8");
       // m3U8TaskList.add(bean0);
        m3U8TaskList.add(bean1);


    }

    private OnM3U8DownloadListener onM3U8DownloadListener = new OnM3U8DownloadListener() {

        @Override
        public void onDownloadItem(M3U8Task task, long itemFileSize, int totalTs, int curTs) {
            super.onDownloadItem(task, itemFileSize, totalTs, curTs);
        }

        @Override
        public void onDownloadSuccess(M3U8Task task) {
            super.onDownloadSuccess(task);
            adapter.notifyChanged(m3U8TaskList, task);
        }

        @Override
        public void onDownloadPending(M3U8Task task) {
            super.onDownloadPending(task);
            notifyChanged(task);
        }

        @Override
        public void onDownloadPause(M3U8Task task) {
            super.onDownloadPause(task);
            notifyChanged(task);
        }

        @Override
        public void onDownloadProgress(final M3U8Task task) {
            super.onDownloadProgress(task);
            notifyChanged(task);
        }

        @Override
        public void onDownloadPrepare(final M3U8Task task) {
            super.onDownloadPrepare(task);
            notifyChanged(task);

        }

        @Override
        public void onDownloadError(final M3U8Task task, Throwable errorMsg) {
            super.onDownloadError(task, errorMsg);
            notifyChanged(task);
        }

    };

    private void notifyChanged(final M3U8Task task) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyChanged(m3U8TaskList, task);
            }
        });

    }

    private void requestAppPermissions() {
        Dexter.withActivity(this)
                .withPermissions(PERMISSIONS)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            initView();
                            Toast.makeText(getApplicationContext(), "权限获取成功", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "权限获取失败", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    }
                })
                .check();
    }
}
