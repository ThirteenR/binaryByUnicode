package com.thirteen.util;

import sun.awt.CharsetString;
import sun.nio.cs.ext.GBK;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 * Author: thirteen
 * date-time: 2019-06-29 18:49
 **/
public class App {
        public static void main(String[] args) throws IOException {
            BinaryByUnicode bu = new BinaryByUnicode();
            bu.addEncoder(new UTF8Code());
            File file = new File("D:/out.txt");
            file.createNewFile();
            Scanner scanner = new Scanner(System.in);
            bu.initScanner(scanner);
            bu.input("a").print(new FileOutputStream(file,true));
    }
}
