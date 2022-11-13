package com.example.nextstep;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nextstep.common.Constant;
import com.example.nextstep.model.Customer;
import com.example.nextstep.network.RetrofitCongClass;
import com.google.gson.Gson;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class createTeacherProfile extends AppCompatActivity {
    EditText teachername, teacherquali, teachergender, teacherspecialization;
    Button createprofilebtn;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_t_profile);
        initViews();
        iniVariables();
        setUpClicks();

    }

    private void initViews() {
        teachername = findViewById(R.id.teachername);
        teacherquali = findViewById(R.id.teacherquali);
        teachergender = findViewById(R.id.teachergender);
        teacherspecialization = findViewById(R.id.teacherspecailization);
        createprofilebtn = findViewById(R.id.createprofilebtn);
    }

    private void iniVariables() {
    }

    private void setUpClicks() {
        createTeacherProfile();
    }

    void createTeacherProfile() {

        customer = new Customer();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Registration");
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        RetrofitCongClass.getClient().createTeacherProfile(teachername.getText().toString(),
                        teac.getText().toString(), teacherquali.getText().toString(),
                        teachergender.getText().toString(),
                        tea.getText().toString())
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                                Boolean error = jsonObject.getBoolean("error");
                                String message = jsonObject.getString("message");
                                if (error) {
                                    Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();
                                } else {
                                    if (type == Constant.TEACHER) {
                                        startActivity(new Intent(SignupActivity.this, createTeacherProfile.class));
                                    } else if (type == Constant.STUDENT) {
                                        startActivity(new Intent(SignupActivity.this, edit_std_profile.class));
                                    }
                                    Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                                Log.d("RegResponse", "onResponse: " + jsonObject);

                            } catch (Exception exception) {
                                Toast.makeText(SignupActivity.this, exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.d("RegResponse", "onFailure: " + t.getLocalizedMessage());
                        //t.printStackTrace();
                        // Toast.makeText(SignupActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}