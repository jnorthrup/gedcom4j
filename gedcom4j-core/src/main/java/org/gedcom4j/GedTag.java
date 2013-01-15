package org.gedcom4j;

import static org.gedcom4j.GedEvent.*;

/**
 * Created with IntelliJ IDEA.
 * User: jim
 * Date: 1/11/13
 * Time: 12:33 AM
 * To change this template use File | Settings | File Templates.
 */
public enum GedTag {
    /**
     * A short name of a title, description, or name.
     */


    ABBR(ABBREVIATION), /**
     * The contemporary place, usually required for postal purposes, of an individual, a submitter of information, a repository, a business, a school, or a company.
     */
    ADDR(ADDRESS), /**
     * The first line of an address.
     */
    ADR1(ADDRESS1), /**
     * The second line of an address.
     */
    ADR2(ADDRESS2), /**
     * Pertaining to creation of a child-parent relationship that does not exist biologically.
     */
    ADOP(ADOPTION), /**
     * A unique permanent record file number of an individual record stored in Ancestral File.
     */
    AFN(GedEvent.AFN), /**
     * The age of the individual at the time an event occurred, or the age listed in the document.
     */
    AGE(GedEvent.AGE), /**
     * The institution or individual having authority and/or responsibility to manage or govern.
     */
    AGNC(AGENCY), /**
     * An indicator to link different record descriptions of a person who may be the same person.
     */
    ALIA(ALIAS), /**
     * Pertaining to forbearers of an individual.
     */
    ANCE(ANCESTORS), /**
     * Indicates an interest in additional research for ancestors of this individual. (See also DESI.)
     */
    ANCI(ANCES_INTEREST), /**
     * Declaring a marriage void from the beginning (never existed).
     */
    ANUL(ANNULMENT), /**
     * An indicator to link friends, neighbors, relatives, or associates of an individual.
     */
    ASSO(ASSOCIATES), /**
     * The name of the individual who created or compiled information.
     */
    AUTH(AUTHOR), /**
     * The event of baptism performed at age eight or later by priesthood authority of the LDS Church. (See also BAPM)
     * ge
     */
    BAPL(BAPTISM_LDS), /**
     * The event of baptism (not LDS), performed in infancy or later. (See also BAPL, and CHR.)
     */
    BAPM(BAPTISM), /**
     * The ceremonial event held when a Jewish boy reaches age 13.
     */
    BARM(BAR_MITZVAH), /**
     * The ceremonial event held when a Jewish girl reaches age 13, also known as "Bat Mitzvah."
     */
    BASM(BAS_MITZVAH), /**
     * The event of entering into life.
     */
    BIRT(BIRTH), /**
     * A religious event of bestowing divine care or intercession. Sometimes given in connection with a naming ceremony.
     */
    BLES(BLESSING), /**
     * A grouping of data used as input to a multimedia system that processes binary data to represent images, sound, and video.
     */
    BLOB(BINARY_OBJECT), /**
     * The event of the proper disposing of the mortal remains of a deceased person.
     */
    BURI(BURIAL), /**
     * The number used by a repository to identify the specific items in its collections.
     */
    CALN(CALL_NUMBER), /**
     * The name of an individual's rank or status in society, based on racial or religious differences, or differences in wealth, inherited rank, profession, occupation, etc.
     */
    CAST(CASTE), /**
     * A description of the cause of the associated event or fact, such as the cause of death.
     */
    CAUS(CAUSE), /**
     * The event of the periodic count of the population for a designated locality, such as a national or state Census.
     */
    CENS(CENSUS), /**
     * Indicates a change, correction, or modification. Typically used in connection with a DATE to specify when a change in information occurred.
     */
    CHAN(CHANGE), /**
     * An indicator of the character set used in writing this automated information.
     */
    CHAR(CHARACTER), /**
     * The natural, adopted, or sealed (LDS) child of a father and a mother.
     */
    CHIL(CHILD), /**
     * The religious event (not LDS) of baptizing and/or naming a child.
     */
    CHR(CHRISTENING), /**
     * The religious event (not LDS) of baptizing and/or naming an adult person.
     */
    CHRA(ADULT_CHRISTENING), /**
     * A lower level jurisdictional unit. Normally an incorporated municipal unit.
     */
    CITY(GedEvent.CITY), /**
     * An indicator that additional data belongs to the superior value. The information from the CONC value is to be connected to the value of the superior preceding line without a space and without a carriage return and/or new line character. Values that are split for a CONC tag must always be split at a non-space. If the value is split on a space the space will be lost when concatenation takes place. This is because of the treatment that spaces get as a GEDCOM delimiter, many GEDCOM values are trimmed of trailing spaces and some systems look for the first non-space starting after the tag to determine the beginning of the value.
     */
    CONC(CONCATENATION), /**
     * The religious event (not LDS) of conferring the gift of the Holy Ghost and, among protestants, full church membership.
     */
    CONF(CONFIRMATION), /**
     * The religious event by which a person receives membership in the LDS Church.
     */
    CONL(CONFIRMATION_L), /**
     * An indicator that additional data belongs to the superior value. The information from the CONT value is to be connected to the value of the superior preceding line with a carriage return and/or new line character. Leading spaces could be important to the formatting of the resultant text. When importing values from CONT lines the reader should assume only one delimiter character following the CONT tag. Assume that the rest of the leading spaces are to be a part of the value.
     */
    CONT(CONTINUED), /**
     * A statement that accompanies data to protect it from unlawful duplication and distribution.
     */
    COPR(COPYRIGHT), /**
     * A name of an institution, agency, corporation, or company.
     */
    CORP(CORPORATE), /**
     * Disposal of the remains of a person's body by fire.
     */
    CREM(CREMATION), /**
     * The name or code of the country.
     */
    CTRY(COUNTRY), /**
     * Pertaining to stored automated information.
     */
    DATA(GedEvent.DATA), /**
     * The time of an event in a calendar format.
     */
    DATE(GedEvent.DATE), /**
     * The event when mortal life terminates.
     */
    DEAT(DEATH), /**
     * Pertaining to offspring of an individual.
     */
    DESC(DESCENDANTS), /**
     * Indicates an interest in research to identify additional descendants of this individual. (See also ANCI.)
     */
    DESI(DESCENDANT_INT), /**
     * A system receiving data.
     */
    DEST(DESTINATION), /**
     * An event of dissolving a marriage through civil action.
     */
    DIV(DIVORCE), /**
     * An event of filing for a divorce by a spouse.
     */
    DIVF(DIVORCE_FILED), /**
     * The physical characteristics of a person, place, or thing.
     */
    DSCR(PHY_DESCRIPTION), /**
     * Indicator of a level of education attained.
     */
    EDUC(EDUCATION), /**
     * An event of leaving one's homeland with the intent of residing elsewhere.
     */
    EMIG(EMIGRATION), /**
     * A religious event where an endowment ordinance for an individual was performed by priesthood authority in an LDS temple.
     */
    ENDL(ENDOWMENT), /**
     * An event of recording or announcing an agreement between two people to become married.
     */
    ENGA(ENGAGEMENT), /**
     * A noteworthy happening related to an individual, a group, or an organization.
     */
    EVEN(EVENT), /**
     * Identifies a legal, common law, or other customary relationship of man and woman and their children, if any, or a family created by virtue of the birth of a child to its biological father and mother.
     */
    FAM(FAMILY), /**
     * Identifies the family in which an individual appears as a child.
     */
    FAMC(FAMILY_CHILD), /**
     * Pertaining to, or the name of, a family file. Names stored in a file that are assigned to a family for doing temple ordinance work.
     */
    FAMF(FAMILY_FILE), /**
     * Identifies the family in which an individual appears as a spouse.
     */
    FAMS(FAMILY_SPOUSE), /**
     * A religious rite, the first act of sharing in the Lord's supper as part of church worship.
     */
    FCOM(FIRST_COMMUNION), /**
     * An information storage place that is ordered and arranged for preservation and reference.
     */
    FILE(GedEvent.FILE), /**
     * An assigned name given to a consistent format in which information can be conveyed.
     */
    FORM(FORMAT), /**
     * Information about the use of GEDCOM in a transmission.
     */
    GEDC(GEDCOM), /**
     * A given or earned name used for official identification of a person.
     */
    GIVN(GIVEN_NAME),
    /**
     * An event of awarding educational diplomas or degrees to individuals.
     */
    GRAD(GRADUATION), /**
     * Identifies information pertaining to an entire GEDCOM transmission.
     */
    HEAD(HEADER), /**
     * An individual in the family role of a married man or father.
     */
    HUSB(HUSBAND), /**
     * A number assigned to identify a person within some significant external system.
     */
    IDNO(IDENT_NUMBER), /**
     * An event of entering into a new locality with the intent of residing there.
     */
    IMMI(IMMIGRATION), /**
     * A person.
     */
    INDI(INDIVIDUAL), /**
     * The name of the language used in a communication or transmission of information.
     */
    LANG(LANGUAGE), /**
     * A role of an individual acting as a person receiving a bequest or legal devise.
     */
    LEGA(LEGATEE), /**
     * An event of an official public notice given that two people intend to marry.
     */
    MARB(MARRIAGE_BANN), /**
     * An event of recording a formal agreement of marriage, including the prenuptial agreement in which marriage partners reach agreement about the property rights of one or both, securing property to their children.
     */
    MARC(MARR_CONTRACT), /**
     * An event of obtaining a legal license to marry.
     */
    MARL(MARR_LICENSE), /**
     * A legal, common-law, or customary event of creating a family unit of a man and a woman as husband and wife.
     */
    MARR(MARRIAGE), /**
     * An event of creating an agreement between two people contemplating marriage, at which time they agree to release or modify property rights that would otherwise arise from the marriage.
     */
    MARS(MARR_SETTLEMENT), /**
     * Identifies information about the media or having to do with the medium in which information is stored.
     */
    MEDI(MEDIA), /**
     * A word or combination of words used to help identify an individual, title, or other item. More than one NAME line should be used for people who were known by multiple names.
     */
    NAME(GedEvent.NAME), /**
     * The national heritage of an individual.
     */
    NATI(NATIONALITY), /**
     * The event of obtaining citizenship.
     */
    NATU(NATURALIZATION), /**
     * The number of children that this person is known to be the parent of (all marriages) when subordinate to an individual, or that belong to this family when subordinate to a FAM_RECORD.
     */
    NCHI(CHILDREN_COUNT), /**
     * A descriptive or familiar that is used instead of, or in addition to, one's proper name.
     */
    NICK(NICKNAME), /**
     * The number of times this person has participated in a family as a spouse or parent.
     */
    NMR(MARRIAGE_COUNT), /**
     * Additional information provided by the submitter for understanding the enclosing data.
     */
    NOTE(GedEvent.NOTE), /**
     * Text which appears on a name line before the given and surname parts of a name.
     * i.e. ( Lt. Cmndr. ) Joseph /Allen/ jr.
     * In this example Lt. Cmndr. is considered as the name prefix portion.
     */
    NPFX(NAME_PREFIX), /**
     * Text which appears on a name line after or behind the given and surname parts of a name.
     * i.e. Lt. Cmndr. Joseph /Allen/ ( jr. )
     * In this example jr. is considered as the name suffix portion.
     */
    NSFX(NAME_SUFFIX), /**
     * Pertaining to a grouping of attributes used in describing something. Usually referring to the data required to represent a multimedia object, such an audio recording, a photograph of a person, or an image of a document.
     */
    OBJE(OBJECT), /**
     * The type of work or profession of an individual.
     */
    OCCU(OCCUPATION), /**
     * Pertaining to a religious ordinance in general.
     */
    ORDI(ORDINANCE), /**
     * A religious event of receiving authority to act in religious matters.
     */
    ORDN(ORDINATION), /**
     * A number or description to identify where information can be found in a referenced work.
     */
    PAGE(GedEvent.PAGE), /**
     * Information pertaining to an individual to parent lineage chart.
     */
    PEDI(PEDIGREE), /**
     * A unique number assigned to access a specific telephone.
     */
    PHON(PHONE), /**
     * A jurisdictional name to identify the place or location of an event.
     */
    PLAC(PLACE), /**
     * A code used by a postal service to identify an area to facilitate mail handling.
     */
    POST(POSTAL_CODE), /**
     * An event of judicial determination of the validity of a will. May indicate several related court activities over several dates.
     */
    PROB(PROBATE), /**
     * Pertaining to possessions such as real estate or other property of interest.
     */
    PROP(PROPERTY), /**
     * Refers to when and/or were a work was published or created.
     */
    PUBL(PUBLICATION), /**
     * An assessment of the certainty of the evidence to support the conclusion drawn from evidence.
     */
    QUAY(QUALITY_OF_DATA), /**
     * A description or number used to identify an item for filing, storage, or other reference purposes.
     */
    REFN(REFERENCE), /**
     * A relationship value between the indicated contexts.
     */
    RELA(RELATIONSHIP), /**
     * A religious denomination to which a person is affiliated or for which a record applies.
     */
    RELI(RELIGION), /**
     * An institution or person that has the specified item as part of their collection(s).
     */
    REPO(REPOSITORY), /**
     * The act of dwelling at an address for a period of time.
     */
    RESI(RESIDENCE), /**
     * A processing indicator signifying access to information has been denied or otherwise restricted.
     */
    RESN(RESTRICTION), /**
     * An event of exiting an occupational relationship with an employer after a qualifying time period.
     */
    RETI(RETIREMENT), /**
     * A permanent number assigned to a record that uniquely identifies it within a known file.
     */
    RFN(REC_FILE_NUMBER), /**
     * A number assigned to a record by an originating automated system that can be used by a receiving system to report results pertaining to that record.
     */
    RIN(REC_ID_NUMBER), /**
     * A name given to a role played by an individual in connection with an event.
     */
    ROLE(GedEvent.ROLE), /**
     * Indicates the sex of an individual--male or female.
     */
    SEX(GedEvent.SEX), /**
     * A religious event pertaining to the sealing of a child to his or her parents in an LDS temple ceremony.
     */
    SLGC(SEALING_CHILD), /**
     * A religious event pertaining to the sealing of a husband and wife in an LDS temple ceremony.
     */
    SLGS(SEALING_SPOUSE), /**
     * The initial or original material from which information was obtained.
     */
    SOUR(SOURCE), /**
     * A name piece used as a non-indexing pre-part of a surname.
     */
    SPFX(SURN_PREFIX), /**
     * A number assigned by the United States Social Security Administration. Used for tax identification purposes.
     */
    SSN(SOC_SEC_NUMBER), /**
     * A geographical division of a larger jurisdictional area, such as a State within the United States of America.
     */
    STAE(STATE), /**
     * An assessment of the state or condition of something.
     */
    STAT(STATUS), /**
     * An individual or organization who contributes genealogical data to a file or transfers it to someone else.
     */
    SUBM(SUBMITTER), /**
     * Pertains to a collection of data issued for processing.
     */
    SUBN(SUBMISSION), /**
     * A family name passed on or used by members of a family.
     */
    SURN(SURNAME), /**
     * The name or code that represents the name a temple of the LDS Church.
     */
    TEMP(TEMPLE), /**
     * The exact wording found in an original source document.
     */
    TEXT(GedEvent.TEXT), /**
     * A time value in a 24-hour clock format, including hours, minutes, and optional seconds, separated by a colon (:). Fractions of seconds are shown in decimal notation.
     */
    TIME(GedEvent.TIME), /**
     * A description of a specific writing or other work, such as the title of a book when used in a source context, or a formal designation used by an individual in connection with positions of royalty or other social status, such as Grand Duke.
     */
    TITL(TITLE), /**
     * At level 0, specifies the end of a GEDCOM transmission.
     */
    TRLR(TRAILER), /**
     * A further qualification to the meaning of the associated superior tag. The value does not have any computer processing reliability. It is more in the form of a short one or two word note that should be displayed any time the associated data is displayed.
     */
    TYPE(GedEvent.TYPE), /**
     * Indicates which version of a product, item, or publication is being used or referenced.
     */
    VERS(VERSION), /**
     * An individual in the role as a mother and/or married woman.
     */
    WIFE(GedEvent.WIFE), /**
     * A legal document treated as an event, by which a person disposes of his or her estate, to take effect after death. The event date is the date the will was signed while the person was alive. (See also PROBate.)
     */
    WILL(GedEvent.WILL),;

    GedTag(GedEvent event) {
        //To change body of created methods use File | Settings | File Templates.
    }
}