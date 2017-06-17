package com.emprendesoft.madridshops.views;

import android.support.annotation.NonNull;

public interface OnElementClick<T> {

    void clickedOn(@NonNull T element, int position);
}
