package com.example.imooc_voice.util;

import android.util.Log;

public class Logger {
    public enum INFO{
        HOMEMAIN
    }

    public static final void e(INFO info,String msg){
        Log.e(info.toString(),msg);
    }

}
