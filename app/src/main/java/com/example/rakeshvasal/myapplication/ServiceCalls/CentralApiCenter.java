package com.example.rakeshvasal.myapplication.ServiceCalls;

import com.example.rakeshvasal.myapplication.UIError;

public class CentralApiCenter {

    private static CentralApiCenter instance;

    public void setListener(OnCentralApiCenterResponse listener) {
        this.listener = listener;
    }

    private OnCentralApiCenterResponse listener;

    private CentralApiCenter() {

    }

    public static CentralApiCenter getInstance() {
        if (instance != null) {
            return instance;
        } else {
            return instance = new CentralApiCenter();
        }
    }

    public interface OnCentralApiCenterResponse<T> {
        void onSuccess(T response);

        void onFailure(UIError error);
    }

}
