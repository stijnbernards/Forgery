package com.stijnhero.forgery.client.renderer;

import java.util.List;

public class ColourThreshold implements Comparable<ColourThreshold>
{
    public int threshold;
    public String colorCode;

    public ColourThreshold(int t, String c)
    {
        threshold = t;
        colorCode = c;
    }

    @Override
    public String toString()
    {
        return String.valueOf(threshold) + ", " + colorCode;
    }

    @Override
    public int compareTo(ColourThreshold o)
    {
        if (this.threshold > o.threshold)
            return 1;
        else if (this.threshold < o.threshold)
            return -1;
        else
            return 0;
    }

    public static String getColorCode(List<ColourThreshold> colorList, int value)
    {
        for (ColourThreshold ct : colorList)
            if (value <= ct.threshold)
                return ct.colorCode;

        return "f";
    }
}