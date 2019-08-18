package c.zzz.loginsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import c.zzz.loginsystem.Adapter.StatusAdapter;
import c.zzz.loginsystem.Database.UserDatabase;
import c.zzz.loginsystem.Models.StatusModel;

public class UploadStatus extends AppCompatActivity {
    TextInputEditText tietStatus;
    Button btnUpload;
    RecyclerView rvShowStatus;
    UserDatabase udb=new UserDatabase(UploadStatus.this);
    List<StatusModel> statusModelList;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_status);
        //typecast
        tietStatus=findViewById(R.id.tietUploadStatus);
        btnUpload=findViewById(R.id.btnUpload);
        rvShowStatus=findViewById(R.id.rvShowStatus);
        //actionbar
        getSupportActionBar().setTitle("Upload Status");
        //show data
       onResume();



//upload button
btnUpload.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        //user validation(blank)
        if (tietStatus.getText().toString().isEmpty()) {
            Toast.makeText(UploadStatus.this, "Fill Status", Toast.LENGTH_SHORT).show();
            tietStatus.setError("Fill Status Here");
        } else {
            if (udb.InsertStatus(tietStatus.getText().toString())) {
                Toast.makeText(UploadStatus.this, "Upload Successfully", Toast.LENGTH_LONG).show();
                //show data
                    onResume();

            } else {
                Toast.makeText(UploadStatus.this, "Upload Fail, Try again", Toast.LENGTH_LONG).show();
            }
        }
    }
});

    }

    @Override
    protected void onResume() {
        super.onResume();
        statusModelList = udb.GetStatus();
        rvShowStatus.setLayoutManager(new GridLayoutManager(UploadStatus.this, 2, RecyclerView.VERTICAL, false));
        rvShowStatus.setHasFixedSize(true);

        rvShowStatus.setAdapter(new StatusAdapter(statusModelList));

    }

    //connect menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
//connect menu item and check condition
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    {
        switch (item.getItemId())
        {
            //calling dialog
            case R.id.Logout:
                AlertDialog.Builder alertdialog=new AlertDialog.Builder(UploadStatus.this);
                alertdialog.setMessage("Are You Sure You Want to Log Out ?");
                alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //removing preferences if user choose Yes
                        pref=getSharedPreferences("MY_PREF",MODE_PRIVATE);
                            SharedPreferences.Editor editor=pref.edit();
                            editor.clear();
                            editor.putBoolean("Login",false);
                            editor.apply();

                            //redirect back to Login
                        Intent intent=new Intent(UploadStatus.this,LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(UploadStatus.this, "Logged Out", Toast.LENGTH_SHORT).show();
                    }
                });
                alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog=alertdialog.create();
                dialog.show();
                break;
        }
return super.onOptionsItemSelected(item);
    }

}}
