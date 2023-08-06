package com.example.memotion.home;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.example.memotion.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

//날짜 누르면 사진 띄움
public class SelectorDecorator implements DayViewDecorator {
    private final Drawable drawable;

    public SelectorDecorator(Activity context) {
        drawable = context.getResources().getDrawable(R.drawable.calendar_selector);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
}