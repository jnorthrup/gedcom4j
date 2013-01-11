package org.gedcom4j;

/**
 * The GEDCOM Standard Release 5.5
 * Appendix A
 * <p/>
 * Lineage-Linked GEDCOM Tag Definition
 * <p/>
 * Introduction
 * <p/>
 * Appendix A is a glossary of the tags used in the Lineage-Linked GEDCOM Form. These tags are used in a hierarchal structure to describe individuals in terms of their families, names, dates, places, events, roles, sources, relationships. Control information and other kinds of data intended for computer processing is also included. (An example of the tags used in the Lineage-Linked Form begins here .) To ensure all transmitted information in the Lineage-Linked GEDCOM is uniformly identified the standardized tags cannot be placed in any other context than shown in Chapter 2. It is legal to extend the context of the form, but only by using user-defined tags which must begin with an underscore. This will not violate the lineage-linked GEDCOM standard unless the context for the grammar of the Lineage-Linked GEDCOM Form is violated. The use of the underscore in the user tag name is to signal a non-standard construct is being used. This notifies the reading system of a discrepancy and will avoid future conflicts with tags that may be standardized in subsequent GEDCOM releases.
 * <p/>
 * Lineage-Linked GEDCOM Tag Definitions
 * <p/>
 * This section provides the definitions of the standardized GEDCOM tags and shows their formal name inside of the {braces}. The formal names are not used in place of the tag. Full understanding must come from the context in which the tag is used.
 * Created with IntelliJ IDEA.
  */
public enum GedEvent {          /**
           
A short name of a title, description, or name.
*/
 ABBREVIATION  (GedTag.ABBR), /**

The contemporary place, usually required for postal purposes, of an individual, a submitter of information, a repository, a business, a school, or a company.
*/
 ADDRESS  (GedTag.ADDR), /**

The first line of an address.
*/     ADDRESS1(GedTag.ADR1), /**

The second line of an address.
*/    ADDRESS2(GedTag.ADR2 ), /**

Pertaining to creation of a child-parent relationship that does not exist biologically.
*/
 ADOPTION  (GedTag.ADOP), /**

A unique permanent record file number of an individual record stored in Ancestral File.
*/
 AFN  (GedTag.AFN), /**

The age of the individual at the time an event occurred, or the age listed in the document.
*/
 AGE  (GedTag.AGE), /**

The institution or individual having authority and/or responsibility to manage or govern.
*/
 AGENCY  (GedTag.AGNC), /**

An indicator to link different record descriptions of a person who may be the same person.
*/
 ALIAS  (GedTag.ALIA), /**

Pertaining to forbearers of an individual.
*/
 ANCESTORS  (GedTag.ANCE), /**

Indicates an interest in additional research for ancestors of this individual. (See also DESI.)
*/
 ANCES_INTEREST  (GedTag.ANCI), /**

Declaring a marriage void from the beginning (never existed).
*/
 ANNULMENT  (GedTag.ANUL), /**

An indicator to link friends, neighbors, relatives, or associates of an individual.
*/
 ASSOCIATES  (GedTag.ASSO), /**

The name of the individual who created or compiled information.
*/
 AUTHOR  (GedTag.AUTH), /**

The event of baptism performed at age eight or later by priesthood authority of the LDS Church. (See also BAPM)
ge */
 BAPTISM_LDS  (GedTag.BAPL), /**

The event of baptism (not LDS), performed in infancy or later. (See also BAPL, and CHR.)
*/
 BAPTISM  (GedTag.BAPM), /**

The ceremonial event held when a Jewish boy reaches age 13.
*/
 BAR_MITZVAH  (GedTag.BARM), /**

The ceremonial event held when a Jewish girl reaches age 13, also known as "Bat Mitzvah."
*/
 BAS_MITZVAH  (GedTag.BASM), /**

The event of entering into life.
*/
 BIRTH  (GedTag.BIRT), /**

A religious event of bestowing divine care or intercession. Sometimes given in connection with a naming ceremony.
*/
 BLESSING  (GedTag.BLES), /**

A grouping of data used as input to a multimedia system that processes binary data to represent images, sound, and video.
*/
 BINARY_OBJECT  (GedTag.BLOB), /**

The event of the proper disposing of the mortal remains of a deceased person.
*/
 BURIAL  (GedTag.BURI), /**

The number used by a repository to identify the specific items in its collections.
*/
 CALL_NUMBER  (GedTag.CALN), /**

    The name of an individual's rank or status in society, based on racial or religious differences, or differences in wealth, inherited rank, profession, occupation, etc.
*/
 CASTE  (GedTag.CAST), /**

A description of the cause of the associated event or fact, such as the cause of death.
*/
 CAUSE  (GedTag.CAUS), /**

The event of the periodic count of the population for a designated locality, such as a national or state Census.
*/
 CENSUS  (GedTag.CENS), /**

Indicates a change, correction, or modification. Typically used in connection with a DATE to specify when a change in information occurred.
*/
 CHANGE  (GedTag.CHAN), /**

An indicator of the character set used in writing this automated information.
*/
 CHARACTER  (GedTag.CHAR), /**

The natural, adopted, or sealed (LDS) child of a father and a mother.
*/
 CHILD  (GedTag.CHIL), /**

The religious event (not LDS) of baptizing and/or naming a child.
*/
 CHRISTENING  (GedTag.CHR), /**

The religious event (not LDS) of baptizing and/or naming an adult person.
*/
 ADULT_CHRISTENING  (GedTag.CHRA), /**

A lower level jurisdictional unit. Normally an incorporated municipal unit.
*/
 CITY  (GedTag.CITY), /**

An indicator that additional data belongs to the superior value. The information from the CONC value is to be connected to the value of the superior preceding line without a space and without a carriage return and/or new line character. Values that are split for a CONC tag must always be split at a non-space. If the value is split on a space the space will be lost when concatenation takes place. This is because of the treatment that spaces get as a GEDCOM delimiter, many GEDCOM values are trimmed of trailing spaces and some systems look for the first non-space starting after the tag to determine the beginning of the value.
*/
 CONCATENATION  (GedTag.CONC), /**

The religious event (not LDS) of conferring the gift of the Holy Ghost and, among protestants, full church membership.
*/
 CONFIRMATION  (GedTag.CONF), /**

The religious event by which a person receives membership in the LDS Church.
*/
 CONFIRMATION_L  (GedTag.CONL), /**

An indicator that additional data belongs to the superior value. The information from the CONT value is to be connected to the value of the superior preceding line with a carriage return and/or new line character. Leading spaces could be important to the formatting of the resultant text. When importing values from CONT lines the reader should assume only one delimiter character following the CONT tag. Assume that the rest of the leading spaces are to be a part of the value.
*/
 CONTINUED  (GedTag.CONT), /**

A statement that accompanies data to protect it from unlawful duplication and distribution.
*/
 COPYRIGHT  (GedTag.COPR), /**

A name of an institution, agency, corporation, or company.
*/
 CORPORATE  (GedTag.CORP), /**

Disposal of the remains of a person's body by fire.
*/
 CREMATION  (GedTag.CREM), /**

The name or code of the country.
*/
 COUNTRY  (GedTag.CTRY), /**

Pertaining to stored automated information.
*/
 DATA  (GedTag.DATA), /**

The time of an event in a calendar format.
*/
 DATE  (GedTag.DATE), /**

The event when mortal life terminates.
*/
 DEATH  (GedTag.DEAT), /**

Pertaining to offspring of an individual.
*/
 DESCENDANTS  (GedTag.DESC), /**
Indicates an interest in research to identify additional descendants of this individual. (See also ANCI.)
*/
 DESCENDANT_INT  (GedTag.DESI), /**

A system receiving data.
*/
 DESTINATION  (GedTag.DEST), /**

An event of dissolving a marriage through civil action.
*/
 DIVORCE  (GedTag.DIV), /**

An event of filing for a divorce by a spouse.
*/
 DIVORCE_FILED  (GedTag.DIVF), /**

The physical characteristics of a person, place, or thing.
*/
 PHY_DESCRIPTION  (GedTag.DSCR), /**

Indicator of a level of education attained.
*/
 EDUCATION  (GedTag.EDUC), /**

An event of leaving one's homeland with the intent of residing elsewhere.
*/
 EMIGRATION  (GedTag.EMIG), /**

A religious event where an endowment ordinance for an individual was performed by priesthood authority in an LDS temple.
*/
 ENDOWMENT  (GedTag.ENDL), /**

An event of recording or announcing an agreement between two people to become married.
*/
 ENGAGEMENT  (GedTag.ENGA), /**

A noteworthy happening related to an individual, a group, or an organization.
*/
 EVENT  (GedTag.EVEN), /**

Identifies a legal, common law, or other customary relationship of man and woman and their children, if any, or a family created by virtue of the birth of a child to its biological father and mother.
*/
 FAMILY  (GedTag.FAM), /**

Identifies the family in which an individual appears as a child.
*/
 FAMILY_CHILD  (GedTag.FAMC), /**

Pertaining to, or the name of, a family file. Names stored in a file that are assigned to a family for doing temple ordinance work.
*/
 FAMILY_FILE  (GedTag.FAMF), /**

Identifies the family in which an individual appears as a spouse.
*/
 FAMILY_SPOUSE  (GedTag.FAMS), /**

A religious rite, the first act of sharing in the Lord's supper as part of church worship.
*/
 FIRST_COMMUNION  (GedTag.FCOM), /**

An information storage place that is ordered and arranged for preservation and reference.
*/
 FILE  (GedTag.FILE), /**

An assigned name given to a consistent format in which information can be conveyed.
*/
 FORMAT  (GedTag.FORM), /**

Information about the use of GEDCOM in a transmission.
*/
 GEDCOM  (GedTag.GEDC), /**

A given or earned name used for official identification of a person.
*/
 GIVEN_NAME  (GedTag.GIVN),
                               /**
An event of awarding educational diplomas or degrees to individuals.
*/
 GRADUATION  (GedTag.GRAD), /**

Identifies information pertaining to an entire GEDCOM transmission.
*/
 HEADER  (GedTag.HEAD), /**

An individual in the family role of a married man or father.
*/
 HUSBAND  (GedTag.HUSB), /**

A number assigned to identify a person within some significant external system.
*/
 IDENT_NUMBER  (GedTag.IDNO), /**

An event of entering into a new locality with the intent of residing there.
*/
 IMMIGRATION  (GedTag.IMMI), /**

A person.
*/
 INDIVIDUAL  (GedTag.INDI), /**

The name of the language used in a communication or transmission of information.
*/
 LANGUAGE  (GedTag.LANG), /**

A role of an individual acting as a person receiving a bequest or legal devise.
*/
 LEGATEE  (GedTag.LEGA), /**

An event of an official public notice given that two people intend to marry.
*/
 MARRIAGE_BANN  (GedTag.MARB), /**

An event of recording a formal agreement of marriage, including the prenuptial agreement in which marriage partners reach agreement about the property rights of one or both, securing property to their children.
*/
 MARR_CONTRACT  (GedTag.MARC), /**

An event of obtaining a legal license to marry.
*/
 MARR_LICENSE  (GedTag.MARL), /**

A legal, common-law, or customary event of creating a family unit of a man and a woman as husband and wife.
*/
 MARRIAGE  (GedTag.MARR), /**

An event of creating an agreement between two people contemplating marriage, at which time they agree to release or modify property rights that would otherwise arise from the marriage.
*/
 MARR_SETTLEMENT  (GedTag.MARS), /**

Identifies information about the media or having to do with the medium in which information is stored.
*/
 MEDIA  (GedTag.MEDI), /**

A word or combination of words used to help identify an individual, title, or other item. More than one NAME line should be used for people who were known by multiple names.
*/
 NAME  (GedTag.NAME), /**

The national heritage of an individual.
*/
 NATIONALITY  (GedTag.NATI), /**

The event of obtaining citizenship.
*/
 NATURALIZATION  (GedTag.NATU), /**

The number of children that this person is known to be the parent of (all marriages) when subordinate to an individual, or that belong to this family when subordinate to a FAM_RECORD.
*/
 CHILDREN_COUNT  (GedTag.NCHI), /**

A descriptive or familiar that is used instead of, or in addition to, one's proper name.
*/
 NICKNAME  (GedTag.NICK), /**

The number of times this person has participated in a family as a spouse or parent.
*/
 MARRIAGE_COUNT  (GedTag.NMR), /**

Additional information provided by the submitter for understanding the enclosing data.
*/
 NOTE  (GedTag.NOTE), /**

Text which appears on a name line before the given and surname parts of a name.
            i.e. ( Lt. Cmndr. ) Joseph /Allen/ jr.
    In this example Lt. Cmndr. is considered as the name prefix portion.
*/
 NAME_PREFIX  (GedTag.NPFX), /**

Text which appears on a name line after or behind the given and surname parts of a name.
    i.e. Lt. Cmndr. Joseph /Allen/ ( jr. )
    In this example jr. is considered as the name suffix portion.
*/
 NAME_SUFFIX  (GedTag.NSFX), /**

Pertaining to a grouping of attributes used in describing something. Usually referring to the data required to represent a multimedia object, such an audio recording, a photograph of a person, or an image of a document.
*/
 OBJECT  (GedTag.OBJE), /**

The type of work or profession of an individual.
*/
 OCCUPATION  (GedTag.OCCU), /**

Pertaining to a religious ordinance in general.
*/
 ORDINANCE  (GedTag.ORDI), /**

A religious event of receiving authority to act in religious matters.
*/
 ORDINATION  (GedTag.ORDN), /**

A number or description to identify where information can be found in a referenced work.
*/
 PAGE  (GedTag.PAGE), /**

Information pertaining to an individual to parent lineage chart.
*/
 PEDIGREE  (GedTag.PEDI), /**

A unique number assigned to access a specific telephone.
*/
 PHONE  (GedTag.PHON), /**

A jurisdictional name to identify the place or location of an event.
*/
 PLACE  (GedTag.PLAC), /**

A code used by a postal service to identify an area to facilitate mail handling.
*/
 POSTAL_CODE  (GedTag.POST), /**

An event of judicial determination of the validity of a will. May indicate several related court activities over several dates.
*/
 PROBATE  (GedTag.PROB), /**

Pertaining to possessions such as real estate or other property of interest.
*/
 PROPERTY  (GedTag.PROP), /**

Refers to when and/or were a work was published or created.
*/
 PUBLICATION  (GedTag.PUBL), /**

An assessment of the certainty of the evidence to support the conclusion drawn from evidence.
*/
 QUALITY_OF_DATA  (GedTag.QUAY), /**

A description or number used to identify an item for filing, storage, or other reference purposes.
*/
 REFERENCE  (GedTag.REFN), /**

A relationship value between the indicated contexts.
*/
 RELATIONSHIP  (GedTag.RELA), /**

A religious denomination to which a person is affiliated or for which a record applies.
*/
 RELIGION  (GedTag.RELI), /**

An institution or person that has the specified item as part of their collection(s).
*/
 REPOSITORY  (GedTag.REPO), /**

The act of dwelling at an address for a period of time.
*/
 RESIDENCE  (GedTag.RESI), /**

A processing indicator signifying access to information has been denied or otherwise restricted.
*/
 RESTRICTION  (GedTag.RESN), /**

An event of exiting an occupational relationship with an employer after a qualifying time period.
*/
 RETIREMENT  (GedTag.RETI), /**

A permanent number assigned to a record that uniquely identifies it within a known file.
*/
 REC_FILE_NUMBER  (GedTag.RFN), /**

A number assigned to a record by an originating automated system that can be used by a receiving system to report results pertaining to that record.
*/
 REC_ID_NUMBER  (GedTag.RIN), /**

A name given to a role played by an individual in connection with an event.
*/
 ROLE  (GedTag.ROLE), /**

Indicates the sex of an individual--male or female.
*/
 SEX  (GedTag.SEX), /**

A religious event pertaining to the sealing of a child to his or her parents in an LDS temple ceremony.
*/
 SEALING_CHILD  (GedTag.SLGC), /**

A religious event pertaining to the sealing of a husband and wife in an LDS temple ceremony.
*/
 SEALING_SPOUSE  (GedTag.SLGS), /**

The initial or original material from which information was obtained.
*/
 SOURCE  (GedTag.SOUR), /**

A name piece used as a non-indexing pre-part of a surname.
*/
 SURN_PREFIX  (GedTag.SPFX), /**

A number assigned by the United States Social Security Administration. Used for tax identification purposes.
*/
 SOC_SEC_NUMBER  (GedTag.SSN), /**

A geographical division of a larger jurisdictional area, such as a State within the United States of America.
*/
 STATE  (GedTag.STAE), /**

An assessment of the state or condition of something.
*/
 STATUS  (GedTag.STAT), /**

An individual or organization who contributes genealogical data to a file or transfers it to someone else.
*/
 SUBMITTER  (GedTag.SUBM), /**

Pertains to a collection of data issued for processing.
*/
 SUBMISSION  (GedTag.SUBN), /**

A family name passed on or used by members of a family.
*/
 SURNAME  (GedTag.SURN), /**

The name or code that represents the name a temple of the LDS Church.
*/
 TEMPLE  (GedTag.TEMP), /**

The exact wording found in an original source document.
*/
 TEXT  (GedTag.TEXT), /**

A time value in a 24-hour clock format, including hours, minutes, and optional seconds, separated by a colon (:). Fractions of seconds are shown in decimal notation.
*/
 TIME  (GedTag.TIME), /**

A description of a specific writing or other work, such as the title of a book when used in a source context, or a formal designation used by an individual in connection with positions of royalty or other social status, such as Grand Duke.
*/
 TITLE  (GedTag.TITL), /**

At level 0, specifies the end of a GEDCOM transmission.
*/
 TRAILER  (GedTag.TRLR), /**

A further qualification to the meaning of the associated superior tag. The value does not have any computer processing reliability. It is more in the form of a short one or two word note that should be displayed any time the associated data is displayed.
*/
 TYPE  (GedTag.TYPE), /**

Indicates which version of a product, item, or publication is being used or referenced.
*/
 VERSION  (GedTag.VERS), /**

An individual in the role as a mother and/or married woman.
*/
 WIFE  (GedTag.WIFE), /**

A legal document treated as an event, by which a person disposes of his or her estate, to take effect after death. The event date is the date the will was signed while the person was alive. (See also PROBate.)
*/
 WILL  (GedTag.WILL), ;

    GedEvent(GedTag tag) {
        
    }
}
