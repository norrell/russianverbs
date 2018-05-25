package com.example.me.annabella;

import java.util.ArrayList;
import java.util.HashSet;

public class Data {
    private VerbSummaryArray allVerbs;
    private HashSet<Long> studyList;

    public VerbSummaryArray getAllVerbs() { return allVerbs; }
    public HashSet<Long> getStudyList() { return studyList; }
    public void setAllVerbs(VerbSummaryArray allVerbs) { this.allVerbs = allVerbs; }
    public void setStudyList(HashSet<Long> studyList) { this.studyList = studyList; }

    private static final Data data = new Data();
    public static Data getInstance() { return data; }
}
