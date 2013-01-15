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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author frizbog1
 * 
 */
public final class AnselMapping {

    /**
     * The encoding mapping from characters to arrays of byte
     */
    private static Map<Character, Character> charToByte = new HashMap<>();

    /**
     * The encoding mapping from characters to arrays of byte
     */
    private static Map<Character, Character> byteToChar = new HashMap<>();

    static {
        charToByte.put('\u0141',
                (char) 0xA1);
        charToByte.put('\u00D8',
                (char) 0xA2);
        charToByte.put('\u0110',
                (char) 0xA3);
        charToByte.put('\u00DE',
                (char) 0xA4);
        charToByte.put('\u00C6',
                (char) 0xA5);
        charToByte.put('\u0152',
                (char) 0xA6);
        charToByte.put('\u02B9',
                (char) 0xA7);
        charToByte.put('\u00B7',
                (char) 0xA8);
        charToByte.put('\u266D',
                (char) 0xA9);
        charToByte.put('\u00AE',
                (char) 0xAA);
        charToByte.put('\u00B1',
                (char) 0xAB);
        charToByte.put('\u01A0',
                (char) 0xAC);
        charToByte.put('\u01AF',
                (char) 0xAD);
        charToByte.put('\u02BC',
                (char) 0xAE);
        charToByte.put('\u02BB',
                (char) 0xB0);
        charToByte.put('\u0142',
                (char) 0xB1);
        charToByte.put('\u00F8',
                (char) 0xB2);
        charToByte.put('\u0111',
                (char) 0xB3);
        charToByte.put('\u00FE',
                (char) 0xB4);
        charToByte.put('\u00E6',
                (char) 0xB5);
        charToByte.put('\u0153',
                (char) 0xB6);
        charToByte.put('\u02BA',
                (char) 0xB7);
        charToByte.put('\u0131',
                (char) 0xB8);
        charToByte.put('\u00A3',
                (char) 0xB9);
        charToByte.put('\u00F0',
                (char) 0xBA);
        charToByte.put('\u01A1',
                (char) 0xBC);
        charToByte.put('\u01B0',
                (char) 0xBD);
        charToByte.put('\u00B0',
                (char) 0xC0);
        charToByte.put('\u2113',
                (char) 0xC1);
        charToByte.put('\u2117',
                (char) 0xC2);
        charToByte.put('\u00A9',
                (char) 0xC3);
        charToByte.put('\u266F',
                (char) 0xC4);
        charToByte.put('\u00BF',
                (char) 0xC5);
        charToByte.put('\u00A1',
                (char) 0xC6);
        charToByte.put('\u00DF',
                (char) 0xCF);
        charToByte.put('\u0309',
                (char) 0xE0);
        charToByte.put('\u0300',
                (char) 0xE1);
        charToByte.put('\u0301',
                (char) 0xE2);
        charToByte.put('\u0302',
                (char) 0xE3);
        charToByte.put('\u0303',
                (char) 0xE4);
        charToByte.put('\u0304',
                (char) 0xE5);
        charToByte.put('\u0306',
                (char) 0xE6);
        charToByte.put('\u0307',
                (char) 0xE7);
        charToByte.put('\u0308',
                (char) 0xE8);
        charToByte.put('\u030C',
                (char) 0xE9);
        charToByte.put('\u030A',
                (char) 0xEA);
        charToByte.put('\uFE20',
                (char) 0xEB);
        charToByte.put('\uFE21',
                (char) 0xEC);
        charToByte.put('\u0315',
                (char) 0xED);
        charToByte.put('\u030B',
                (char) 0xEE);
        charToByte.put('\u0310',
                (char) 0xEF);
        charToByte.put('\u0327',
                (char) 0xF0);
        charToByte.put('\u0328',
                (char) 0xF1);
        charToByte.put('\u0323',
                (char) 0xF2);
        charToByte.put('\u0324',
                (char) 0xF3);
        charToByte.put('\u0325',
                (char) 0xF4);
        charToByte.put('\u0333',
                (char) 0xF5);
        charToByte.put('\u0332',
                (char) 0xF6);
        charToByte.put('\u0326',
                (char) 0xF7);
        charToByte.put('\u031C',
                (char) 0xF8);
        charToByte.put('\u032E',
                (char) 0xF9);
        charToByte.put('\uFE22',
                (char) 0xFA);
        charToByte.put('\uFE23',
                (char) 0xFB);
        charToByte.put('\u0313',
                (char) 0xFE);

        // Derive the reverse mapping from the original
        for (Entry<Character, Character> e : charToByte.entrySet()) {
            byteToChar.put(e.getValue(), e.getKey());
        }
    }

    /**
     * Decode an ANSEL byte into a UTF-16 Character
     * 
     * @param b
     *            the ANSEL byte (in int form)
     * @return the character (in UTF-16) represented by the byte
     */
    public static char decode(int b) {
        if (b < 0x80) {
            return (char) b;
        }
        Character result = byteToChar.get((char) b);
        if (result == null) {
            // Map unmappable characters to a question mark
            return '?';
        }
        return result;
    }

    /**
     * Encode a UTF-16 character into an ANSEL byte
     * 
     * @param c
     *            the ANSEL byte (as a char)
     * @return the character (in UTF-16) represented by the byte
     */
    public static char encode(char c) {
        Character b = charToByte.get(c);
        if (b != null) {
            return b;
        }
        return c;
    }

    /**
     * Private constructor prevents instantiation and subclassing
     */
    private AnselMapping() {
        super();
    }

}
