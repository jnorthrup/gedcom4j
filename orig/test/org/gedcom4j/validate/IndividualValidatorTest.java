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
package org.gedcom4j.validate;

import java.util.Map;

import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.validate.AbstractValidator;
import org.gedcom4j.validate.GedcomValidationFinding;
import org.gedcom4j.validate.GedcomValidator;
import org.gedcom4j.validate.IndividualValidator;
import org.gedcom4j.validate.Severity;


/**
 * Tests for {@link IndividualValidator}
 * 
 * @author frizbog1
 * 
 */
public class IndividualValidatorTest extends AbstractValidatorTestCase {

    /**
     * Test for a default individual (no xref)
     */
    public void testValidateIndividual() {
        Individual i = new Individual();
        AbstractValidator v = new IndividualValidator(rootValidator, i);
        v.validate();
        assertFindingsContain(Severity.ERROR, "xref", "null");
    }

    /**
     * Test for {@link GedcomValidator#validateIndividuals()} with a malformed
     * xref on an individual, which does not match its key in the individuals
     * map
     */
    public void testValidateIndividuals2() {
        Gedcom g = new Gedcom();

        // Deliberately introduce a problem
        Individual i = new Individual();
        i.xref = "FryingPan";
        g.individuals.put("WrongKey", i);

        // Go validate
        GedcomValidator v = new GedcomValidator(g);
        v.validate();

        // Assert stuff
        assertEquals("There should be one finding", 1, v.findings.size());
        GedcomValidationFinding f = v.findings.get(0);
        assertNotNull(f);
        assertNotNull("The finding should have an object attached",
                f.itemWithProblem);
        assertTrue("The object attached should be a Map entry",
                f.itemWithProblem instanceof Map.Entry);
        assertEquals("The finding should be at severity ERROR", Severity.ERROR,
                f.severity);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

}
