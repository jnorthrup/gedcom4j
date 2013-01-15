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
package org.gedcom4j.parser;

import org.gedcom4j.model.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Class for parsing GEDCOM 5.5 files and creating a {@link Gedcom} structure from them.
 * </p>
 * <p>
 * General usage is as follows:
 * <ol>
 * <li>Instantiate a <code>GedcomParser</code> object</li>
 * <li>Call the <code>GedcomParser.load()</code> method (in one of its various forms) to parse a file/stream</li>
 * <li>Access the parser's <code>gedcom</code> property to access the parsed data</li>
 * </ol>
 * </p>
 * <p>
 * It is <b>highly recommended</b> that after calling the <code>GedcomParser.load()</code> method, the user check the
 * {@link GedcomParser#errors} and {@link GedcomParser#warnings} collections to see if anything problematic was
 * encountered in the data while parsing. Most commonly, the <code>warnings</code> collection will have information
 * about tags from GEDCOM 5.5.1 that were specified in a file that was designated as a GEDCOM 5.5 file. When this
 * occurs, the data is loaded, but will not be able to be written by {@link org.gedcom4j.writer.GedcomWriter} until the
 * version number in the <code>gedcomVersion</code> field of {@link Gedcom#header} is updated to
 * {@link SupportedVersion#V5_5_1}, or the 5.5.1-specific data is cleared from the data.
 * </p>
 * <p>
 * The parser takes a "forgiving" approach where it tries to load as much data as possible, including 5.5.1 data in a
 * file that says it's in 5.5 format, and vice-versa. However, when it finds inconsistencies, it will add messages to
 * the warnings and errors collections. Most of these messages indicate that the data was loaded, even though it was
 * incorrect, and the data will need to be corrected before it can be written.
 * </p>
 * 
 * <p>
 * The parser makes the assumption that if the version of GEDCOM used is explicitly specified in the file header, that
 * the rest of the data in the file should conform to that spec. For example, if the file header says the file is in 5.5
 * format (i.e., has a VERS 5.5 tag), then it will generate warnings if the new 5.5.1 tags (e.g., EMAIL) are encountered
 * elsewhere, but will load the data anyway. If no version is specified, the 5.5.1 format is assumed as a default.
 * </p>
 * 
 * <p>
 * This approach was selected based on the presumption that most of the uses of GEDCOM4J will be to read GEDCOM files
 * rather than to write them, so this provides that use case with the lowest friction.
 * </p>
 * 
 * @author frizbog1
 * 
 */
public class GedcomParser {

    /**
     * The content of the gedcom file
     */
    public final Gedcom gedcom = new Gedcom();

    /**
     * The things that went wrong while parsing the gedcom file
     */
    public final List<String> errors = new ArrayList<>();

    /**
     * The warnings issued during the parsing of the gedcom file
     */
    public final List<String> warnings = new ArrayList<>();

    /**
     * A flag that indicates whether feedback should be sent to System.out as parsing occurs
     */
    public boolean verbose = false;

    /**
     * Returns true if and only if the Gedcom data says it is for the 5.5 standard.
     * 
     * @return true if and only if the Gedcom data says it is for the 5.5 standard.
     */
    private boolean g55() {
        return null != gedcom.header && null != gedcom.header.gedcomVersion && SupportedVersion.V5_5.equals(gedcom.header.gedcomVersion.versionNumber);
    }

    /**
     * Get a family by their xref, adding them to the gedcom collection of families if needed.
     * 
     * @param xref
     *            the xref of the family
     * @return the family with the specified xref
     */
    private Family getFamily(String xref) {
        Family f = gedcom.families.get(xref);
        if (null == f) {
            f = new Family();
            gedcom.families.put(f.xref = xref, f);
        }
        return f;
    }

    /**
     * Get an individual by their xref, adding them to the gedcom collection of individuals if needed.
     * 
     * @param xref
     *            the xref of the individual
     * @return the individual with the specified xref
     */
    private Individual getIndividual(String xref) {
        Individual i;
        i = gedcom.individuals.get(xref);
        if (null == i) {
            i = new Individual();
            i.xref = xref;
            gedcom.individuals.put(xref, i);
        }
        return i;
    }

    /**
     * Get a multimedia item by its xref, adding it to the gedcom collection of multimedia items if needed.
     * 
     * @param xref
     *            the xref of the multimedia item
     * @return the multimedia item with the specified xref
     */
    private Multimedia getMultimedia(String xref) {
        Multimedia m;
        m = gedcom.multimedia.get(xref);
        if (null == m) {
            m = new Multimedia();
            m.xref = xref;
            gedcom.multimedia.put(xref, m);
        }
        return m;
    }

    /**
     * Get a note by its xref, adding it to the gedcom collection of notes if needed.
     * 
     * @param xref
     *            the xref of the note
     * @return the note with the specified xref
     */
    private Note getNote(String xref) {
        Note note;
        note = gedcom.notes.get(xref);
        if (null == note) {
            note = new Note();
            note.xref = xref;
            gedcom.notes.put(xref, note);
        }
        return note;
    }

    /**
     * Get a repository by its xref, adding it to the gedcom collection of repositories if needed.
     * 
     * @param xref
     *            the xref of the repository
     * @return the repository with the specified xref
     */
    private Repository getRepository(String xref) {
        Repository r = gedcom.repositories.get(xref);
        if (null == r) {
            r = new Repository();
            r.xref = xref;
            gedcom.repositories.put(xref, r);
        }
        return r;

    }

    /**
     * Get a source by its xref, adding it to the gedcom collection of sources if needed.
     * 
     * @param xref
     *            the xref of the source
     * @return the source with the specified xref
     */
    private Source getSource(String xref) {
        Source src = gedcom.sources.get(xref);
        if (null == src) {
            src = new Source(xref);
            gedcom.sources.put(src.xref, src);
        }
        return src;
    }

    /**
     * Get a submitter by their xref, adding them to the gedcom collection of submitters if needed.
     * 
     * @param xref
     *            the xref of the submitter
     * @return the submitter with the specified xref
     */
    private Submitter getSubmitter(String xref) {
        Submitter i;
        i = gedcom.submitters.get(xref);
        if (null == i) {
            i = new Submitter();
            i.xref = xref;
            gedcom.submitters.put(xref, i);
        }
        return i;
    }

    /**
     * Load an address node into an address structure
     * 
     * @param st
     *            the string tree node
     * @param address
     *            the address to load into
     */
    private void loadAddress(StringTree st, Address address) {
        if (null != st.value) {
            address.lines.add(st.value);
        }
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "ADR1":
                    address.addr1 = new StringWithCustomTags(ch);
                    break;
                case "ADR2":
                    address.addr2 = new StringWithCustomTags(ch);
                    break;
                case "CITY":
                    address.city = new StringWithCustomTags(ch);
                    break;
                case "STAE":
                    address.stateProvince = new StringWithCustomTags(ch);
                    break;
                case "POST":
                    address.postalCode = new StringWithCustomTags(ch);
                    break;
                case "CTRY":
                    address.country = new StringWithCustomTags(ch);
                    break;
                case "CONC":
                    if (0 == address.lines.size()) {
                        address.lines.add(ch.value);
                    } else {
                        address.lines.set(address.lines.size() - 1, address.lines.get(address.lines.size() - 1) + ch.value);
                    }
                    break;
                case "CONT":
                    address.lines.add(null == ch.value ? "" : ch.value);
                    break;
                default:
                    unknownTag(ch, address);
                    break;
            }
        }
    }

    /**
     * Load an association between two individuals from a string tree node
     * 
     * @param st
     *            the node
     * @param associations
     *            the list of associations to load into
     */
    private void loadAssociation(StringTree st, List<Association> associations) {
        Association a = new Association();
        associations.add(a);
        a.associatedEntityXref = st.value;
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "RELA":
                    a.relationship = new StringWithCustomTags(ch);
                    break;
                case "NOTE":
                    loadNote(ch, a.notes);
                    break;
                case "SOUR":
                    loadCitation(ch, a.citations);
                    break;
                case "TYPE":
                    a.associatedEntityType = new StringWithCustomTags(ch);
                    break;
                default:
                    unknownTag(ch, a);
                    break;
            }
        }

    }

    /**
     * Load a change date structure from a string tree node
     * 
     * @param st
     *            the node
     * @param changeDate
     *            the change date to load into
     */
    private void loadChangeDate(StringTree st, ChangeDate changeDate) {
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "DATE":
                    changeDate.date = new StringWithCustomTags(ch.value);
                    if (!ch.children.isEmpty()) {
                        changeDate.time = new StringWithCustomTags(ch.children.get(0));
                    }
                    break;
                case "NOTE":
                    loadNote(ch, changeDate.notes);
                    break;
                default:
                    unknownTag(ch, changeDate);
                    break;
            }
        }

    }

    /**
     * Load a source citation from a string tree node into a list of citations
     * 
     * @param st
     *            the string tree node
     * @param list
     *            the list of citations to load into
     */
    private void loadCitation(StringTree st, List<AbstractCitation> list) {
        AbstractCitation citation;
        boolean b = GedcomParserHelper.referencesAnotherNode(st);
        citation = b?new CitationWithSource():new CitationWithoutSource();
        GedcomParser x = b ? loadCitationWithSource(st, citation) : loadCitationWithoutSource(st, citation);
        list.add(citation);
    }

    /**
     * Load a DATA structure in a source citation from a string tree node
     * 
     * @param st
     *            the node
     * @param d
     *            the CitationData structure
     */
    private void loadCitationData(StringTree st, CitationData d) {
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "DATE":
                    d.entryDate = new StringWithCustomTags(ch);
                    break;
                case "TEXT":
                    List<String> ls = new ArrayList<>();
                    d.sourceText.add(ls);
                    loadMultiLinesOfText(ch, ls, d);
                    break;
                default:
                    unknownTag(ch, d);
                    break;
            }
        }

    }

    /**
     * Load the non-cross-referenced source citation from a string tree node
     *
     * @param st
     *            the node
     * @param citation
     */
    private GedcomParser loadCitationWithoutSource(StringTree st, AbstractCitation citation) {
        CitationWithoutSource cws = (CitationWithoutSource) citation;
        cws.description.add(st.value);
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "CONT":
                    cws.description.add(null == ch.value ? "" : ch.value);
                    break;
                case "CONC":
                    if (0 == cws.description.size()) {
                        cws.description.add(ch.value);
                    } else {
                        // Append to last value in string list
                        cws.description.set(cws.description.size() - 1, cws.description.get(cws.description.size() - 1)
                                + ch.value);
                    }
                    break;
                case "TEXT":
                    List<String> ls = new ArrayList<>();
                    cws.textFromSource.add(ls);
                    loadMultiLinesOfText(ch, ls, cws);
                    break;
                case "NOTE":
                    loadNote(ch, cws.notes);
                    break;
                default:
                    unknownTag(ch, citation);
                    break;
            }
        } return this;
    }

    /**
     * Load a cross-referenced source citation from a string tree node
     *
     * @param st
     *            the node
     * @param citation
     */
    private GedcomParser loadCitationWithSource(StringTree st, AbstractCitation citation) {
        CitationWithSource cws = (CitationWithSource) citation;
        Source src = null;
        if (GedcomParserHelper.referencesAnotherNode(st)) {
            src = getSource(st.value);
        }
        cws.source = src;
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "PAGE":
                    cws.whereInSource = new StringWithCustomTags(ch);
                    break;
                case "EVEN":
                    cws.eventCited = new StringWithCustomTags(ch.value);
                    if (null != ch.children) {
                        for (StringTree gc : ch.children) {
                            if ("ROLE".equals(gc.tag)) {
                                cws.roleInEvent = new StringWithCustomTags(gc);
                            } else {
                                unknownTag(gc, cws.eventCited);
                            }
                        }
                    }
                    break;
                case "DATA":
                    CitationData d = new CitationData();
                    cws.data.add(d);
                    loadCitationData(ch, d);
                    break;
                case "QUAY":
                    cws.certainty = new StringWithCustomTags(ch);
                    break;
                case "NOTE":
                    loadNote(ch, cws.notes);
                    break;
                default:
                    unknownTag(ch, citation);
                    break;
            }
        }  return this;
    }

    /**
     * Load a corporation structure from a string tree node
     * 
     * @param st
     *            the node
     * @param corporation
     *            the corporation structure to fill
     */
    private void loadCorporation(StringTree st, Corporation corporation) {
        corporation.businessName = st.value;
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "ADDR":
                    corporation.address = new Address();
                    loadAddress(ch, corporation.address);
                    break;
                case "PHON":
                    corporation.phoneNumbers.add(new StringWithCustomTags(ch));
                    break;
                case "WWW":
                    corporation.wwwUrls.add(new StringWithCustomTags(ch));
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but WWW URL was specified for the corporation in the source system on line "
                                + ch.lineNum
                                + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "FAX":
                    corporation.faxNumbers.add(new StringWithCustomTags(ch));
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but fax number was specified for the corporation in the source system on line "
                                + ch.lineNum
                                + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "EMAIL":
                    corporation.emails.add(new StringWithCustomTags(ch));
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but emails was specified for the corporation in the source system on line "
                                + ch.lineNum
                                + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                default:
                    unknownTag(ch, corporation);
                    break;
            }
        }
    }

    /**
     * Load a family structure from a stringtree node, and load it into the gedcom family collection
     * 
     * @param st
     *            the node
     */
    private void loadFamily(StringTree st) {
        Family f = getFamily(st.id);
        for (StringTree ch : st.children) {
            String tag = ch.tag;
            switch (tag) {
                case "HUSB":
                    f.husband = getIndividual(ch.value);
                    break;
                case "WIFE":
                    f.wife = getIndividual(ch.value);
                    break;
                case "CHIL":
                    f.children.add(getIndividual(ch.value));
                    break;
                case "NCHI":
                    f.numChildren = new StringWithCustomTags(ch);
                    break;
                case "SOUR":
                    loadCitation(ch, f.citations);
                    break;
                case "OBJE":
                    loadMultimediaLink(ch, f.multimedia);
                    break;
                case "RIN":
                    f.automatedRecordId = new StringWithCustomTags(ch);
                    break;
                case "CHAN":
                    f.changeDate = new ChangeDate();
                    loadChangeDate(ch, f.changeDate);
                    break;
                case "NOTE":
                    loadNote(ch, f.notes);
                    break;
                case "RESN":
                    f.restrictionNotice = new StringWithCustomTags(ch);
                    if (g55())
                        warnings.add("GEDCOM version is 5.5 but restriction notice was specified for family on line " + ch.lineNum + " , which is a GEDCOM 5.5.1 feature.  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    break;
                case "RFN":
                    f.recFileNumber = new StringWithCustomTags(ch);
                    break;
                case "SLGS":
                    loadLdsSpouseSealing(ch, f.ldsSpouseSealings);
                    break;
                case "SUBM":
                    f.submitters.add(getSubmitter(ch.value));
                    break;
                case "REFN":
                    UserReference u = new UserReference();
                    f.userReferences.add(u);
                    loadUserReference(ch, u);
                    break;
                default:
                    if (FamilyEventType.isValidTag(tag)) loadFamilyEvent(ch, f.events);
                    else unknownTag(ch, f);
                    break;
            }
        }

    }

    /**
     * Load a family event from a string tree node into a list of family events
     * 
     * @param st
     *            the node
     * @param events
     *            the list of family events
     */
    private void loadFamilyEvent(StringTree st, List<FamilyEvent> events) {
        FamilyEvent e = new FamilyEvent();
        events.add(e);
        e.type = FamilyEventType.getFromTag(st.tag);
        e.yNull = st.value;
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "TYPE":
                    e.subType = new StringWithCustomTags(ch);
                    break;
                case "DATE":
                    e.date = new StringWithCustomTags(ch);
                    break;
                case "PLAC":
                    e.place = new Place();
                    loadPlace(ch, e.place);
                    break;
                case "OBJE":
                    loadMultimediaLink(ch, e.multimedia);
                    break;
                case "NOTE":
                    loadNote(ch, e.notes);
                    break;
                case "SOUR":
                    loadCitation(ch, e.citations);
                    break;
                case "RESN":
                    e.restrictionNotice = new StringWithCustomTags(ch);
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but restriction notice was specified for family event on line "
                                + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "RELI":
                    e.religiousAffiliation = new StringWithCustomTags(ch);
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but religious affiliation was specified for family event on line "
                                + ch.lineNum
                                + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "AGE":
                    e.age = new StringWithCustomTags(ch);
                    break;
                case "CAUS":
                    e.cause = new StringWithCustomTags(ch);
                    break;
                case "ADDR":
                    e.address = new Address();
                    loadAddress(ch, e.address);
                    break;
                case "AGNC":
                    e.respAgency = new StringWithCustomTags(ch);
                    break;
                case "PHON":
                    e.phoneNumbers.add(new StringWithCustomTags(ch));
                    break;
                case "WWW":
                    e.wwwUrls.add(new StringWithCustomTags(ch));
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but WWW URL was specified for " + e.type
                                + " family event on line " + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "FAX":
                    e.faxNumbers.add(new StringWithCustomTags(ch));
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but fax number was specified for " + e.type
                                + " family event on line " + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "EMAIL":
                    e.emails.add(new StringWithCustomTags(ch));
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but email was specified for " + e.type
                                + " family event on line " + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "HUSB":
                    e.husbandAge = new StringWithCustomTags(ch.children.get(0));
                    break;
                case "WIFE":
                    e.wifeAge = new StringWithCustomTags(ch.children.get(0));
                    break;
                default:
                    unknownTag(ch, e);
                    break;
            }
        }

    }

    /**
     * Load a reference to a family where this individual was a child, from a string tree node
     * 
     * @param st
     *            the string tree node
     * @param familiesWhereChild
     *            the list of families where the individual was a child
     */
    private void loadFamilyWhereChild(StringTree st, List<FamilyChild> familiesWhereChild) {
        Family f = getFamily(st.value);
        FamilyChild fc = new FamilyChild();
        familiesWhereChild.add(fc);
        fc.family = f;
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "NOTE":
                    loadNote(ch, fc.notes);
                    break;
                case "PEDI":
                    fc.pedigree = new StringWithCustomTags(ch);
                    break;
                case "ADOP":
                    fc.adoptedBy = AdoptedByWhichParent.valueOf(ch.value);
                    break;
                case "STAT":
                    fc.status = new StringWithCustomTags(ch);
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but status was specified for child-to-family link on line "
                                + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                default:
                    unknownTag(ch, fc);
                    break;
            }
        }

    }

    /**
     * Load a reference to a family where this individual was a spouse, from a string tree node
     * 
     * @param st
     *            the string tree node
     * @param familiesWhereSpouse
     *            the list of families where the individual was a child
     */
    private void loadFamilyWhereSpouse(StringTree st, List<FamilySpouse> familiesWhereSpouse) {
        Family f = getFamily(st.value);
        FamilySpouse fs = new FamilySpouse();
        fs.family = f;
        familiesWhereSpouse.add(fs);
        for (StringTree ch : st.children) {
            if ("NOTE".equals(ch.tag)) {
                loadNote(ch, fs.notes);
            } else {
                unknownTag(ch, fs);
            }
        }
    }

    /**
     * Load a single 5.5-style file reference
     * 
     * @param m
     *            The multimedia object to contain the new file reference
     * @param children
     *            the sub-tags of the OBJE tag
     */
    private void loadFileReference55(Multimedia m, List<StringTree> children) {
        FileReference currentFileRef = new FileReference();
        m.fileReferences.add(currentFileRef);
        for (StringTree ch : children) {
            switch (ch.tag) {
                case "FORM":
                    currentFileRef.format = new StringWithCustomTags(ch);
                    break;
                case "TITL":
                    m.embeddedTitle = new StringWithCustomTags(ch);
                    break;
                case "FILE":
                    currentFileRef.referenceToFile = new StringWithCustomTags(ch);
                    break;
                case "NOTE":
                    loadNote(ch, m.notes);
                    break;
                default:
                    unknownTag(ch, m);
                    break;
            }
        }

    }

    /**
     * Load all the file references in the current OBJE tag
     * 
     * @param m
     *            the multimedia object being with the reference
     * @param st
     *            the OBJE node being parsed
     */
    private void loadFileReferences(Multimedia m, StringTree st) {
        int fileTagCount = 0;
        int formTagCount = 0;

        for (StringTree ch : st.children) {
            /*
             * Count up the number of files referenced for this object - GEDCOM 5.5.1 allows multiple, 5.5 only allows 1
             */
            if ("FILE".equals(ch.tag)) {
                fileTagCount++;
            }
            /*
             * Count the number of formats referenced per file - GEDCOM 5.5.1 has them as children of FILEs (so should
             * be zero), 5.5 pairs them with the single FILE tag (so should be one)
             */
            if ("FORM".equals(ch.tag)) {
                formTagCount++;
            }
        }
        if (1 < fileTagCount) {
            if (g55()) {
                warnings.add("GEDCOM version is 5.5, but multiple files referenced in multimedia reference on line "
                        + st.lineNum
                        + ", which is only allowed in 5.5.1. "
                        + "Data will be loaded, but cannot be written back out unless the GEDCOM version is changed to 5.5.1");
            }
        }
        if (0 == formTagCount) {
            if (g55()) {
                warnings.add("GEDCOM version is 5.5, but there is not a FORM tag in the multimedia link on line "
                        + st.lineNum
                        + ", a scenario which is only allowed in 5.5.1. "
                        + "Data will be loaded, but cannot be written back out unless the GEDCOM version is changed to 5.5.1");
            }
        }
        if (1 < formTagCount) {
            errors.add("Multiple FORM tags were found for a multimedia file reference at line " + st.lineNum
                    + " - this is not compliant with any GEDCOM standard - data not loaded");
            return;
        }

        if (1 < fileTagCount || formTagCount < fileTagCount) {
            loadFileReferences551(m, st.children);
        } else {
            loadFileReference55(m, st.children);
        }
    }

    /**
     * Load one or more 5.5.1-style references
     * 
     * @param m
     *            the multimedia object to which we are adding the file references
     * 
     * @param children
     *            the sub-tags of the OBJE tag
     */
    private void loadFileReferences551(Multimedia m, List<StringTree> children) {
        for (StringTree ch : children) {
            switch (ch.tag) {
                case "FILE":
                    FileReference fr = new FileReference();
                    m.fileReferences.add(fr);
                    fr.referenceToFile = new StringWithCustomTags(ch);
                    if (1 != ch.children.size()) {
                        errors.add("Missing or multiple children nodes found under FILE node - GEDCOM 5.5.1 standard requires exactly 1 FORM node");
                    }
                    for (StringTree gch : ch.children) {
                        if ("FORM".equals(gch.tag)) {
                            fr.format = new StringWithCustomTags(gch.value);
                            for (StringTree ggch : ch.children) {
                                if ("MEDI".equals(ggch.tag)) {
                                    fr.mediaType = new StringWithCustomTags(ggch);
                                } else {
                                    unknownTag(ggch, fr);
                                }
                            }
                        } else {
                            unknownTag(gch, fr);
                        }
                    }
                    break;
                case "TITL":
                    for (FileReference fr1 : m.fileReferences) {
                        fr1.title = new StringWithCustomTags(ch.tag);
                    }
                    break;
                case "NOTE":
                    loadNote(ch, m.notes);
                    if (!g55()) {
                        warnings.add("Gedcom version was 5.5.1, but a NOTE was found on a multimedia link on line "
                                + ch.lineNum
                                + ", which is no longer supported. "
                                + "Data will be loaded, but cannot be written back out unless the GEDCOM version is changed to 5.5");
                    }
                    break;
                default:
                    unknownTag(ch, m);
                    break;
            }
        }
    }

    /**
     * Load a gedcom version from the string tree node
     * 
     * @param st
     *            the node
     * @param gedcomVersion
     *            the gedcom version structure to load
     */
    private void loadGedcomVersion(StringTree st, GedcomVersion gedcomVersion) {
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "VERS":
                    SupportedVersion vn = null;
                    try {
                        vn = SupportedVersion.forString(ch.value);
                    } catch (UnsupportedVersionException e) {
                        errors.add(e.getMessage());
                    }
                    gedcomVersion.versionNumber = vn;
                    break;
                case "FORM":
                    gedcomVersion.gedcomForm = new StringWithCustomTags(ch);
                    break;
                default:
                    unknownTag(ch, gedcomVersion);
                    break;
            }
        }
    }

    /**
     * Load a gedcom header from a string tree node
     * 
     * @param st
     *            the node
     */
    private void loadHeader(StringTree st) {
        Header header = new Header();
        gedcom.header = header;
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "SOUR":
                    header.sourceSystem = new SourceSystem();
                    loadSourceSystem(ch, header.sourceSystem);
                    break;
                case "DEST":
                    header.destinationSystem = new StringWithCustomTags(ch);
                    break;
                case "DATE":
                    header.date = new StringWithCustomTags(ch);
                    // one optional time subitem is the only possibility here
                    if (!ch.children.isEmpty()) {
                        header.time = new StringWithCustomTags(ch.children.get(0));
                    }
                    break;
                case "CHAR":
                    header.characterSet = new CharacterSet();
                    header.characterSet.characterSetName = new StringWithCustomTags(ch);
                    // one optional version subitem is the only possibility here
                    if (!ch.children.isEmpty()) {
                        header.characterSet.versionNum = new StringWithCustomTags(ch.children.get(0));
                    }
                    break;
                case "SUBM":
                    header.submitter = getSubmitter(ch.value);
                    break;
                case "FILE":
                    header.fileName = new StringWithCustomTags(ch);
                    break;
                case "GEDC":
                    header.gedcomVersion = new GedcomVersion();
                    loadGedcomVersion(ch, header.gedcomVersion);
                    break;
                case "COPR":
                    loadMultiLinesOfText(ch, header.copyrightData, header);
                    if (g55() && 1 < header.copyrightData.size()) {
                        warnings.add("GEDCOM version is 5.5, but multiple lines of copyright data were specified, which is only allowed in GEDCOM 5.5.1. "
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "SUBN":
                    if (null == header.submission) {
                    /*
                     * There can only be one SUBMISSION record per GEDCOM, and it's found at the root level, but the
                     * HEAD structure has a cross-reference to that root-level structure, so we're setting it here (if
                     * it hasn't already been loaded, which it probably isn't yet)
                     */
                        header.submission = gedcom.submission;
                    }
                    break;
                case "LANG":
                    header.language = new StringWithCustomTags(ch);
                    break;
                case "PLAC":
                    header.placeHierarchy = new StringWithCustomTags(ch.children.get(0));
                    break;
                case "NOTE":
                    loadMultiLinesOfText(ch, header.notes, header);
                    break;
                default:
                    unknownTag(ch, header);
                    break;
            }
        }
    }

    /**
     * Load the header source data structure from a string tree node
     * 
     * @param st
     *            the node
     * @param sourceData
     *            the header source data
     */
    private void loadHeaderSourceData(StringTree st, HeaderSourceData sourceData) {
        sourceData.name = st.value;
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "DATE":
                    sourceData.publishDate = new StringWithCustomTags(ch);
                    break;
                case "COPR":
                    sourceData.copyright = new StringWithCustomTags(ch);
                    break;
                default:
                    unknownTag(ch, sourceData);
                    break;
            }
        }

    }

    /**
     * Load an individual from a string tree node
     * 
     * @param st
     *            the node
     */
    private void loadIndividual(StringTree st) {
        Individual i = new Individual();
        gedcom.individuals.put(st.id, i);
        i.xref = st.id;
        for (StringTree ch : st.children) {
            if ("NAME".equals(ch.tag)) {
                PersonalName pn = new PersonalName();
                i.names.add(pn);
                loadPersonalName(ch, pn);
            } else if ("SEX".equals(ch.tag)) {
                i.sex = new StringWithCustomTags(ch);
            } else if ("ADDR".equals(ch.tag)) {
                i.address = new Address();
                loadAddress(ch, i.address);
            } else if ("PHON".equals(ch.tag)) {
                i.phoneNumbers.add(new StringWithCustomTags(ch));
            } else if ("WWW".equals(ch.tag)) {
                i.wwwUrls.add(new StringWithCustomTags(ch));
                if (g55()) {
                    warnings.add("GEDCOM version is 5.5 but WWW URL was specified for individual " + i.xref
                            + " on line " + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                            + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                }
            } else if ("FAX".equals(ch.tag)) {
                i.faxNumbers.add(new StringWithCustomTags(ch));
                if (g55()) {
                    warnings.add("GEDCOM version is 5.5 but fax was specified for individual " + i.xref + "on line "
                            + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                            + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                }
            } else if ("EMAIL".equals(ch.tag)) {
                i.emails.add(new StringWithCustomTags(ch));
                if (g55()) {
                    warnings.add("GEDCOM version is 5.5 but email was specified for individual " + i.xref + " on line "
                            + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                            + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                }
            } else if (IndividualEventType.isValidTag(ch.tag)) {
                loadIndividualEvent(ch, i.events);
            } else if (IndividualAttributeType.isValidTag(ch.tag)) {
                loadIndividualAttribute(ch, i.attributes);
            } else if (LdsIndividualOrdinanceType.isValidTag(ch.tag)) {
                loadLdsIndividualOrdinance(ch, i.ldsIndividualOrdinances);
            } else if ("NOTE".equals(ch.tag)) {
                loadNote(ch, i.notes);
            } else if ("CHAN".equals(ch.tag)) {
                i.changeDate = new ChangeDate();
                loadChangeDate(ch, i.changeDate);
            } else if ("RIN".equals(ch.tag)) {
                i.recIdNumber = new StringWithCustomTags(ch);
            } else if ("RFN".equals(ch.tag)) {
                i.permanentRecFileNumber = new StringWithCustomTags(ch);
            } else if ("OBJE".equals(ch.tag)) {
                loadMultimediaLink(ch, i.multimedia);
            } else if ("RESN".equals(ch.tag)) {
                i.restrictionNotice = new StringWithCustomTags(ch);
            } else if ("SOUR".equals(ch.tag)) {
                loadCitation(ch, i.citations);
            } else if ("ALIA".equals(ch.tag)) {
                i.aliases.add(new StringWithCustomTags(ch));
            } else if ("FAMS".equals(ch.tag)) {
                loadFamilyWhereSpouse(ch, i.familiesWhereSpouse);
            } else if ("FAMC".equals(ch.tag)) {
                loadFamilyWhereChild(ch, i.familiesWhereChild);
            } else if ("ASSO".equals(ch.tag)) {
                loadAssociation(ch, i.associations);
            } else if ("ANCI".equals(ch.tag)) {
                i.ancestorInterest.add(getSubmitter(ch.value));
            } else if ("DESI".equals(ch.tag)) {
                i.descendantInterest.add(getSubmitter(ch.value));
            } else if ("AFN".equals(ch.tag)) {
                i.ancestralFileNumber = new StringWithCustomTags(ch);
            } else if ("REFN".equals(ch.tag)) {
                UserReference u = new UserReference();
                i.userReferences.add(u);
                loadUserReference(ch, u);
            } else if ("SUBM".equals(ch.tag)) {
                i.submitters.add(getSubmitter(ch.value));
            } else {
                unknownTag(ch, i);
            }
        }

    }

    /**
     * Load an attribute about an individual from a string tree node
     * 
     * @param st
     *            the node
     * @param attributes
     *            the list of individual attributes
     */
    private void loadIndividualAttribute(StringTree st, List<IndividualAttribute> attributes) {
        IndividualAttribute a = new IndividualAttribute();
        attributes.add(a);
        a.type = IndividualAttributeType.getFromTag(st.tag);
        if (IndividualAttributeType.FACT.equals(a.type) && g55()) {
            warnings.add("FACT tag specified on a GEDCOM 5.5 file at line " + st.lineNum
                    + ", but FACT was not added until 5.5.1."
                    + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
        }
        a.description = new StringWithCustomTags(st.value);
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "TYPE":
                    a.subType = new StringWithCustomTags(ch);
                    break;
                case "DATE":
                    a.date = new StringWithCustomTags(ch);
                    break;
                case "PLAC":
                    a.place = new Place();
                    loadPlace(ch, a.place);
                    break;
                case "AGE":
                    a.age = new StringWithCustomTags(ch);
                    break;
                case "CAUS":
                    a.cause = new StringWithCustomTags(ch);
                    break;
                case "SOUR":
                    loadCitation(ch, a.citations);
                    break;
                case "AGNC":
                    a.respAgency = new StringWithCustomTags(ch);
                    break;
                case "PHON":
                    a.phoneNumbers.add(new StringWithCustomTags(ch));
                    break;
                case "WWW":
                    a.wwwUrls.add(new StringWithCustomTags(ch));
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but WWW URL was specified for " + a.type
                                + " attribute on line " + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "FAX":
                    a.faxNumbers.add(new StringWithCustomTags(ch));
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but fax was specified for " + a.type + " attribute on line "
                                + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "EMAIL":
                    a.emails.add(new StringWithCustomTags(ch));
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but email was specified for " + a.type + " attribute on line "
                                + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "ADDR":
                    a.address = new Address();
                    loadAddress(ch, a.address);
                    break;
                case "OBJE":
                    loadMultimediaLink(ch, a.multimedia);
                    break;
                case "NOTE":
                    loadNote(ch, a.notes);
                    break;
                /*case "SOUR":
                    loadCitation(ch, a.citations);
                    break;*/
                case "CONC":
                    if (null == a.description) {
                        a.description = new StringWithCustomTags(ch);
                    } else {
                        a.description.value += ch.value;
                    }
                    break;
                default:
                    unknownTag(ch, a);
                    break;
            }
        }
    }

    /**
     * Load an event about an individual from a string tree node
     * 
     * @param st
     *            the node
     * @param events
     *            the list of events about an individual
     */
    private void loadIndividualEvent(StringTree st, List<IndividualEvent> events) {
        IndividualEvent e = new IndividualEvent();
        events.add(e);
        e.type = IndividualEventType.getFromTag(st.tag);
        e.yNull = st.value;
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "TYPE":
                    e.subType = new StringWithCustomTags(ch);
                    break;
                case "DATE":
                    e.date = new StringWithCustomTags(ch);
                    break;
                case "PLAC":
                    e.place = new Place();
                    loadPlace(ch, e.place);
                    break;
                case "OBJE":
                    loadMultimediaLink(ch, e.multimedia);
                    break;
                case "NOTE":
                    loadNote(ch, e.notes);
                    break;
                case "SOUR":
                    loadCitation(ch, e.citations);
                    break;
                case "AGE":
                    e.age = new StringWithCustomTags(ch);
                    break;
                case "CAUS":
                    e.cause = new StringWithCustomTags(ch);
                    break;
                case "ADDR":
                    e.address = new Address();
                    loadAddress(ch, e.address);
                    break;
                case "AGNC":
                    e.respAgency = new StringWithCustomTags(ch);
                    break;
                case "RESN":
                    e.restrictionNotice = new StringWithCustomTags(ch);
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but restriction notice was specified for individual event on line "
                                + ch.lineNum
                                + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "RELI":
                    e.religiousAffiliation = new StringWithCustomTags(ch);
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but religious affiliation was specified for individual event on line "
                                + ch.lineNum
                                + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "PHON":
                    e.phoneNumbers.add(new StringWithCustomTags(ch));
                    break;
                case "WWW":
                    e.wwwUrls.add(new StringWithCustomTags(ch));
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but WWW URL was specified on " + e.type + " event on line "
                                + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "FAX":
                    e.faxNumbers.add(new StringWithCustomTags(ch));
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but fax was specified on " + e.type + " event on line "
                                + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "EMAIL":
                    e.emails.add(new StringWithCustomTags(ch));
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but email was specified on " + e.type + " event on line "
                                + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "CONC":
                    if (null == e.description) {
                        e.description = new StringWithCustomTags(ch);
                    } else {
                        e.description.value += ch.value;
                    }
                    break;
                case "CONT":
                    if (null == e.description) {
                        e.description = new StringWithCustomTags(null == ch.value ? "" : ch.value);
                    } else {
                        e.description.value += '\n' + ch.value;
                    }
                    break;
                case "FAMC":
                    List<FamilyChild> families = new ArrayList<>();
                    loadFamilyWhereChild(ch, families);
                    if (!families.isEmpty()) {
                        e.family = families.get(0);
                    }
                    break;
                default:
                    unknownTag(ch, e);
                    break;
            }
        }

    }

    /**
     * Load an LDS individual ordinance from a string tree node
     * 
     * @param st
     *            the node
     * @param ldsIndividualOrdinances
     *            the list of LDS ordinances
     */
    private void loadLdsIndividualOrdinance(StringTree st, List<LdsIndividualOrdinance> ldsIndividualOrdinances) {
        LdsIndividualOrdinance o = new LdsIndividualOrdinance();
        ldsIndividualOrdinances.add(o);
        o.type = LdsIndividualOrdinanceType.getFromTag(st.tag);
        o.yNull = st.value;
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "DATE":
                    o.date = new StringWithCustomTags(ch);
                    break;
                case "PLAC":
                    o.place = new StringWithCustomTags(ch);
                    break;
                case "STAT":
                    o.status = new StringWithCustomTags(ch);
                    break;
                case "TEMP":
                    o.temple = new StringWithCustomTags(ch);
                    break;
                case "SOUR":
                    loadCitation(ch, o.citations);
                    break;
                case "NOTE":
                    loadNote(ch, o.notes);
                    break;
                case "FAMC":
                    List<FamilyChild> families = new ArrayList<>();
                    loadFamilyWhereChild(ch, families);
                    if (!families.isEmpty()) {
                        o.familyWhereChild = families.get(0);
                    }
                    break;
                default:
                    unknownTag(ch, o);
                    break;
            }
        }
    }

    /**
     * Load an LDS Spouse Sealing from a string tree node
     * 
     * @param st
     *            the string tree node
     * @param ldsSpouseSealings
     *            the list of LDS spouse sealings on the family
     */
    private void loadLdsSpouseSealing(StringTree st, List<LdsSpouseSealing> ldsSpouseSealings) {
        LdsSpouseSealing o = new LdsSpouseSealing();
        ldsSpouseSealings.add(o);
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "DATE":
                    o.date = new StringWithCustomTags(ch);
                    break;
                case "PLAC":
                    o.place = new StringWithCustomTags(ch);
                    break;
                case "STAT":
                    o.status = new StringWithCustomTags(ch);
                    break;
                case "TEMP":
                    o.temple = new StringWithCustomTags(ch);
                    break;
                case "SOUR":
                    loadCitation(ch, o.citations);
                    break;
                case "NOTE":
                    loadNote(ch, o.notes);
                    break;
                default:
                    unknownTag(ch, o);
                    break;
            }

        }

    }

    /**
     * Load multiple (continued) lines of text from a string tree node
     * 
     * @param st
     *            the node
     * @param listOfString
     *            the list of string to load into
     * @param element
     *            the parent element to which the <code>listOfString</code> belongs
     */
    private void loadMultiLinesOfText(StringTree st, List<String> listOfString, AbstractElement element) {
        if (null != st.value) {
            listOfString.add(st.value);
        }
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "CONT":
                    if (null == ch.value) {
                        listOfString.add("");
                    } else {
                        listOfString.add(ch.value);
                    }
                    break;
                case "CONC":
                    // If there's no value to concatenate, ignore it
                    if (null != ch.value) {
                        if (0 == listOfString.size()) {
                            listOfString.add(ch.value);
                        } else {
                            listOfString.set(listOfString.size() - 1, listOfString.get(listOfString.size() - 1) + ch.value);
                        }
                    }
                    break;
                default:
                    unknownTag(ch, element);
                    break;
            }
        }
    }

    /**
     * Load a multimedia reference from a string tree node. This corresponds to the MULTIMEDIA_LINK structure in the
     * GEDCOM specs.
     * 
     * @param st
     *            the string tree node
     * @param multimedia
     *            the list of multimedia on the current object
     */
    private void loadMultimediaLink(StringTree st, List<Multimedia> multimedia) {
        Multimedia m;
        if (GedcomParserHelper.referencesAnotherNode(st)) {
            m = getMultimedia(st.value);
        } else {
            m = new Multimedia();
            loadFileReferences(m, st);
        }
        multimedia.add(m);
    }

    /**
     * Determine which style is being used here - GEDCOM 5.5 or 5.5.1 - and load appropriately. Warn if the structure is
     * inconsistent with the specified format.
     * 
     * @param st
     *            the OBJE node being loaded
     */
    private void loadMultimediaRecord(StringTree st) {
        int fileTagCount = 0;
        for (StringTree ch : st.children) {
            if ("FILE".equals(ch.tag)) {
                fileTagCount++;
            }
        }
        if (0 < fileTagCount) {
            if (g55()) {
                warnings.add("GEDCOM version was 5.5, but a 5.5.1-style multimedia record was found at line "
                        + st.lineNum
                        + ". "
                        + "Data will be loaded, but might have problems being written until the version is for the data is changed to 5.5.1");
            }
            loadMultimediaRecord551(st);
        } else {
            if (!g55()) {
                warnings.add("GEDCOM version is 5.5.1, but a 5.5-style multimedia record was found at line "
                        + st.lineNum
                        + ". "
                        + "Data will be loaded, but might have problems being written until the version is for the data is changed to 5.5.1");
            }
            loadMultimediaRecord55(st);
        }
    }

    /**
     * Load a GEDCOM 5.5-style multimedia record (that could be referenced from another object) from a string tree node.
     * This corresponds to the MULTIMEDIA_RECORD structure in the GEDCOM 5.5 spec.
     * 
     * @param st
     *            the OBJE node being loaded
     */
    private void loadMultimediaRecord55(StringTree st) {
        Multimedia m = getMultimedia(st.id);
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "FORM":
                    m.embeddedMediaFormat = new StringWithCustomTags(ch);
                    break;
                case "TITL":
                    m.embeddedTitle = new StringWithCustomTags(ch);
                    break;
                case "NOTE":
                    loadNote(ch, m.notes);
                    break;
                case "SOUR":
                    loadCitation(ch, m.citations);
                    break;
                case "BLOB":
                    loadMultiLinesOfText(ch, m.blob, m);
                    if (!g55()) {
                        warnings.add("GEDCOM version is 5.5.1, but a BLOB tag was found at line " + ch.lineNum + ". "
                                + "Data will be loaded but will not be writeable unless GEDCOM version is changed to 5.5.1");
                    }
                    break;
                case "OBJE":
                    List<Multimedia> continuedObjects = new ArrayList<>();
                    loadMultimediaLink(ch, continuedObjects);
                    m.continuedObject = continuedObjects.get(0);
                    if (!g55()) {
                        warnings.add("GEDCOM version is 5.5.1, but a chained OBJE tag was found at line " + ch.lineNum
                                + ". "
                                + "Data will be loaded but will not be writeable unless GEDCOM version is changed to 5.5.1");
                    }
                    break;
                case "REFN":
                    UserReference u = new UserReference();
                    m.userReferences.add(u);
                    loadUserReference(ch, u);
                    break;
                case "RIN":
                    m.recIdNumber = new StringWithCustomTags(ch);
                    break;
                case "CHAN":
                    m.changeDate = new ChangeDate();
                    loadChangeDate(ch, m.changeDate);
                    break;
                default:
                    unknownTag(ch, m);
                    break;
            }
        }

    }

    /**
     * Load a GEDCOM 5.5.1-style multimedia record (that could be referenced from another object) from a string tree
     * node. This corresponds to the MULTIMEDIA_RECORD structure in the GEDCOM 5.5.1 spec.
     * 
     * @param st
     *            the OBJE node being loaded
     */
    private void loadMultimediaRecord551(StringTree st) {
        Multimedia m = getMultimedia(st.id);
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "FILE":
                    FileReference fr = new FileReference();
                    m.fileReferences.add(fr);
                    fr.referenceToFile = new StringWithCustomTags(ch);
                    for (StringTree gch : ch.children) {
                        switch (gch.tag) {
                            case "FORM":
                                fr.format = new StringWithCustomTags(gch.value);
                                if (1 == gch.children.size()) {
                                    StringTree ggch = gch.children.get(0);
                                    if ("TYPE".equals(ggch.tag)) {
                                        fr.mediaType = new StringWithCustomTags(ggch);
                                    } else {
                                        unknownTag(ggch, fr);
                                    }
                                }
                                break;
                            case "TITL":
                                fr.title = new StringWithCustomTags(gch);
                                break;
                            default:
                                unknownTag(gch, fr);
                                break;
                        }
                    }
                    if (null == fr.format) {
                        errors.add("FORM tag not found under FILE reference on line " + st.lineNum);
                    }
                    break;
                case "NOTE":
                    loadNote(ch, m.notes);
                    break;
                case "SOUR":
                    loadCitation(ch, m.citations);
                    break;
                case "REFN":
                    UserReference u = new UserReference();
                    m.userReferences.add(u);
                    loadUserReference(ch, u);
                    break;
                case "RIN":
                    m.recIdNumber = new StringWithCustomTags(ch);
                    break;
                case "CHAN":
                    m.changeDate = new ChangeDate();
                    loadChangeDate(ch, m.changeDate);
                    break;
                default:
                    unknownTag(ch, m);
                    break;
            }

        }

    }

    /**
     * Load a note from a string tree node into a list of notes
     * 
     * @param st
     *            the node
     * @param notes
     *            the list of notes to add the note to as it is parsed.
     */
    private void loadNote(StringTree st, List<Note> notes) {
        Note note;
        if (GedcomParserHelper.referencesAnotherNode(st)) {
            note = getNote(st.value);
            notes.add(note);
            return;
        } else if (null != st.id) {
            note = getNote(st.id);
        } else {
            note = new Note();
            notes.add(note);
        }
        note.lines.add(st.value);
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "CONC":
                    if (0 == note.lines.size()) {
                        note.lines.add(ch.value);
                    } else {
                        String lastNote = note.lines.get(note.lines.size() - 1);
                        if (null == lastNote || 0 == lastNote.length()) {
                            note.lines.set(note.lines.size() - 1, ch.value);
                        } else {
                            note.lines.set(note.lines.size() - 1, lastNote + ch.value);
                        }
                    }
                    break;
                case "CONT":
                    note.lines.add(null == ch.value ? "" : ch.value);
                    break;
                case "SOUR":
                    loadCitation(ch, note.citations);
                    break;
                case "REFN":
                    UserReference u = new UserReference();
                    note.userReferences.add(u);
                    loadUserReference(ch, u);
                    break;
                case "RIN":
                    note.recIdNumber = new StringWithCustomTags(ch);
                    break;
                case "CHAN":
                    note.changeDate = new ChangeDate();
                    loadChangeDate(ch, note.changeDate);
                    break;
                default:
                    unknownTag(ch, note);
                    break;
            }
        }
    }

    /**
     * Load a personal name structure from a string tree node
     * 
     * @param st
     *            the node
     * @param pn
     *            the personal name structure to fill in
     */
    private void loadPersonalName(StringTree st, PersonalName pn) {
        pn.basic = st.value;
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "NPFX":
                    pn.prefix = new StringWithCustomTags(ch);
                    break;
                case "GIVN":
                    pn.givenName = new StringWithCustomTags(ch);
                    break;
                case "NICK":
                    pn.nickname = new StringWithCustomTags(ch);
                    break;
                case "SPFX":
                    pn.surnamePrefix = new StringWithCustomTags(ch);
                    break;
                case "SURN":
                    pn.surname = new StringWithCustomTags(ch);
                    break;
                case "NSFX":
                    pn.suffix = new StringWithCustomTags(ch);
                    break;
                case "SOUR":
                    loadCitation(ch, pn.citations);
                    break;
                case "NOTE":
                    loadNote(ch, pn.notes);
                    break;
                case "ROMN": {
                    PersonalNameVariation pnv = new PersonalNameVariation();
                    pn.romanized.add(pnv);
                    loadPersonalNameVariation(ch, pnv);
                    break;
                }
                case "FONE": {
                    PersonalNameVariation pnv = new PersonalNameVariation();
                    pn.phonetic.add(pnv);
                    loadPersonalNameVariation(ch, pnv);
                    break;
                }
                default:
                    unknownTag(ch, pn);
                    break;
            }
        }

    }

    /**
     * Load a personal name variation (romanization or phonetic version) from a string tree node
     * 
     * @param st
     *            the string tree node to load from
     * @param pnv
     *            the personal name variation to fill in
     */
    private void loadPersonalNameVariation(StringTree st, PersonalNameVariation pnv) {
        pnv.variation = st.value;
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "NPFX":
                    pnv.prefix = new StringWithCustomTags(ch);
                    break;
                case "GIVN":
                    pnv.givenName = new StringWithCustomTags(ch);
                    break;
                case "NICK":
                    pnv.nickname = new StringWithCustomTags(ch);
                    break;
                case "SPFX":
                    pnv.surnamePrefix = new StringWithCustomTags(ch);
                    break;
                case "SURN":
                    pnv.surname = new StringWithCustomTags(ch);
                    break;
                case "NSFX":
                    pnv.suffix = new StringWithCustomTags(ch);
                    break;
                case "SOUR":
                    loadCitation(ch, pnv.citations);
                    break;
                case "NOTE":
                    loadNote(ch, pnv.notes);
                    break;
                case "TYPE":
                    pnv.variationType = new StringWithCustomTags(ch);
                    break;
                default:
                    unknownTag(ch, pnv);
                    break;
            }
        }
    }

    /**
     * Load a place structure from a string tree node
     * 
     * @param st
     *            the node
     * @param place
     *            the place structure to fill in
     */
    private void loadPlace(StringTree st, Place place) {
        place.placeName = st.value;
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "FORM":
                    place.placeFormat = new StringWithCustomTags(ch);
                    break;
                case "SOUR":
                    loadCitation(ch, place.citations);
                    break;
                case "NOTE":
                    loadNote(ch, place.notes);
                    break;
                case "CONC":
                    place.placeName += ch.value;
                    break;
                case "CONT":
                    place.placeName += '\n' + (null == ch.value ? "" : ch.value);
                    break;
                case "ROMN": {
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but a romanized variation was specified on a place on line "
                                + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    NameVariation nv = new NameVariation();
                    place.romanized.add(nv);
                    nv.variation = ch.value;
                    for (StringTree gch : ch.children) {
                        if ("TYPE".equals(gch.tag)) {
                            nv.variationType = new StringWithCustomTags(gch);
                        } else {
                            unknownTag(gch, nv);
                        }
                    }
                    break;
                }
                case "FONE": {
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but a phonetic variation was specified on a place on line "
                                + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    NameVariation nv = new NameVariation();
                    place.phonetic.add(nv);
                    nv.variation = ch.value;
                    for (StringTree gch : ch.children) {
                        if ("TYPE".equals(gch.tag)) {
                            nv.variationType = new StringWithCustomTags(gch);
                        } else {
                            unknownTag(gch, nv);
                        }
                    }
                    break;
                }
                case "MAP":
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but a map coordinate was specified on a place on line "
                                + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    for (StringTree gch : ch.children) {
                        switch (gch.tag) {
                            case "LAT":
                                place.latitude = new StringWithCustomTags(gch);
                                break;
                            case "LONG":
                                place.longitude = new StringWithCustomTags(gch);
                                break;
                            default:
                                unknownTag(gch, place);
                                break;
                        }
                    }
                    break;
                default:
                    unknownTag(ch, place);
                    break;
            }
        }

    }

    /**
     * Load a repository for sources from a string tree node, and put it in the gedcom collection of repositories
     * 
     * @param st
     *            the node
     */
    private void loadRepository(StringTree st) {
        Repository r = getRepository(st.id);
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "NAME":
                    r.name = new StringWithCustomTags(ch);
                    break;
                case "ADDR":
                    r.address = new Address();
                    loadAddress(ch, r.address);
                    break;
                case "PHON":
                    r.phoneNumbers.add(new StringWithCustomTags(ch));
                    break;
                case "WWW":
                    r.wwwUrls.add(new StringWithCustomTags(ch));
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but WWW URL was specified on repository " + r.xref
                                + " on line " + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "FAX":
                    r.faxNumbers.add(new StringWithCustomTags(ch));
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but fax was specified on repository " + r.xref + " on line "
                                + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "EMAIL":
                    r.emails.add(new StringWithCustomTags(ch));
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but email was specified on repository " + r.xref + " on line "
                                + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "NOTE":
                    loadNote(ch, r.notes);
                    break;
                case "REFN":
                    UserReference u = new UserReference();
                    r.userReferences.add(u);
                    loadUserReference(ch, u);
                    break;
                case "RIN":
                    r.recIdNumber = new StringWithCustomTags(ch);
                    break;
                case "CHAN":
                    r.changeDate = new ChangeDate();
                    loadChangeDate(ch, r.changeDate);
                    break;
              /*  case "CHAN":
                    r.changeDate = new ChangeDate();
                    loadChangeDate(ch, r.changeDate);
                    break;*/
                default:
                    unknownTag(ch, r);
                    break;
            }
        }

    }

    /**
     * Load a reference to a repository in a source, from a string tree node
     * 
     *
     * @param st
     *            the node
     * @return the RepositoryCitation loaded
     */
    private RepositoryCitation loadRepositoryCitation(StringTree st) {
        RepositoryCitation r = new RepositoryCitation();
        r.repositoryXref = st.value;
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "NOTE":
                    loadNote(ch, r.notes);
                    break;
                case "CALN":
                    SourceCallNumber scn = new SourceCallNumber();
                    r.callNumbers.add(scn);
                    scn.callNumber = new StringWithCustomTags(ch.value);
                    for (StringTree gch : ch.children) {
                        if ("MEDI".equals(gch.tag)) {
                            scn.mediaType = new StringWithCustomTags(gch);
                        } else {
                            unknownTag(gch, scn.callNumber);
                        }
                    }
                    break;
                default:
                    unknownTag(ch, r);
                    break;
            }
        }
        return r;
    }

    /**
     * Load the root level items for the gedcom
     * 
     * @param st
     *            the root of the string tree
     * @throws GedcomParserException
     *             if the data cannot be parsed because it's not in the format expected
     */
    private void loadRootItems(StringTree st) throws GedcomParserException {
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "HEAD":
                    loadHeader(ch);
                    break;
                case "SUBM":
                    loadSubmitter(ch);
                    break;
                case "INDI":
                    loadIndividual(ch);
                    break;
                case "SUBN":
                    loadSubmission(ch);
                    break;
                case "NOTE":
                    loadRootNote(ch);
                    break;
                case "FAM":
                    loadFamily(ch);
                    break;
                case "TRLR":
                    gedcom.trailer = new Trailer();
                    break;
                case "SOUR":
                    loadSource(ch);
                    break;
                case "REPO":
                    loadRepository(ch);
                    break;
                case "OBJE":
                    loadMultimediaRecord(ch);
                    break;
                default:
                    unknownTag(ch, gedcom);
                    break;
            }
        }
    }

    /**
     * Load a note at the root level of the GEDCOM. All these should have &#64;ID&#64;'s and thus should get added to
     * the GEDCOM's collection of notes rather than the one passed to <code>loadNote()</code>
     * 
     * @param ch
     *            the child nodes to be loaded as a note
     * @throws GedcomParserException
     *             if the data cannot be parsed because it's not in the format expected
     */
    private void loadRootNote(StringTree ch) throws GedcomParserException {
        List<Note> dummyList = new ArrayList<>();
        loadNote(ch, dummyList);
        if (0 < dummyList.size()) {
            throw new GedcomParserException("At root level NOTE structures should have @ID@'s");
        }

    }

    /**
     * Load a source (which may be referenced later) from a source tree node, and put it in the gedcom collection of
     * sources.
     * 
     * @param st
     *            the node
     */
    private void loadSource(StringTree st) {
        Source s = getSource(st.id);
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "DATA":
                    s.data = new SourceData();
                    loadSourceData(ch, s.data);
                    break;
                case "TITL":
                    loadMultiLinesOfText(ch, s.title, s);
                    break;
                case "PUBL":
                    loadMultiLinesOfText(ch, s.publicationFacts, s);
                    break;
                case "TEXT":
                    loadMultiLinesOfText(ch, s.sourceText, s);
                    break;
                case "ABBR":
                    s.sourceFiledBy = new StringWithCustomTags(ch);
                    break;
                case "AUTH":
                    loadMultiLinesOfText(ch, s.originatorsAuthors, s);
                    break;
                case "REPO":
                    s.repositoryCitation = loadRepositoryCitation(ch);
                    break;
                case "NOTE":
                    loadNote(ch, s.notes);
                    break;
                case "OBJE":
                    loadMultimediaLink(ch, s.multimedia);
                    break;
                case "REFN":
                    UserReference u = new UserReference();
                    s.userReferences.add(u);
                    loadUserReference(ch, u);
                    break;
                case "RIN":
                    s.recIdNumber = new StringWithCustomTags(ch);
                    break;
                case "CHAN":
                    s.changeDate = new ChangeDate();
                    loadChangeDate(ch, s.changeDate);
                    break;
                default:
                    unknownTag(ch, s);
                    break;
            }
        }
    }

    /**
     * Load data for a source from a string tree node into a source data structure
     * 
     * @param st
     *            the node
     * @param data
     *            the source data structure
     */
    private void loadSourceData(StringTree st, SourceData data) {
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "EVEN":
                    loadSourceDataEventRecorded(ch, data);
                    break;
                case "NOTE":
                    loadNote(ch, data.notes);
                    break;
                case "AGNC":
                    data.respAgency = new StringWithCustomTags(ch);
                    break;
                default:
                    unknownTag(ch, data);
                    break;
            }
        }
    }

    /**
     * Load the data for a recorded event from a string tree node
     * 
     * @param st
     *            the node
     * @param data
     *            the source data
     */
    private void loadSourceDataEventRecorded(StringTree st, SourceData data) {
        EventRecorded e = new EventRecorded();
        data.eventsRecorded.add(e);
        e.eventType = st.value;
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "DATE":
                    e.datePeriod = new StringWithCustomTags(ch);
                    break;
                case "PLAC":
                    e.jurisdiction = new StringWithCustomTags(ch);
                    break;
                default:
                    unknownTag(ch, data);
                    break;
            }
        }

    }

    /**
     * Load a source system structure from a string tree node
     * 
     * @param st
     *            the node
     * @param sourceSystem
     *            the source system structure
     */
    private void loadSourceSystem(StringTree st, SourceSystem sourceSystem) {
        sourceSystem.systemId = st.value;
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "VERS":
                    sourceSystem.versionNum = new StringWithCustomTags(ch);
                    break;
                case "NAME":
                    sourceSystem.productName = new StringWithCustomTags(ch);
                    break;
                case "CORP":
                    sourceSystem.corporation = new Corporation();
                    loadCorporation(ch, sourceSystem.corporation);
                    break;
                case "DATA":
                    sourceSystem.sourceData = new HeaderSourceData();
                    loadHeaderSourceData(ch, sourceSystem.sourceData);
                    break;
                default:
                    unknownTag(ch, sourceSystem);
                    break;
            }
        }
    }

    /**
     * Load the submission structure from a string tree node
     * 
     * @param st
     *            the node
     */
    private void loadSubmission(StringTree st) {
        Submission s = new Submission(st.id);
        gedcom.submission = s;
        if (null == gedcom.header) {
            gedcom.header = new Header();
        }
        if (null == gedcom.header.submission) {
            /*
             * The GEDCOM spec puts a cross reference to the root-level SUBN element in the HEAD structure. Now that we
             * have a submission object, represent that cross reference in the header object
             */
            gedcom.header.submission = s;
        }
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "SUBM":
                    gedcom.submission.submitter = getSubmitter(ch.value);
                    break;
                case "FAMF":
                    gedcom.submission.nameOfFamilyFile = new StringWithCustomTags(ch);
                    break;
                case "TEMP":
                    gedcom.submission.templeCode = new StringWithCustomTags(ch);
                    break;
                case "ANCE":
                    gedcom.submission.ancestorsCount = new StringWithCustomTags(ch);
                    break;
                case "DESC":
                    gedcom.submission.descendantsCount = new StringWithCustomTags(ch);
                    break;
                case "ORDI":
                    gedcom.submission.ordinanceProcessFlag = new StringWithCustomTags(ch);
                    break;
                case "RIN":
                    gedcom.submission.recIdNumber = new StringWithCustomTags(ch);
                    break;
                default:
                    unknownTag(ch, gedcom.submission);
                    break;
            }
        }

    }

    /**
     * Load a submitter from a string tree node into the gedcom global collection of submitters
     * 
     * @param st
     *            the node
     */
    private void loadSubmitter(StringTree st) {
        Submitter submitter = getSubmitter(st.id);
        for (StringTree ch : st.children) {
            switch (ch.tag) {
                case "NAME":
                    submitter.name = new StringWithCustomTags(ch);
                    break;
                case "ADDR":
                    submitter.address = new Address();
                    loadAddress(ch, submitter.address);
                    break;
                case "PHON":
                    submitter.phoneNumbers.add(new StringWithCustomTags(ch));
                    break;
                case "WWW":
                    submitter.wwwUrls.add(new StringWithCustomTags(ch));
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but WWW URL number was specified on submitter on line "
                                + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "FAX":
                    submitter.faxNumbers.add(new StringWithCustomTags(ch));
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but fax number was specified on submitter on line "
                                + ch.lineNum + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "EMAIL":
                    submitter.emails.add(new StringWithCustomTags(ch));
                    if (g55()) {
                        warnings.add("GEDCOM version is 5.5 but email was specified on submitter on line " + ch.lineNum
                                + ", which is a GEDCOM 5.5.1 feature."
                                + "  Data loaded but cannot be re-written unless GEDCOM version changes.");
                    }
                    break;
                case "LANG":
                    submitter.languagePref.add(new StringWithCustomTags(ch));
                    break;
                case "CHAN":
                    submitter.changeDate = new ChangeDate();
                    loadChangeDate(ch, submitter.changeDate);
                    break;
                case "OBJE":
                    loadMultimediaLink(ch, submitter.multimedia);
                    break;
                case "RIN":
                    submitter.recIdNumber = new StringWithCustomTags(ch);
                    break;
                case "RFN":
                    submitter.regFileNumber = new StringWithCustomTags(ch);
                    break;
                case "NOTE":
                    loadNote(ch, submitter.notes);
                    break;
                default:
                    unknownTag(ch, submitter);
                    break;
            }
        }
    }

    /**
     * Load a user reference to from a string tree node
     * 
     * @param st
     *            the string tree node
     * @param u
     *            the user reference object
     */
    private static void loadUserReference(StringTree st, UserReference u) {
        u.referenceNum = st.value;
        if (!st.children.isEmpty()) {
            u.type = st.children.get(0).value;
        }

    }

    /**
     * Default handler for a tag that the parser was not expecting to see. If the tag begins with an underscore, it is a
     * user-defined tag, which is stored in the customTags collection of the passed in element, and returns. If it does
     * not begin with an underscore, it is presumably a real tag from the spec and should be processed, so that would
     * indicate a bug in the parser, or a bad tag that indicates a data error.
     * 
     * @param node
     *            the node containing the unknown tag.
     * @param element
     *            the element that the node is part of, so if it's a custom tag, this unknown tag can be added to this
     *            node's collection of custom tags
     */
    private void unknownTag(StringTree node, AbstractElement element) {
        if (node.tag.startsWith("_")) {
            element.customTags.add(node);
            return;
        }
        unknownTagNoUserDefinedTagsAllowed(node);
    }

    /**
     * This is the handler for when a node is read that is not a user-defined tag, but that the parser does not
     * recognize as valid or does not have a handler for...either of which is bad, so it gets added to the errors
     * collection.
     * 
     * @param node
     *            the node with the unrecognized tag
     */
    private void unknownTagNoUserDefinedTagsAllowed(StringTree node) {
        StringBuilder sb = new StringBuilder("Line " + node.lineNum + ": Cannot handle tag ");
        sb.append(node.tag);
        StringTree st = node;
        while (null != st.parent) {
            st = st.parent;
            sb.append(", child of ").append(st.tag);
            if (null != st.id) {
                sb.append(' ').append(st.id);
            }
            sb.append(" on line ").append(st.lineNum);
        }
        errors.add(sb.toString());
    }

    /**
     * A convenience method to write all the parsing errors and warnings to System.err.
     */
    void dumpErrorsAndWarnings() {
        if (errors.isEmpty()) {
            System.out.println("No errors.");
        } else {
            System.out.println("Errors:");
            for (String e : errors) {
                System.out.println("  " + e);
            }
        }
        if (warnings.isEmpty()) {
            System.out.println("No warnings.");
        } else {
            System.out.println("Warnings:");
            for (String w : warnings) {
                System.out.println("  " + w);
            }
        }
    }

    /**
     * Load a gedcom file from an input stream and create an object hierarchy from the data therein.
     * 
     * @param stream
     *            the stream to load from
     * @throws IOException
     *             if the file cannot be read
     * @throws GedcomParserException
     *             if the file cannot be parsed
     */
    public void load(BufferedInputStream stream) throws IOException, GedcomParserException {
        if (verbose) {
            System.out.println("Loading and parsing GEDCOM from input stream");
        }
        StringTree stringTree = GedcomParserHelper.readStream(stream);
        loadRootItems(stringTree);
        if (verbose) {
            dumpErrorsAndWarnings();
        }
    }

    /**
     * Load a gedcom file by filename and create an object heirarchy from the data therein.
     * 
     * @param filename
     *            the name of the file to load
     * @throws IOException
     *             if the file cannot be read
     * @throws GedcomParserException
     *             if the file cannot be parsed
     */
    public void load(String filename) throws IOException, GedcomParserException {
        if (verbose) {
            System.out.println("Loading and parsing GEDCOM from file " + filename);
        }
        StringTree stringTree = GedcomParserHelper.readFile(filename);
        loadRootItems(stringTree);
        if (verbose) {
            dumpErrorsAndWarnings();
        }
    }

}
