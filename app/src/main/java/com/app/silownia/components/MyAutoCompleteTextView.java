package com.app.silownia.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class MyAutoCompleteTextView extends AutoCompleteTextView {

    private int myThreshold;

    public MyAutoCompleteTextView(Context context) {
        super(context);
    }

    public MyAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setThreshold(int threshold) {
        if (threshold < 0) {
            threshold = 0;
        }
        myThreshold = threshold;
    }

    @Override
    public boolean enoughToFilter() {
        return getText().length() >= myThreshold;
    }

    @Override
    public int getThreshold() {
        return myThreshold;
    }
}