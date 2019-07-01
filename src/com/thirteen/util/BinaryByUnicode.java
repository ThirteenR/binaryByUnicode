package com.thirteen.util;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

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
    private static final char LF_L = '\n';
    private static final String[] HEADER = new String[]{"Original","Unicode","HEX","Binary"};
    private static final int CELL_WIDTH = 32;
    private ArrayList<Encoder> encoders = new ArrayList<Encoder>();
 public void addEncoder(Encoder encode){
     this.encoders.add(encode);
 }
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
        int size = this.encoders.size();
        String[] header = new String[size + HEADER.length];
        System.arraycopy(HEADER,0,header,0,HEADER.length);
        for (int i =HEADER.length;i<size+HEADER.length;++i){
            header[i] = this.encoders.get(i-HEADER.length).getType();
        }
        createRow(line, header, true);
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
        int encodersSize = this.encoders.size();
        String[] container;
        for (int i = 0; i < next.length(); ++i) {
            container= new String[HEADER.length+encodersSize];
            int c = next.charAt(i);
            container[0] = String.valueOf((char) c);
            container[1] = "" + c;
            container[2] = "0x" + Integer.toHexString(c);
            container[3] = Integer.toBinaryString(c);
            for (int j=HEADER.length;j<container.length;++j){
                container[j] = this.encoders.get(j-HEADER.length).encoding(c);
            }
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
        return (CELL_WIDTH+1)*(HEADER.length+this.encoders.size())+1;
    }
}
