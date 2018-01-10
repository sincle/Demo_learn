package com.haieros.demo_learn.crash;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * UncaughtException处理类 当程序发生uncaught 异常时接管程序
 * 由该类接管程序 并发送错误报告
 * 在application 中 注册 从程序开始便开始监测
 * Created by Kang on 2018/1/10.
 */

public class CrashHandler implements UncaughtExceptionHandler {


    private static final String TAG = CrashHandler.class.getSimpleName();
    private static final String APP_CACHE_PATH = Environment.getExternalStorageDirectory().getPath() +
            "/YoungHeart/crash/";

    //系统默认的UncaughtException 处理类
    private UncaughtExceptionHandler mDefaultHandler;

    //crashHandler示例
    private static CrashHandler instance;

    private Context context;

    //用来储存设备信息和异常信息
    private Map<String,String> infos = new HashMap<>();

    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private CrashHandler(){

    }

    public static CrashHandler getInstance(){
        if(instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }
    public void init(Context context){
        //获取系统默认的UncaughtExcepation 处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        //设置CrashHandler 为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 切换发生Crash 所在的Activity
     * @param context
     */
    public void swithCrashActivity(Context context){

        this.context = context;
    }

    /**
     *
     * UncaughtExcepation 发生时会转入此方法处理
     * @param t
     * @param e
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {

        Log.e(TAG, "uncaughtException");
        if(!handleException(e) && mDefaultHandler != null) {
            //如果用户没有处理 则让系统默认处理
            mDefaultHandler.uncaughtException(t,e);
        }else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                Log.e(TAG,"error:"+e1);
            }
            //exit
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息
     * @param e
     * @return
     */
    private boolean handleException(Throwable e) {

        Log.e(TAG, "handleException");
        if(e == null) {
            return false;
        }
        new Thread(){
            public void run(){
                Log.e(TAG, "----------------");
                Looper.prepare();
                Toast.makeText(context, "即将推出", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        //saveCrachInfoInFile(e);
        return true;
    }


    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称,便于将文件传送到服务器
     */
    private String saveCrachInfoInFile(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";

            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                File dir = new File(APP_CACHE_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(APP_CACHE_PATH + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }

            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }
}
