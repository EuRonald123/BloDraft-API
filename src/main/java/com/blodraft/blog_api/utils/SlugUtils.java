package com.blodraft.blog_api.utils;

import java.text.Normalizer;

public class SlugUtils {
    public static String generate(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFKD)
                .replaceAll("[^a-zA-Z0-9\\s]", "")
                .toLowerCase()
                .replaceAll("\\s+", "-")
                .replaceAll("^-+|-+$", "");
    }
}
