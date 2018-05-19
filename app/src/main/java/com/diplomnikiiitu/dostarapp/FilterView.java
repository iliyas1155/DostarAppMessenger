package com.diplomnikiiitu.dostarapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class FilterView extends View {

    public FilterView(Context context) {
        super(context);
        init();
    }

    public FilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public FilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
//        inflate(getContext(), R.layout.)
    }
}
