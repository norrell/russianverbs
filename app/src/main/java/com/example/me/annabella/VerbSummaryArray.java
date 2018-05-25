package com.example.me.annabella;

import java.util.ArrayList;

public class VerbSummaryArray extends ArrayList<String[]> {
    public String[] getByID(int id) {
        return super.get(id - 1);
    }

    public String[] getByIndex(int index) {
        return super.get(index);
    }
}
