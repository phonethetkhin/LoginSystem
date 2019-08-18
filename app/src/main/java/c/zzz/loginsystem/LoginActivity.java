package c.zzz.loginsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import c.zzz.loginsystem.Database.UserDatabase;
import c.zzz.loginsystem.Models.UserModel;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText tietUserName,tietPassword;
    Button btnLogin,btnRegister;
    UserDatabase udb=new UserDatabase(LoginActivity.this);
    List<UserModel> userModelList;
    SharedPreferences pref;
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //typecast
        tietUserName = findViewById(R.id.tietUserName);
        tietPassword = findViewById(R.id.tietPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btn_Register);

        //action bar
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //get preferences
        pref = getSharedPreferences("MY_PREF", MODE_PRIVATE);
        isLogin = pref.getBoolean("Login", false);

        //check preferences
        //if isLogin is true, redirect with Intent to upload whenever start onCreate
        if (isLogin) {
            Intent i = new Intent(LoginActivity.this, UploadStatus.class);
            startActivity(i);
        }

        //register button
        else {
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(LoginActivity.this, Register.class);
                    startActivity(i);
                }
            });

            //login button
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userModelList = udb.GetUser(tietUserName.getText().toString(), tietPassword.getText().toString());
                    //user validation(blank)
                    if(tietUserName.getText().toString().isEmpty())
                    {
                        tietUserName.setError("Please Enter UserName");
                    }
                    if(tietPassword.getText().toString().isEmpty())
                    {
                        tietPassword.setError("Please Enter Password");
                    }
                    //user validation(validate existing data)
                    if (userModelList.size() <= 0) {
                        Toast.makeText(LoginActivity.this, "There is No User with this Name and Password,Try Again", Toast.LENGTH_LONG).show();
                    }
                    //adding data
                    else {
                         String name=tietUserName.getText().toString();
                         //adding preferences value as true if login is successful
                        pref = getSharedPreferences("MY_PREF", MODE_PRIVATE);
                        SharedPreferences.Editor myeditor = pref.edit();
                        myeditor.putBoolean("Login", true);
                        myeditor.apply();

                        Toast.makeText(LoginActivity.this, "Welcome "+name+" !!!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this, UploadStatus.class);
                        startActivity(i);
                    }
                }
            });

        }
    }

}
