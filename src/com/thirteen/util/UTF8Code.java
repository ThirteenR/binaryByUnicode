package com.thirteen.util;


/**
 * Author: rsq0113
 * Date: 2019-07-01 9:50
 * Description:
 **/
public class UTF8Code implements Encoder {
    public static final int SINGLE_MIN = 0;
    public static final int SINGLE_MAX = 0x7F;
    public static final int DOUBLE_MIN = 0x80;
    public static final int DOUBLE_MAX = 0x7FF;
    public static final int TREBLE_MIN = 0x800;
    public static final int TREBLE_MAX = 0xFFFF;
    public static final int QUADRUPLE_MIN = 0x10000;
    public static final int QUADRUPLE_MAX = 0x10FFFF;
    private byte[] singleBytes = new byte[]{48, 48, 48, 48, 48, 48, 48, 48, 0};
    private byte[] doubleBytes = new byte[]{49, 49, 48, 48, 48, 48, 48, 48, 49, 48, 48, 48, 48, 48, 48, 48, 0};
    private byte[] trebleBytes = new byte[]{49, 49, 49, 48, 48, 48, 48, 48, 49, 48, 48, 48, 48, 48, 48, 48, 49, 48, 48, 48, 48, 48, 48, 48, 0};
    private byte[] quadrupleBytes = new byte[]{49, 49, 49, 49, 48, 48, 48, 48, 49, 48, 48, 48, 48, 48, 48, 48, 49, 48, 48, 48, 48, 48, 48, 48, 49, 48, 48, 48, 48, 48, 48, 48, 0};
    private String type = "UTF-8";

    @Override
    public String encoding(int unicode) {
        resetBaseBytes();
        return utf8Code(unicode);
    }

    public void resetBaseBytes() {
        resetSingle();
        resetDouble();
        resetTreble();
        resetQuadruple();
    }

    private void resetSingle() {
        if (this.singleBytes[this.singleBytes.length - 1] == 1) {
            this.singleBytes[this.singleBytes.length - 1] = 0;
            for (int i = 0;i<this.singleBytes.length;++i){
                this.singleBytes[i] = 48;
            }
        }
    }

    private void resetDouble() {
        if (this.doubleBytes[this.doubleBytes.length - 1] == 1) {
            this.doubleBytes[this.doubleBytes.length - 1] = 0;
            for (int i = 3;i<this.doubleBytes.length-1;++i){
                if((i&7) != 0 )
                this.doubleBytes[i] = 48;
            }
        }
    }

    private void resetTreble() {
        if (this.trebleBytes[this.trebleBytes.length - 1] == 1) {
            this.trebleBytes[this.trebleBytes.length - 1] = 0;
            for (int i = 4;i<this.trebleBytes.length-1;++i){
                if((i&7) != 0 )
                    this.trebleBytes[i] = 48;
            }
        }
    }

    private void resetQuadruple() {
        if (this.quadrupleBytes[this.quadrupleBytes.length - 1] == 1) {
            this.quadrupleBytes[this.quadrupleBytes.length - 1] = 0;
            for (int i = 5;i<this.quadrupleBytes.length-1;++i){
                if((i&7) != 0 )
                    this.quadrupleBytes[i] = 48;
            }
        }
    }

    @Override
    public String getType() {
        return this.type;
    }

    public String utf8Code(int unicode) {
        if (SINGLE_MIN <= unicode && unicode <= SINGLE_MAX)
            return singleByte(unicode);
        if (DOUBLE_MIN <= unicode && unicode <= DOUBLE_MAX)
            return doubleBytes(unicode);
        if (TREBLE_MIN <= unicode && unicode <= TREBLE_MAX)
            return trebleBytes(unicode);
        if (QUADRUPLE_MIN <= unicode && unicode <= QUADRUPLE_MAX)
            return quadrupleBytes(unicode);
        return null;
    }

    private String singleByte(int unicode) {
        String s = Integer.toBinaryString(unicode);
        byte[] src = s.getBytes();
        int offset = Byte.SIZE - src.length;
        System.arraycopy(src, 0, this.singleBytes, offset, src.length);
        return new String(this.singleBytes,0,this.singleBytes.length-1);
    }

    private String doubleBytes(int unicode) {
        String s = Integer.toBinaryString(unicode);
        byte[] src = s.getBytes();
        int deviation = 11 - src.length;
        System.arraycopy(src, 0, this.doubleBytes, 3 + deviation, 5 - deviation);
        System.arraycopy(src, 5 - deviation, this.doubleBytes, 10, 6);
        this.doubleBytes[this.doubleBytes.length - 1] = 1;
        return new String(this.doubleBytes, 0, this.doubleBytes.length - 1);
    }

    private String trebleBytes(int unicode) {
        String s = Integer.toBinaryString(unicode);
        byte[] src = s.getBytes();
        int deviation = 16 - src.length;
        System.arraycopy(src, 0, this.trebleBytes, 4 + deviation, 4 - deviation);
        System.arraycopy(src, 4 - deviation, this.trebleBytes, 10, 6);
        System.arraycopy(src, 10 - deviation, this.trebleBytes, 18, 6);
        this.trebleBytes[this.trebleBytes.length - 1] = 1;
        return new String(this.trebleBytes, 0, this.trebleBytes.length - 1);
    }

    private String quadrupleBytes(int unicode) {
        String s = Integer.toBinaryString(unicode);
        byte[] src = s.getBytes();
        int deviation = 21 - src.length;
        System.arraycopy(src, 0, this.quadrupleBytes, 5 + deviation, 3 - deviation);
        System.arraycopy(src, 3 - deviation, this.quadrupleBytes, 10, 6);
        System.arraycopy(src, 9 - deviation, this.quadrupleBytes, 18, 6);
        System.arraycopy(src, 15 - deviation, this.quadrupleBytes, 26, 6);
        this.trebleBytes[this.trebleBytes.length - 1] = 1;
        return new String(this.quadrupleBytes, 0, this.quadrupleBytes.length - 1);
    }
}
