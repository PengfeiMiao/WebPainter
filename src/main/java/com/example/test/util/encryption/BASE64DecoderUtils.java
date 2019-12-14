package com.example.test.util.encryption;

import sun.misc.CEFormatException;
import sun.misc.CEStreamExhausted;
import sun.misc.CharacterDecoder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;

/**
 * Created by ljz on 2017/3/29.
 */
public class BASE64DecoderUtils extends CharacterDecoder {

    private static final char[] PEM_ARRAY = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-'};
    private static final byte[] PEM_CONVERT_ARRAY = new byte[256];

    static {
        int var0;
        int number = 255;
        for (var0 = 0; var0 < number; ++var0) {
            PEM_CONVERT_ARRAY[var0] = -1;
        }

        for (var0 = 0; var0 < PEM_ARRAY.length; ++var0) {
            PEM_CONVERT_ARRAY[PEM_ARRAY[var0]] = (byte) var0;
        }

    }

    byte[] decodeBuffer = new byte[4];

    public BASE64DecoderUtils() {
    }

    @Override
    protected int bytesPerAtom() {
        return 4;
    }

    @Override
    protected int bytesPerLine() {
        return 72;
    }

    @Override
    protected void decodeAtom(PushbackInputStream var1, OutputStream var2, int var3) throws IOException {
        byte var5 = -1;
        byte var6 = -1;
        byte var7 = -1;
        byte var8 = -1;
        int var9 = 2;
        int var10 = 3;
        int var11 = 61;
        if (var3 < var9) {
            throw new CEFormatException("BASE64Decoder: Not enough bytes for an atom.");
        } else {
            int var4;
            do {
                var4 = var1.read();
                if (var4 == -1) {
                    throw new CEStreamExhausted();
                }
            } while (var4 == 10 || var4 == 13);

            this.decodeBuffer[0] = (byte) var4;
            var4 = this.readFully(var1, this.decodeBuffer, 1, var3 - 1);
            if (var4 == -1) {
                throw new CEStreamExhausted();
            } else {
                if (var3 > var10 && this.decodeBuffer[var10] == var11) {
                    var3 = 3;
                }

                if (var3 > var9 && this.decodeBuffer[var9] == var11) {
                    var3 = 2;
                }

                switch (var3) {
                    case 4:
                        var8 = PEM_CONVERT_ARRAY[this.decodeBuffer[3] & 255];
                    case 3:
                        var7 = PEM_CONVERT_ARRAY[this.decodeBuffer[2] & 255];
                    case 2:
                        var6 = PEM_CONVERT_ARRAY[this.decodeBuffer[1] & 255];
                        var5 = PEM_CONVERT_ARRAY[this.decodeBuffer[0] & 255];
                    default:
                        switch (var3) {
                            case 2:
                                var2.write((byte) (var5 << 2 & 252 | var6 >>> 4 & 3));
                                break;
                            case 3:
                                var2.write((byte) (var5 << 2 & 252 | var6 >>> 4 & 3));
                                var2.write((byte) (var6 << 4 & 240 | var7 >>> 2 & 15));
                                break;
                            case 4:
                                var2.write((byte) (var5 << 2 & 252 | var6 >>> 4 & 3));
                                var2.write((byte) (var6 << 4 & 240 | var7 >>> 2 & 15));
                                var2.write((byte) (var7 << 6 & 192 | var8 & 63));
                            default:
                        }

                }
            }
        }
    }
}
