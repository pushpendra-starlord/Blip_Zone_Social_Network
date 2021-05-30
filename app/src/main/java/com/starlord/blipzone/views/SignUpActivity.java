package com.starlord.blipzone.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.starlord.blipzone.R;
import com.starlord.blipzone.callbacks.ApiResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

import static com.starlord.blipzone.api.CommonClassForAPI.callRegisterRequest;

public class SignUpActivity extends AppCompatActivity {
    EditText username, email, password;
    Button signUp;
    private ProgressDialog progressDialog;
    String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email_sign_up);
        password = findViewById(R.id.password_sign_up);
        signUp = findViewById(R.id.sign_up_btn);
        progressDialog = new ProgressDialog(this);

        signUp.setOnClickListener(v -> {
            String userNameValue = username.getText().toString().trim();
            String emailValue = email.getText().toString().trim();
            String passwordValue = password.getText().toString().trim();

            if (TextUtils.isEmpty(userNameValue)) {
                username.setError("Required field");
                return;
            }

            if (TextUtils.isEmpty(emailValue)) {
                email.setError("Required field");
                return;
            }

            if (TextUtils.isEmpty(passwordValue)) {
                password.setError("Required field");
                return;
            }

            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            //calling registration api.
            callRegisterRequest(SignUpActivity.this,
                    userNameValue,
                    emailValue,
                    passwordValue,
                    new ApiResultCallback() {
                        @Override
                        public void onAPIResultSuccess(JSONObject jsonObject) {
                            Log.d(TAG, "onResponse: Success");
                            progressDialog.dismiss();
                            processLoginResponse(jsonObject);
                        }

                        @Override
                        public void onAPIResultError(VolleyError volleyError) {
                            Log.d(TAG, "onAPIResultError: " + volleyError.toString());
                            progressDialog.dismiss();
                        }
                    });

        });

    }

    private void processLoginResponse(JSONObject jsonObject) {
        try {
            boolean status = jsonObject.getBoolean("status");
            String details = jsonObject.getString("details");

            if (status) {
                Intent intent = new Intent(SignUpActivity.this, VerificationActivity.class);
                intent.putExtra("email", email.getText().toString());
                startActivity(intent);
            } else {
                Toast.makeText(SignUpActivity.this, details, Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}