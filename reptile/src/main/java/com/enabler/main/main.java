package com.enabler.main;

import com.enabler.pachongUtils.JingDongUtils;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        JingDongUtils jingDongUtils = new JingDongUtils();
        jingDongUtils.get("矿泉水",1,5);
    }
}
