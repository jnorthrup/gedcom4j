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
package org.gedcom4j.comparators;

import org.gedcom4j.model.Individual;
import org.gedcom4j.model.PersonalName;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator for sorting individuals by last name (surname) first, then first (given) name
 * 
 * @author frizbog1
 * 
 */
public class IndividualByLastNameFirstNameComparator implements Serializable, Comparator<Individual> {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -8121061183483337581L;

    /**
     * Compare two individuals
     * 
     * @param i1
     *            individual 1
     * @param i2
     *            individual 2
     * @return -1 if i1 < i2, 0 if i1 == i2, 1 if i1 > i2
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Individual i1, Individual i2) {

        String s1 = "-unknown-";
        String s2 = "-unknown-";
        PersonalName n1 = null;
        PersonalName n2 = null;
        if (0 < i1.names.size()) {
            n1 = i1.names.get(0);
        }
        if (0 < i2.names.size()) {
            n2 = i2.names.get(0);
        }

        if (null != n1) {
            if (null == n1.surname && null == n1.givenName) {
                if (n1.basic.contains("/")) {
                    String sn = n1.basic.substring(n1.basic.indexOf('/'));
                    String gn = n1.basic.substring(0, n1.basic.indexOf('/'));
                    s1 = sn + ", " + gn;
                }
            } else {
                s1 = n1.surname + ", " + n1.givenName;
            }
        }

        if (null != n2) {
            if (null == n2.surname && null == n2.givenName) {
                if (n2.basic.contains("/")) {
                    String sn = n2.basic.substring(n2.basic.indexOf('/'));
                    String gn = n2.basic.substring(0, n2.basic.indexOf('/'));
                    s2 = sn + ", " + gn;
                }
            } else {
                s2 = n2.surname + ", " + n2.givenName;
            }
        }

        return s1.compareTo(s2);
    }
}
