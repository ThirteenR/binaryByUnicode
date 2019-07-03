package com.thirteen.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;

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
    private String[] header;
    private String input;
    private List<Map> content = new ArrayList<Map>();
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
            this.input(next).print();
        }
        this.printBanner(END);
    }

    public BinaryByUnicode input(String next){
         return this;
    }

    public List getContent(){
        return this.content;
    }
    /**
     * ֱ�����������ַ���
     */
    public void print(){
        print(System.out);
    }
    public void print(FileOutputStream fos) throws IOException {
        this.loading();
        this.out(fos);
    }
    /**
     * �Զ����������ʽ
     * @param out
     */
   public void print(PrintStream out){
       this.loading();
       out(out);
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
     */
    private void out(PrintStream out) {
        String header = createHeader();
        out.print(header);
        out.println(fillRow());
    }
    /**
     * ת���������ݣ����ļ�������
     */
    private void out(FileOutputStream out) throws IOException {
        String header = createHeader();
        out.write(header.getBytes());
        out.write(fillRow().getBytes());
    }
    /**
     * ����һ���������ͷ����Ϣ
     * @return
     */
    private String createHeader() {
        StringBuilder line = new StringBuilder();
     /*   int size = this.encoders.size();
        String[] header = new String[size + HEADER.length];
        System.arraycopy(HEADER,0,header,0,HEADER.length);
        for (int i =HEADER.length;i<size+HEADER.length;++i){
            header[i] = this.encoders.get(i-HEADER.length).getType();
        }*/
        createRow(line, this.header, true);
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
     * @return
     */
    private String fillRow() {
        StringBuilder sb = new StringBuilder();
        int encodersSize = this.encoders.size();
        String[] container;
        for (int i = 0; i < this.content.size(); ++i) {
            Map<String,String> row = this.content.get(i);
            container= new String[this.header.length];
            for (int j = 0; j<container.length;++j){
                container[j] = row.get(this.header[j]);
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
    private void loading() {
        this.content.clear();
        String[] header = new String[HEADER.length + this.encoders.size()];
        System.arraycopy(HEADER,0,header,0,HEADER.length);
        for (int i = 0; i < this.input.length(); ++i) {
            Map<String, String> row = new HashMap<>();
            int c = this.input.charAt(i);
            row.put(header[0], String.valueOf((char) c));
            row.put(header[1], "" + c);
            row.put(header[2], "0x" + Integer.toHexString(c));
            row.put(header[3], Integer.toBinaryString(c));
            for (int j = 0; j < this.encoders.size(); ++j) {
                Encoder encoder = this.encoders.get(j);
                if(i==0){
                    header[4+j] = encoder.getType();
                }
                row.put(encoder.getType(), encoder.encoding(c));
            }
            this.content.add(row);
        }
        this.header = header;
    }
}
