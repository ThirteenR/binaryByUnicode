package com.thirteen.util;

import sun.awt.CharsetString;
import sun.nio.cs.ext.GBK;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Scanner;

/**
 * Author: thirteen
 * date-time: 2019-06-29 18:49
 **/
public class App {
        public static void main(String[] args) throws UnsupportedEncodingException {
            UTF8Code utf8Code = new UTF8Code();
            byte[] bytes = "«h".getBytes();
            for (byte b:bytes) {
                System.out.print(b  +  "\t"+Integer.toBinaryString(b) + "\n");
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
