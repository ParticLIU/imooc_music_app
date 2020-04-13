package com.example.imooc_voice.model;

public enum CHANNEL {

    MY("我的",0x01),
    DISCOVERY("发现",0x02),
    FRIEND("朋友",0x03);

    public static final int VALUE_MY = 0x01;
    public static final int VALUE_DISCOVERY = 0x02;
    public static final int VALUE_FRIEND = 0x03;

    CHANNEL(String key, int value) {
        this.key = key;
        this.value = value;
    }

    private String key;
    private int value;

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }
}
