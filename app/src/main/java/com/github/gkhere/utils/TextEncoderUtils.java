package com.github.gkhere.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Meiji on 2016/8/10.
 */

public class TextEncoderUtils {
    public static String encoding(String text) {
        try {
            text = URLEncoder.encode(text, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }
}
