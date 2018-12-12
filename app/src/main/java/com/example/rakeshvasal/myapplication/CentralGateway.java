package com.example.rakeshvasal.myapplication;

public class CentralGateway {

    private static CentralGateway gatewayinstance;

    private CentralGatewayResponse gatewayResponseinstance;

    public void setListener(CentralGatewayResponse response) {
        this.gatewayResponseinstance = response;
    }

    private CentralGateway() {
    }

    public static CentralGateway getGatewayinstance() {
        if (gatewayinstance != null) {
            return gatewayinstance;
        } else {
            return gatewayinstance = new CentralGateway();
        }
    }

    public interface CentralGatewayResponse<T> {
        void onSuccess(T response);

        void onFailure();
    }

}
