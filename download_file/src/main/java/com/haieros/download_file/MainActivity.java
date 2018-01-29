package com.haieros.download_file;

import android.os.Bundle;
import android.os.Environment;
import android.os.RecoverySystem;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.haieros.download_file.data.net.IRequestCallBack;
import com.haieros.download_file.data.net.okhttp.OkHttpRequestManager;
import com.haieros.download_file.data.net.okhttp.file.ProgressResponseListener;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String url ="http://192.168.100.194:8080/miui.zip";
    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressResponseListener progressResponseListener = new ProgressResponseListener() {
        @Override
        public void onResponseProgress(final long bytesRead, final long contentLength, boolean done) {
            Log.e("TAG", "bytesRead:" + bytesRead);
            Log.e("TAG", "contentLength:" + contentLength);
            Log.e("TAG", "done:" + done);
            if (contentLength != -1) {
                //长度未知的情况下回返回-1
                Log.e("TAG", (100 * bytesRead) / contentLength + "% done");
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    kang_tv.setText(""+(100 * bytesRead) / contentLength + "%");
                    kang_progressBar.setProgress((int) ((100 * bytesRead) / contentLength));
                }
            });
            Log.e("TAG", "================================");
        }
    };
    private TextView kang_tv;
    private ProgressBar kang_progressBar;
    private String filePath;
    private Button kang_start;
    private String cacheDir;
    private String sdPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filePath = getExternalFilesDir("path").getPath();
        cacheDir = Environment.getDownloadCacheDirectory().getPath();
        sdPath = Environment.getExternalStorageDirectory().getPath();
        Log.e(TAG, "cacheDir:"+ sdPath);
        kang_tv = (TextView) findViewById(R.id.kang_tv);
        kang_start = (Button) findViewById(R.id.kang_start);
        kang_progressBar = (ProgressBar) findViewById(R.id.kang_progressBar);
        try {
            RecoverySystem.installPackage(MainActivity.this, new File(sdPath+ "/update.zip"));

        } catch (IOException e) {
            Log.e(TAG, "升级出错");
            e.printStackTrace();
        }
//        kang_start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                init();
//            }
//        });
    }

    private void init() {
        OkHttpRequestManager.getInstance().downloadFile(
                "http://192.168.100.194:8080/miui6gov.zip",
                "update.zip",
                cacheDir,
                progressResponseListener,
                new IRequestCallBack() {
                    @Override
                    public void onSuccess(Object response) {
                        Log.e(TAG, "下载完成");
                        try {
                            RecoverySystem.installPackage(MainActivity.this, new File(cacheDir+ "/update.zip"));

                        } catch (IOException e) {
                            Log.e(TAG, "升级出错");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e(TAG, "下载失败");
                    }
                });
    }
}
