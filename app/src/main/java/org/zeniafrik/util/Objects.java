package org.zeniafrik.util;

/**
 * Created by BraDev ${LOCALE} on 1/12/2018.
 */

public class Objects {
    public static boolean equals(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        if (o2 == null) {
            return false;
        }
        return o1.equals(o2);
    }
}