package io.github.dsh105.sparktrail.command;

import java.util.ArrayList;

/**
 * Project by DSH105
 */

public class HelpPage {

    public static String[] getPage(int pageNumber) {
        int perPage = 5;
        HelpEntry[] raw = HelpEntry.values();
        int index = perPage * (Math.abs(pageNumber) - 1);
        ArrayList<String> list = new ArrayList<String>();
        if (pageNumber <= Math.ceil(raw.length / perPage)) {
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