package c.zzz.loginsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import c.zzz.loginsystem.Database.UserDatabase;
import c.zzz.loginsystem.Models.StatusModel;

public class UpdateStatus extends AppCompatActivity {
TextInputEditText tietUpdateStatus;
Button btnUpdate;
UserDatabase udb=new UserDatabase(UpdateStatus.this);
StatusModel smodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);
        //typecast
        tietUpdateStatus=findViewById(R.id.tietUpdateStatus);
        btnUpdate=findViewById(R.id.btnUpdate);

        //getting old values
         smodel=getIntent().getParcelableExtra("OldValue");
        tietUpdateStatus.setText(smodel.getStatus());

        //update button
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                udb.UpdateStatus(smodel.getStatusID(),tietUpdateStatus.getText().toString());
                Toast.makeText(UpdateStatus.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
