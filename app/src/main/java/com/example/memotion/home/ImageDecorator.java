package com.example.memotion.home;

import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;


import java.util.HashSet;
import java.util.Set;

public class ImageDecorator implements DayViewDecorator {
    private final Set<CalendarDay> datesWithImages;
    private final Drawable imageDrawable;

    public ImageDecorator(Drawable imageDrawable, Set<CalendarDay> datesWithImages) {
        this.imageDrawable = imageDrawable;
        this.datesWithImages = new HashSet<>(datesWithImages);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return datesWithImages.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(imageDrawable);
    }
}