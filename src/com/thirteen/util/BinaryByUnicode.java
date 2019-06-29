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
     * ����Ӧ�ò���������̨����
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
     * ֱ�����������ַ���
     * @param str
     */
   public void print(String str){
        this.conversion(str);
   }
    /**
     * ֪ͨ����̨��ӡ��Ӧͼ��
     * @param s
     */
    private void printBanner(String s) {
        System.out.println("----------------------------------------------------------");
        switch (s) {
            case START:
                System.out.println("  *\t\t\t\t\t\t ������ת�����ַ���");
                break;
            case END:
                System.out.println("  *\t\t\t\t\t\t �����˳���");
                break;
        }
        System.out.println("----------------------------------------------------------");
    }

    /**
     * ת���������ݣ��ڿ���̨������
     * @param next
     */
    private void conversion(String next) {
        String header = createHeader();
        System.out.print(header);
        System.out.println(fillRow(next));
    }

    /**
     * ����һ���������ͷ����Ϣ
     * @return
     */
    private String createHeader() {

        StringBuilder line = new StringBuilder();
        createRow(line, HEADER, true);
        return line.toString();
    }

    /**
     * ����һ�в�������������Ԫ��isHead������֪ͨ�Ƿ���Ҫ�еĶ����ָ���
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
     * ����һ�в�������������Ԫ��û�ж����ָ���
     * @param sb
     * @param content
     */
    private void createRow(StringBuilder sb, String[] content) {
        this.createRow(sb,content,false);
    }

    /**
     * ������Ԫ�񲢽��������
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
     * �����ָ���
     * @param line
     */
    private void drawLine(StringBuilder line) {
        for (int i = 0; i < this.rowWidth(); ++i) {
            line.append(LINE_ATOM);
        }
        line.append(LF_L);
    }

    /**
     * ���ַ��������ַ���֣�
     *  ��ȡ�����ַ���ԭ�롢Unicode��Hex��ʮ�����ƣ���Binary�������ƣ���䵽����
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
     * ��ʽ����Ԫ�����ʽ���֣���������ʾ�ھ��е�λ�ã��հ״��ÿո�վλ
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
     * ��ʽ����Ԫ�����ʽ���֣���������ʾ�ھ��е�λ�ã��հ״��ÿո�վλ
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
