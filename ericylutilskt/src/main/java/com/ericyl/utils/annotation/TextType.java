package com.ericyl.utils.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({TextType.MARKDOWN, TextType.HTML, TextType.TEXT})
@Retention(RetentionPolicy.SOURCE)
public @interface TextType {
    int MARKDOWN = 0;
    int HTML = 1;
    int TEXT = 2;
}