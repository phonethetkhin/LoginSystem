package c.zzz.loginsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.UserManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import c.zzz.loginsystem.Database.UserDatabase;
import c.zzz.loginsystem.Models.UserModel;

public class Register extends AppCompatActivity {
    TextInputEditText tietUserName,tietPassword,tietCPassword;
    Button btnRegister;
    UserDatabase udb=new UserDatabase(Register.this);
    List<UserModel> userModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //typecast
        tietUserName=findViewById(R.id.tietUserName);
        tietPassword=findViewById(R.id.tietPassword);
        tietCPassword=findViewById(R.id.tietCPassword);
        btnRegister=findViewById(R.id.btn_Register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //user validation(blank)
                if (tietUserName.getText().toString().isEmpty() || tietPassword.getText().toString().isEmpty() || tietCPassword.getText().toString().isEmpty()) {
                    Toast.makeText(Register.this, "Please Fill All Information", Toast.LENGTH_SHORT).show();
                    if (tietUserName.getText().toString().isEmpty())
                        tietUserName.setError("Fill UserName");
                    if (tietPassword.getText().toString().isEmpty())
                        tietPassword.setError("Fill Password");
                    if (tietCPassword.getText().toString().isEmpty())
                        tietCPassword.setError("Confirm Your Password");




                    //user validation(password=confirm)
                    String pass = tietPassword.getText().toString();
                    String confirmpass = tietCPassword.getText().toString();
                    if (!pass.equals(confirmpass)) {
                        Toast.makeText(Register.this, "Your Passwords Do Not Match !!!!", Toast.LENGTH_SHORT).show();
                        tietCPassword.setError("Type the Same Password from Above !!!!");
                    }
                }

                else {
                    //user validation(data uniqueness)
                    userModelList = udb.GetUser(tietUserName.getText().toString());
                    if (userModelList.size() > 0) {
                        Toast.makeText(Register.this, "Sorry, There is a user  with this username already", Toast.LENGTH_LONG).show();
                    }
                    //insert data
                    else if (udb.InsertUser(tietUserName.getText().toString(), tietPassword.getText().toString())) {
                        Toast.makeText(Register.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Register.this, "Register Fail", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
