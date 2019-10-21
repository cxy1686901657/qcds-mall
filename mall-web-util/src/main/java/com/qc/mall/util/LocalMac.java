package com.qc.mall.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author qc
 * @date 2019/10/21
 * @description
 * @project qcds-mall
 */

public class LocalMac {
    public static String getLocalMac() throws SocketException, UnknownHostException {
        //获取网卡，获取地址
        InetAddress ia = InetAddress.getLocalHost();
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        //System.out.println("mac数组长度："+mac.length);
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            //字节转换为整数
            int temp = mac[i] & 0xff;
            String str = Integer.toHexString(temp);
            //System.out.println("每8位:"+str);
            if (str.length() == 1) {
                sb.append("0" + str);
            } else {
                sb.append(str);
            }
        }
        return sb.toString().toUpperCase();
    }

    public static void main(String[] args) throws UnknownHostException {

//        //得到IP，输出PC-201309011313/122.206.73.83
//        InetAddress ia = InetAddress.getLocalHost();
//        String ip = ia.toString().split("/")[1];
//        System.out.println(ia);
//        System.out.println("IP:" + ip);
//        try {
//            String localMac = getLocalMac(ia);
//            System.out.println(localMac);
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }

    }
}
