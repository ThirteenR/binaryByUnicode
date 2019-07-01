package com.thirteen.util;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * Author: thirteen
 * date-time: 2019-06-29 18:49
 **/
public class App {
        public static void main(String[] args) throws UnsupportedEncodingException {
            UTF8Code utf8Code = new UTF8Code();
            byte[] bytes = "«h".getBytes("UTF-8");
            for (byte b:bytes) {
                System.out.print(Integer.toBinaryString(b) + "\n");
            }
           // System.out.println(utf8Code.encoding("«h".charAt(0)));
            BinaryByUnicode bu = new BinaryByUnicode();
            bu.addEncoder(utf8Code);
            bu.addEncoder(utf8Code);
            Scanner scanner = new Scanner(System.in);
            bu.print("«h");
            bu.initScanner(scanner);
    }
}
