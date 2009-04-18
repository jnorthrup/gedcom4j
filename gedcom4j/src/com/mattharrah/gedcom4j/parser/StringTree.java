package com.mattharrah.gedcom4j.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Inner class for the heirarchical structure
 * 
 * @author Matt
 */
class StringTree {
	int level;
	String id;
	String tag;
	String value;
	List<StringTree> children = new ArrayList<StringTree>();
	StringTree parent = null;
	public int lineNum;

	@Override
	public String toString() {
		return "Line " + lineNum + ": " + level + " " + tag + " " + value;
	}
}