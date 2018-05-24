package com.example.me.annabella;

import java.util.ArrayList;
import java.util.HashSet;

public class Data {
    private ArrayList<String[]> allVerbs;
    private HashSet<Long> studyList;

    public ArrayList<String[]> getAllVerbs() { return allVerbs; }
    public HashSet<Long> getStudyList() { return studyList; }
    public void setAllVerbs(ArrayList<String[]> allVerbs) { this.allVerbs = allVerbs; }
    public void setStudyList(HashSet<Long> studyList) { this.studyList = studyList; }

    private static final Data data = new Data();
    public static Data getInstance() { return data; }
}
