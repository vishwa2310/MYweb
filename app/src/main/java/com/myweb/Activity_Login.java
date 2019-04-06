package com.myweb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Login extends AppCompatActivity {
    private Button btn_login;
    private Context context;
    private EditText edittext_usernamelogin, editText_passwordlogin;
    private TextInputLayout textinput_password, textinputlayout_username;
    private ApiInterface apiInterface;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        context = this;
        container();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {

                    try {
                        pDialog = new ProgressDialog(Activity_Login.this);
                        pDialog.setMessage("Please wait...");
                        pDialog.setProgressStyle(R.style.AppCompatAlertDialogStyle);
                        pDialog.setCancelable(false);
                        pDialog.show();
                    } catch (Exception e) {
                    }
                    String username = edittext_usernamelogin.getText().toString().trim();
                    String password = editText_passwordlogin.getText().toString().trim();

                    Call<Login> call = apiInterface.getUserlogin(username, password);

                    call.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            System.out.println(call.request());
                            //String str = response.body();
                            //JSONObject object = (JSONObject) response.body();
                            System.out.println("json" + new Gson().toJson(response.body()));
                            System.out.println(response.code() + "**responce ==" + response.raw());
                            String movies = response.body().getStatus();

                            if (movies.equals("sucessfully")) {
                                String status = response.body().getStatus();
                                String url = response.body().getUrl();
                                Intent i = new Intent(Activity_Login.this, Test_Homepage.class);
                                i.putExtra("status", status);
                                i.putExtra("url", url);
                                startActivity(i);

                            } else {
                                Toast.makeText(context, "Please check Email and Password", Toast.LENGTH_SHORT).show();
                            }

                            pDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {

                            Toast.makeText(context, "Please check Email and Password", Toast.LENGTH_SHORT).show();
                        }
                    });



                 /*   Intent i = new Intent(context, Test_Homepage.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);*/

                }
            }
        });


    }

    private void container() {

        edittext_usernamelogin = (EditText) findViewById(R.id.edittext_usernamelogin);
        editText_passwordlogin = (EditText) findViewById(R.id.editText_passwordlogin);
        textinput_password = (TextInputLayout) findViewById(R.id.textinput_password);
        textinputlayout_username = (TextInputLayout) findViewById(R.id.textinputlayout_username);

    }

    private boolean validate() {
        String username = "", password = "";
        username = edittext_usernamelogin.getText().toString().trim();
        password = editText_passwordlogin.getText().toString().trim();


        if (username.equals("")) {
            textinputlayout_username.setError("please Enter Email");
        } else {
            textinputlayout_username.setError(null);
        }

        if (password.equals("")) {
            textinputlayout_username.setError("please Enter Email");
        } else {
            textinputlayout_username.setError(null);
        }
        return true;
    }


}