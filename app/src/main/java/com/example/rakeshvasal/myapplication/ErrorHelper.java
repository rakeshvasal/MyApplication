package com.example.rakeshvasal.myapplication;

import retrofit2.Response;

public class ErrorHelper {

    /**
     * for: parsing the error from the errorBody from the response of the retrofit
     * author: Rajas Ashtikar
     * Since: 30/09/2016
     *
     * @param response: Generic response object of the Retrofit.
     * @return: returns the error object defined by th Cardinbox
     * @see Error
    public static Error parseError(Response<?> response) {
        //using the methods provided by the retrofit class to convert the errorbody into desired class, here "Error"
        if (response != null) {
            Converter<ResponseBody, Error> converter = Retrofitconfig.getRetrofit().responseBodyConverter(Error.class, new Annotation[0]);
            Error error;
            try {
                error = converter.convert(response.errorBody());
            } catch (Exception e) {
                return new Error();
            }
            return error;
        }
        return null;
    }

    *//**
     * for: parsing the special error body which is specific to the Login Module
     * difference: the internal error body is the same however since the error coming from the
     * Login server is different it has to be specially parsed.
     * author: Rajas Ashtikar
     * Since: 30/09/2016
     *
     * @param response: Generic response object of the Retrofit.
     * @return: returns the error object defined by th Cardinbox
     * @see Error
     *//*
    public static Error parseLoginError(Response<?> response) {
        //using the methods provided by the retrofit class to convert the errorbody into desired class, here "Error"
        if (response != null) {
            Converter<ResponseBody, LoginServerError> converter = Retrofitconfig.getRetrofit().responseBodyConverter(LoginServerError.class, new Annotation[0]);
            LoginServerError loginError;
            Error error = new Error();
            try {
                loginError = converter.convert(response.errorBody());
                if (loginError != null) {
                    error.httpCode = response.code();
                    if (loginError.error != null && loginError.error.trim().length() > 0) {
                        error.errorDetails = loginError.error;
                    }
                    if (loginError.error_description != null && loginError.error_description.trim().length() > 0) {
                        error.errorDescription = loginError.error_description;
                    }
                    error.type = Error.LOGIN_ERROR;
                }
            } catch (Exception e) {
                return new Error();
            }
            return error;
        }
        return null;
    }

    *//**
     * for: parsing the error in the form that can be managed on the Controller side
     * author: Rajas Ashtikar
     * Since: 30/09/2016
     *
     * @param response: Generic response object of the Retrofit.
     * @param httpCode: Http code given returned by the server as a part of the response
     * @return: returns UIError object, which can be managed by the Controllers
     * @see UIError
     *//*
    public static UIError getUIError(Response response, int httpCode) {
        Error error = parseError(response);
        if (error != null) {
            return ErrorInterpreter.interpreteError(error.httpCode, error);
        } else {
            return ErrorInterpreter.interpreteError(httpCode, null);
        }
    }

    *//**
     * for: parsing the error in the form that can be managed on the Controller side
     * author: Rajas Ashtikar
     * Since: 30/09/2016
     *
     * @param response: Generic response object of the Retrofit.
     * @param httpCode: Http code given returned by the server as a part of the response
     * @param from:     from Normal or Login module. Since the errors from Login have to managed differently
     * @return: returns UIError object, which can be managed by the Controllers
     * @see Constants (for exploring the @param from)
     * @see UIError
     *//*
    public static UIError getUIError(Response response, int httpCode, int from) {
        Error error;
        if (from == Constants.LOGIN) {
            error = parseLoginError(response);
        } else {
            error = parseError(response);
        }
        return UI
        if (error != null) {
            return ErrorInterpreter.interpreteError(error.httpCode, error);
        } else {
            return ErrorInterpreter.interpreteError(httpCode, null);
        }
    }

    *//**
     * for: parsing the error in the form that can be managed on the Controller side, from a simple String
     * author: Rajas Ashtikar
     * Since: 30/09/2016
     *
     * @param s: DataType <code>String</code> error string
     * @return: returns UIError object, which can be managed by the Controllers
     * @see UIError
     *//*
    public static UIError getUIError(String s) {
        if (s != null && s.trim().length() > 0) {
            return ErrorInterpreter.interpreteError(s);
        }
        return new UIError();
    }

    *//**
     * for: returning the default object of the UIError object
     * author: Rajas Ashtikar
     * Since: 30/09/2016
     *
     * @return default UIError object
     *//*
    public static UIError getUIError() {
        return new UIError();
    }

    public static UIError getUIError(Throwable t) {
        return ErrorInterpreter.interpreteError(t);
    }*/
}
