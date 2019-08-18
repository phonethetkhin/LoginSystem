package c.zzz.loginsystem.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import c.zzz.loginsystem.Database.UserDatabase;
import c.zzz.loginsystem.Models.StatusModel;
import c.zzz.loginsystem.R;
import c.zzz.loginsystem.UpdateStatus;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    List<StatusModel> statusModelList;

    public StatusAdapter(List<StatusModel> statusModelList) {
        this.statusModelList = statusModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.statuslistitem,parent,false);
        ViewHolder holder=new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, final int position) {
//h.tvUserName.setText(statusModelList.get(position).get);
        h.tvStatus.setText(statusModelList.get(position).getStatus());
        h.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(), UpdateStatus.class);
                Bundle b=new Bundle();
                b.putParcelable("OldValue",statusModelList.get(position));
                i.putExtras(b);
                view.getContext().startActivity(i);
            }
        });
        h.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(view.getContext());
                alertdialogbuilder.setMessage("Are You Sure You Want to Delete This?");
                alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        UserDatabase udb=new UserDatabase(view.getContext());

                        udb.RemoveStatus(statusModelList.get(position).getStatusID());
                        statusModelList.remove(position);
                        Toast.makeText(view.getContext(), "", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }
                });
                alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog=alertdialogbuilder.create();
                alertDialog.show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return statusModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvUserName,tvStatus;
        Button btnUpdate,btnRemove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName=itemView.findViewById(R.id.tvUserName);
            tvStatus=itemView.findViewById(R.id.tvStatus);
            btnUpdate=itemView.findViewById(R.id.btnUpdate);
            btnRemove=itemView.findViewById(R.id.btnRemove);
        }
    }
}
