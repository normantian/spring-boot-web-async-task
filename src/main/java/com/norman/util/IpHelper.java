package com.norman.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/26 9:23 AM.
 */
public class IpHelper {
    public static String getIp() throws SocketException {
        return Collections.list(NetworkInterface.getNetworkInterfaces()).stream()
                .flatMap(i -> Collections.list(i.getInetAddresses()).stream())
                .filter(ip -> ip instanceof Inet4Address)
                .findFirst()//.orElse(InetAddress.getLocalHost())
                .orElseThrow(RuntimeException::new)
                .getHostAddress();
    }

//    public static void main(String[] args) throws Exception {
//
//        final long startTime = System.nanoTime();
//        System.out.println(IpHelper.getIp());
//        System.out.println("use time = " + (System.nanoTime()-startTime));
//
//        final long startTime2 = System.nanoTime();
//        System.out.println(InetAddress.getLocalHost().getHostAddress());
//        System.out.println("use time = " + (System.nanoTime()-startTime2));
//
//
//    }

}
