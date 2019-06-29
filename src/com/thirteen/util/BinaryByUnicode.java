package com.thirteen.util;

import java.util.Scanner;

import static com.sun.tools.javac.util.LayoutCharacters.*;

/**
 * Author: thirteen
 * date-time: 2019-06-28 21:59
 **/
public class BinaryByUnicode {
    private static final String EXIT_FLAG = "!q";
    private static final String START = "start";
    private static final String END = "end";
    private static final String RULE = "*";
    private static final char LINE_ATOM = '-';
    private static final char LF_L = (char) LF;
    private static final String[] HEADER = new String[]{"Original","Unicode","HEX","Binary"};
    private static final int CELL_WIDTH = 25;

    /**
     * 启动应用并监听控制台输入
     * @param scanner
     */
    public void initScanner(Scanner scanner) {
        this.printBanner(START);
        String next;
        while (!EXIT_FLAG.equals((next = scanner.next()))) {
            this.conversion(next);
        }
        this.printBanner(END);
    }

    /**
     * 直接输出传入的字符串
     * @param str
     */
   public void print(String str){
        this.conversion(str);
   }
    /**
     * 通知控制台打印对应图标
     * @param s
     */
    private void printBanner(String s) {
        System.out.println("----------------------------------------------------------");
        switch (s) {
            case START:
                System.out.println("  *\t\t\t\t\t\t 请输入转化的字符串");
                break;
            case END:
                System.out.println("  *\t\t\t\t\t\t 程序退出！");
                break;
        }
        System.out.println("----------------------------------------------------------");
    }

    /**
     * 转化输入内容，在控制台输出结果
     * @param next
     */
    private void conversion(String next) {
        String header = createHeader();
        System.out.print(header);
        System.out.println(fillRow(next));
    }

    /**
     * 创建一个输出表格的头部信息
     * @return
     */
    private String createHeader() {

        StringBuilder line = new StringBuilder();
        createRow(line, HEADER, true);
        return line.toString();
    }

    /**
     * 创建一行并将内容填充进单元格，isHead是用来通知是否需要行的顶部分割线
     * @param sb
     * @param content
     * @param isHead
     */
    private void createRow(StringBuilder sb, String[] content, boolean isHead) {
        if (isHead) drawLine(sb);
        drawCell(sb, content);
        drawLine(sb);
    }

    /**
     * 创建一行并将内容填充进单元格，没有顶部分割线
     * @param sb
     * @param content
     */
    private void createRow(StringBuilder sb, String[] content) {
        this.createRow(sb,content,false);
    }

    /**
     * 画出单元格并将内容填充
     * @param line
     * @param strs
     */
    private void drawCell(StringBuilder line, String[] strs) {
        for (String s : strs) {
            line.append(RULE);
            line.append(new String(layoutByByte(s)));
        }
        line.append(RULE);
        line.append(LF_L);
    }

    /**
     * 画出分割线
     * @param line
     */
    private void drawLine(StringBuilder line) {
        for (int i = 0; i < this.rowWidth(); ++i) {
            line.append(LINE_ATOM);
        }
        line.append(LF_L);
    }

    /**
     * 将字符串各个字符拆分；
     *  获取各个字符的原码、Unicode、Hex（十六进制）、Binary（二进制）填充到行中
     * @param next
     * @return
     */
    private String fillRow(String next) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < next.length(); ++i) {
            String[] container = new String[4];
            int c = next.charAt(i);
            container[0] = String.valueOf((char) c);
            container[1] = "" + c;
            container[2] = "0x" + Integer.toHexString(c);
            container[3] = Integer.toBinaryString(c);
            createRow(sb, container);
        }
        return sb.toString();
    }

    /**
     * 格式化单元格的样式布局，将内容显示在居中的位置，空白处用空格站位
     * @param s
     * @return
     */
    private char[] layoutByChar(String s) {
        char[] dest = new char[CELL_WIDTH];
        for (int i = 0; i < dest.length; ++i) {
            dest[i] = 32;
        }
        int offset = (dest.length - s.length()) / 2;
        s.getChars(0, s.length(), dest, offset);
        return dest;
    }
    /**
     * 格式化单元格的样式布局，将内容显示在居中的位置，空白处用空格站位
     * @param s
     * @return
     */
    private byte[] layoutByByte(String s) {
        byte[] dest = new byte[CELL_WIDTH];
        byte[] bytes = s.getBytes();
        for (int i = 0; i < dest.length; ++i) {
            dest[i] = 32;
        }
        int offset = (CELL_WIDTH - bytes.length) / 2;
        System.arraycopy(bytes,0,dest,offset,bytes.length);
        return dest;
    }
    private int rowWidth(){
        return (CELL_WIDTH+1)*HEADER.length+1;
    }
}
