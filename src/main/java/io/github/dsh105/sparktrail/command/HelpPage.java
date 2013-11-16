package io.github.dsh105.sparktrail.command;

import java.util.ArrayList;

/**
 * Project by DSH105
 */

public class HelpPage {

    public static int getIndex() {
        return (int) (Math.ceil(HelpEntry.values().length / 5));
    }

    public static double getDoubleIndex() {
        return (int) (Math.ceil(HelpEntry.values().length / 5));
    }

    public static String[] getPage(int pageNumber) {
        int perPage = 5;
        HelpEntry[] raw = HelpEntry.values();
        int index = perPage * (Math.abs(pageNumber) - 1);
        ArrayList<String> list = new ArrayList<String>();
        if (pageNumber <= getDoubleIndex()) {
            for (int i = index; i < (index + perPage); i++) {
                if (raw.length > i) {
                    list.add(raw[i].getLine());
                }
            }
        } else {
            return null;
        }
        return list.toArray(new String[list.size()]);
    }
}