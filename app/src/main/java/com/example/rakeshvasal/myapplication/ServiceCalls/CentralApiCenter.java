package com.example.rakeshvasal.myapplication.ServiceCalls;

import com.example.rakeshvasal.myapplication.Interface.CentralCallbacks;
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

    public void getAllUsers(final CentralCallbacks centralCallbacks) {
        FirebaseCalls firebaseCalls = new FirebaseCalls();
        firebaseCalls.getAllUsers(new CentralCallbacks() {
            @Override
            public void onSuccess(Object response) {
                centralCallbacks.onSuccess(response);
            }

            @Override
            public void onFailure(UIError error) {
                centralCallbacks.onFailure(error);
            }
        });
    }

}
