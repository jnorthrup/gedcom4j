/*
 * Copyright (c) 2009-2012 Matthew R. Harrah
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.gedcom4j.io;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;

/**
 * @author frizbog1
 */
public class AnselMapping {

    /**
     * The encoding mapping from characters to arrays of byte
     */
    private static ByteBuffer charToByte = ByteBuffer.allocate(0xffff);
    /**
     * The encoding mapping from characters to arrays of byte
     */
    private static CharBuffer byteToChar = CharBuffer.allocate(0xff);

    static {
        Arrays.fill(charToByte.array(), (byte) ('?' & 0xff));
        Arrays.fill(byteToChar.array(), (char) ('?' & 0xff));
        for (int i = 0; 0x7f > i; i++) {
            charToByte.put(i, (byte) (i & 0xff));
            byteToChar.put(i, (char) (i & 0xff));
        }
        charToByte.put('\u0141', (byte) 0xA1).put('\u00D8', (byte) 0xA2).put('\u0110', (byte) 0xA3).put('\u00DE', (byte) 0xA4).put('\u00C6', (byte) 0xA5).put('\u0152', (byte) 0xA6).put('\u02B9', (byte) 0xA7).put('\u00B7', (byte) 0xA8).put('\u266D', (byte) 0xA9).put('\u00AE', (byte) 0xAA).put('\u00B1', (byte) 0xAB).put('\u01A0', (byte) 0xAC).put('\u01AF', (byte) 0xAD).put('\u02BC', (byte) 0xAE).put('\u02BB', (byte) 0xB0).put('\u0142', (byte) 0xB1).put('\u00F8', (byte) 0xB2).put('\u0111', (byte) 0xB3).put('\u00FE', (byte) 0xB4).put('\u00E6', (byte) 0xB5).put('\u0153', (byte) 0xB6).put('\u02BA', (byte) 0xB7).put('\u0131', (byte) 0xB8).put('\u00A3', (byte) 0xB9).put('\u00F0', (byte) 0xBA).put('\u01A1', (byte) 0xBC).put('\u01B0', (byte) 0xBD).put('\u00B0', (byte) 0xC0).put('\u2113', (byte) 0xC1).put('\u2117', (byte) 0xC2).put('\u00A9', (byte) 0xC3).put('\u266F', (byte) 0xC4).put('\u00BF', (byte) 0xC5).put('\u00A1', (byte) 0xC6).put('\u00DF', (byte) 0xCF).put('\u0309', (byte) 0xE0).put('\u0300', (byte) 0xE1).put('\u0301', (byte) 0xE2).put('\u0302', (byte) 0xE3).put('\u0303', (byte) 0xE4).put('\u0304', (byte) 0xE5).put('\u0306', (byte) 0xE6).put('\u0307', (byte) 0xE7).put('\u0308', (byte) 0xE8).put('\u030C', (byte) 0xE9).put('\u030A', (byte) 0xEA).put('\uFE20', (byte) 0xEB).put('\uFE21', (byte) 0xEC).put('\u0315', (byte) 0xED).put('\u030B', (byte) 0xEE).put('\u0310', (byte) 0xEF).put('\u0327', (byte) 0xF0).put('\u0328', (byte) 0xF1).put('\u0323', (byte) 0xF2).put('\u0324', (byte) 0xF3).put('\u0325', (byte) 0xF4).put('\u0333', (byte) 0xF5).put('\u0332', (byte) 0xF6).put('\u0326', (byte) 0xF7).put('\u031C', (byte) 0xF8).put('\u032E', (byte) 0xF9).put('\uFE22', (byte) 0xFA).put('\uFE23', (byte) 0xFB).put('\u0313', (byte) 0xFE);

        charToByte.position(0x80);
        do {
            int position = charToByte.position();
            byte b = charToByte.get();
            if (('?' & 0xff) != (b & 0xff))


                byteToChar.put(b & 0xff, (char) (position));
        } while (charToByte.hasRemaining());
    }

    /**
     * Decode an ANSEL byte into a UTF-16 Character @param b the ANSEL byte (in int form) @return the character (in UTF-16) represented by the byte
     */
    public static char decode(int b) {
        return (char) (byteToChar.get(b) & 0xffff);
    }

    /**
     * Encode a UTF-16 character into an ANSEL byte @param c the ANSEL byte (as a char) @return the character (in UTF-16) represented by the byte
     */
    public static char encode(char c) {
        return (char) (charToByte.get(c) & 0xff);
    }

    /**
     * Private constructor prevents instantiation and subclassing
     */
    private AnselMapping() {
        super();
    }
}
