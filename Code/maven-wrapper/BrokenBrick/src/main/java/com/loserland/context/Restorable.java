package com.loserland.context;

public interface Restorable<T> {
    T restore();
    int getX();
    int getY();
}
