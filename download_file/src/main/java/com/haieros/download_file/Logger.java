package com.haieros.download_file;

import android.util.Log;


/**
 * Created by Kang on 2017/3/27.
 * 日志工具
 */
public class Logger {

    private static final String TAG = Logger.class.getSimpleName();

    private static boolean isLog(){
        return true;
    }

    /**
     * Verbose log message.
     *
     * @param tag
     * @param s
     */
    public static void v(String tag, String s) {
        if (isLog()) Log.v(tag, s);
    }

    /**
     * Debug log message.
     *
     * @param tag
     * @param s
     */
    public static void d(String tag, String s) {
        if (isLog()) Log.d(tag, s);
    }

    /**
     * Info log message.
     *
     * @param tag
     * @param s
     */
    public static void i(String tag, String s) {
        if (isLog()) Log.i(tag, s);
    }

    /**
     * Warning log message.
     *
     * @param tag
     * @param s
     */
    public static void w(String tag, String s) {
        if (isLog()) Log.w(tag, s);
    }

    /**
     * Error log message.
     *
     * @param tag
     * @param s
     */
    public static void e(String tag, String s) {
        if (isLog()) Log.e(tag, s);
    }

    /**
     * Verbose log message.
     *
     * @param tag
     * @param s
     * @param e
     */
    public static void v(String tag, String s, Throwable e) {
        if (isLog()) Log.v(tag, s, e);
    }

    /**
     * Debug log message.
     *
     * @param tag
     * @param s
     * @param e
     */
    public static void d(String tag, String s, Throwable e) {
        if (isLog()) Log.d(tag, s, e);
    }

    /**
     * Info log message.
     *
     * @param tag
     * @param s
     * @param e
     */
    public static void i(String tag, String s, Throwable e) {
        if (isLog()) Log.i(tag, s, e);
    }

    /**
     * Warning log message.
     *
     * @param tag
     * @param e
     */
    public static void w(String tag, Throwable e) {
        if (isLog()) Log.w(tag, e);
    }

    /**
     * Warning log message.
     *
     * @param tag
     * @param s
     * @param e
     */
    public static void w(String tag, String s, Throwable e) {
        if (isLog()) Log.w(tag, s, e);
    }

    /**
     * Error log message.
     *
     * @param tag
     * @param s
     * @param e
     */
    public static void e(String tag, String s, Throwable e) {
        if (isLog()) Log.e(tag, s, e);
    }

    /**
     * Verbose log message with printf formatting.
     *
     * @param tag
     * @param s
     * @param args
     */
    public static void v(String tag, String s, Object... args) {
        if (isLog()) Log.v(tag, String.format(s, args));
    }

    /**
     * Debug log message with printf formatting.
     *
     * @param tag
     * @param s
     * @param args
     */
    public static void d(String tag, String s, Object... args) {
        if (isLog()) Log.d(tag, String.format(s, args));
    }

    /**
     * Info log message with printf formatting.
     *
     * @param tag
     * @param s
     * @param args
     */
    public static void i(String tag, String s, Object... args) {
        if (isLog()) Log.i(tag, String.format(s, args));
    }

    /**
     * Warning log message with printf formatting.
     *
     * @param tag
     * @param s
     * @param args
     */
    public static void w(String tag, String s, Object... args) {
        if (isLog()) Log.w(tag, String.format(s, args));
    }

    /**
     * Error log message with printf formatting.
     *
     * @param tag
     * @param s
     * @param args
     */
    public static void e(String tag, String s, Object... args) {
        if (isLog()) Log.e(tag, String.format(s, args));
    }
}
