package com.event.inject.adapter;


public interface InjectAdapter<T> {
    void injects(T event);
}
