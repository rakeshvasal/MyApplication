package com.example.rakeshvasal.myapplication.Interface;

import com.example.rakeshvasal.myapplication.UIError;

public interface CentralCallbacks<T>    {
    void onSuccess(T response);

    void onFailure(UIError error);
}
