package studio.brunocasamassa.myapplication.utils;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by bruno on 28/09/2017
 *
 * .
 */

public class Tools {

    public static void refresh(Activity activity) {
        Intent intent = activity.getIntent();
        activity.finish();
        activity.startActivity(intent);
    }


    public static void refreshAll(Activity activity) {
        Intent intent = activity.getIntent();
        activity.finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }


    public static void getMemoryStatus() {
        final Runtime runtime = Runtime.getRuntime();
        final long usedMemInMB = (runtime.totalMemory() - runtime.freeMemory()) / 1048576L;  //1024bits * 1024bits == 1MB (1048576)
        final long maxHeapSizeInMB = runtime.maxMemory() / 1048576L;
        final long availHeapSizeInMB = maxHeapSizeInMB - usedMemInMB;

        System.out.println("FREE MEMORY " + availHeapSizeInMB + " - USED MEMORY: " + usedMemInMB + " - MAX MEMORY " + maxHeapSizeInMB);


    }

}
