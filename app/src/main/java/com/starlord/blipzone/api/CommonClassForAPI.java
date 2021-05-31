package com.starlord.blipzone.api;


import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.starlord.blipzone.callbacks.ApiResponseCallback;
import com.starlord.blipzone.callbacks.ApiResultCallback;
import com.starlord.blipzone.configurations.GlobalVariables;
import com.starlord.blipzone.configurations.UrlConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CommonClassForAPI {
    private static final String TAG = "CommonClassForAPI";

    public static void callGetRequest(Activity context, String url, ApiResultCallback apiResultCallback) {
        Log.d(TAG, "callAuthAPI: url  " + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    Log.d(TAG, "onResponse: callAuthAPI JSONObject " + response);
                    apiResultCallback.onAPIResultSuccess(response);
                }, error -> {
                    Log.d(TAG, "onErrorResponse: callAuthAPI JSONObject " + error);
                    apiResultCallback.onAPIResultError(error);

                });

        // Access the RequestQueue through your singleton class.
        VolleyClient.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }

    public static void callRegisterRequest(Activity context, String username, String email,
                                           String password, ApiResponseCallback apiResponseCallback) {

        StringRequest callRequest = new StringRequest
                (Request.Method.POST, UrlConstants.REGISTER_USER, response -> {
                    try {
                        Log.d(TAG, "onResponse: RegisterAPI " + response);
                        apiResponseCallback.onApiSuccessResult(new JSONObject(response));
                    } catch (JSONException e) {
                        apiResponseCallback.onApiFailureResult(e);
                    }
                },
                        (VolleyError error) -> {
                            Log.d(TAG, "onErrorResponse: RegisterAPI " + error);
                            if (error != null) {
                                NetworkResponse networkResponse = error.networkResponse;
                                Log.d(TAG, "onErrorResponse: RegisterAPI " + networkResponse);
                                apiResponseCallback.onApiErrorResult(error);
                            }
                        }) {

            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);

                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        // Access the RequestQueue through your singleton class.
        VolleyClient.getInstance(context).addToRequestQueue(callRequest);

    }

    public static void callVerificationRequest(Activity context, String email, String otp,
                                               ApiResponseCallback apiResponseCallback) {

        StringRequest callRequest = new StringRequest
                (Request.Method.POST, UrlConstants.VERIFY_USER, response -> {
                    try {
                        Log.d(TAG, "onResponse: VerificationAPI " + response);
                        apiResponseCallback.onApiSuccessResult(new JSONObject(response));
                    } catch (JSONException e) {
                        apiResponseCallback.onApiFailureResult(e);
                    }
                },
                        (VolleyError error) -> {
                            Log.d(TAG, "onErrorResponse: VerificationAPI " + error);
                            if (error != null) {
                                NetworkResponse networkResponse = error.networkResponse;
                                Log.d(TAG, "onErrorResponse: VerificationAPI " + networkResponse);
                                apiResponseCallback.onApiErrorResult(error);
                            }
                        }) {

            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("otp", otp);

                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        // Access the RequestQueue through your singleton class.
        VolleyClient.getInstance(context).addToRequestQueue(callRequest);

    }

    public static void callLoginRequest(Activity context, String loginData, String password,
                                               ApiResponseCallback apiResponseCallback) {

        StringRequest callRequest = new StringRequest
                (Request.Method.POST, UrlConstants.LOGIN_USER, response -> {
                    try {
                        Log.d(TAG, "onResponse: LoginAPI " + response);
                        apiResponseCallback.onApiSuccessResult(new JSONObject(response));
                    } catch (JSONException e) {
                        apiResponseCallback.onApiFailureResult(e);
                    }
                },
                        (VolleyError error) -> {
                            Log.d(TAG, "onErrorResponse: LoginAPI " + error);
                            if (error != null) {
                                NetworkResponse networkResponse = error.networkResponse;
                                Log.d(TAG, "onErrorResponse: LoginAPI " + networkResponse);
                                apiResponseCallback.onApiErrorResult(error);
                            }
                        }) {

            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("login_data", loginData);
                params.put("password", password);

                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        // Access the RequestQueue through your singleton class.
        VolleyClient.getInstance(context).addToRequestQueue(callRequest);

    }
}
