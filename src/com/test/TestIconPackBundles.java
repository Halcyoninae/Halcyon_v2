package com.test;

import com.halcyoninae.cosmos.icon.IconPackBundles;
import com.halcyoninae.halcyon.debug.Debugger;

public class TestIconPackBundles {
    public static void main(String ... args) {
        for(long i : new long[]{1,2,3,4,5,6,7,8,9}) {
            Debugger.info(IconPackBundles.isReservedID(i));
        }

    }
}
