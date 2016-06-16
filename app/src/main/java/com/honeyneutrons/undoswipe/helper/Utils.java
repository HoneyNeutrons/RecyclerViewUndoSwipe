package com.honeyneutrons.undoswipe.helper;

import android.content.res.Resources;

class Utils {
   // private static int screenWidth = 0;
   // private static int screenHeight = 0;


    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

   /* public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
               screenHeight = size.y;
            }
            else
            {
               screenHeight = size.y;
            }
        }
        return screenHeight;
    }*/

   /* public static int getStatusBarHeight(Context c) {
        int statusBarHeight = 0;
        int resourceId = c.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = c.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }*/

  /*  public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }*/
}

