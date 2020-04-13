package com.example.lib_network.listener;

public class DisposeDataHandle {

    public DisposeDataListener mListemer = null;
    public Class<?> mClass = null;
    public String mSource = null;//文件保存路径

    public DisposeDataHandle(DisposeDataListener mListemer) {
        this.mListemer = mListemer;
    }

    public DisposeDataHandle(DisposeDataListener mListemer, Class<?> mClass) {
        this.mListemer = mListemer;
        this.mClass = mClass;
    }

    public DisposeDataHandle(DisposeDataListener mListemer, String mSource) {
        this.mListemer = mListemer;
        this.mSource = mSource;
    }
}
