package c.zzz.loginsystem.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.StampedLock;

import c.zzz.loginsystem.Models.StatusModel;
import c.zzz.loginsystem.Models.UserModel;

public class UserDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME= "Login";
    public static final int DB_VERSION=1;
    public final String USER_TABLE="User";
    public final String STATUS_TABLE="Status";

    public UserDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+USER_TABLE+" (User_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,User_Name TEXT,Password TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE "+STATUS_TABLE+"(Status_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,User_ID INTEGER,Status TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean InsertUser(String UserName,String Password)
    {
        SQLiteDatabase mydb=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("User_Name",UserName);
        cv.put("Password",Password);
        try {
            mydb.insert(USER_TABLE, null, cv);
            mydb.close();
            return true;
        }
        catch (Exception e)
        {
            mydb.close();
            return false;
        }
    }
    public List<UserModel> GetUser(String UserName, String Password)
    {
        List<UserModel> userModelList=new ArrayList<>();
        SQLiteDatabase mydb=this.getReadableDatabase();
        Cursor cs=mydb.rawQuery("SELECT * FROM "+USER_TABLE+" WHERE User_Name='"+UserName+"' AND Password='"+Password+"';",null);
        if(cs.moveToFirst())
        {
            while(!cs.isAfterLast()) {
                UserModel umodel = new UserModel(cs.getInt(0), cs.getString(cs.getColumnIndex("User_Name")),
                        cs.getString(cs.getColumnIndex("Password")));
                userModelList.add(umodel);
                cs.moveToNext();
            }

        }
        cs.close();
        return userModelList;
    }
    public List<UserModel> GetUser(String UserName)
    {
        List<UserModel> userModelList=new ArrayList<>();
        SQLiteDatabase mydb=this.getReadableDatabase();
        Cursor cs=mydb.rawQuery("SELECT * FROM "+USER_TABLE+" WHERE User_Name='"+UserName,null);
        if(cs.moveToFirst())
        {
            while(!cs.isAfterLast()) {
                UserModel umodel = new UserModel(cs.getInt(0), cs.getString(cs.getColumnIndex("User_Name")),
                        cs.getString(cs.getColumnIndex("Password")));
                userModelList.add(umodel);
                cs.moveToNext();
            }

        }
        cs.close();
        return userModelList;
    }
    public boolean InsertStatus(String Status)
    {
        SQLiteDatabase mydb=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("Status",Status);
        try {
            mydb.insert(STATUS_TABLE, null, cv);
            mydb.close();
            return true;
        }
        catch (Exception e)
        {
            mydb.close();
            return false;
        }
    }
   /*public List<StatusModel> GetStatus()
   {
       List<StatusModel> statusModels=new ArrayList<>();
       SQLiteDatabase mydb=this.getReadableDatabase();
       Cursor cs=mydb.rawQuery("SELECT S.Status_ID,S.User_ID,U.User_Name,S.Status FROM "+STATUS_TABLE+" S  INNER JOIN "+USER_TABLE+" U ON S.User_ID=U.User_ID;"
,null);
       if(cs.moveToFirst())
       {
           while(!cs.isAfterLast())
           {
               StatusModel smodel=new StatusModel(cs.getInt(cs.getColumnIndex("Status_ID")),cs.getInt(cs.getColumnIndex("User_ID")),
                       cs.getString(cs.getColumnIndex("User_Name")),cs.getString(cs.getColumnIndex("Status")));
               statusModels.add(smodel);
               cs.moveToNext();
           }
       }
       cs.close();
       return statusModels;
   }*/
   public List<StatusModel> GetStatus()
   {
       List<StatusModel> statusModelList=new ArrayList<>();
       SQLiteDatabase mydb=this.getReadableDatabase();
       Cursor cs=mydb.rawQuery("SELECT * FROM "+STATUS_TABLE,null);
       if(cs.moveToFirst())
       {
           while(!cs.isAfterLast())
           {
               StatusModel smodel=new StatusModel(cs.getInt(0),
                       cs.getInt(cs.getColumnIndex("User_ID")),
                       cs.getString(cs.getColumnIndex("Status")));
               statusModelList.add(smodel);
               cs.moveToNext();
           }
       }
       cs.close();
       return statusModelList;
   }
   public void UpdateStatus(int StatusID,String Status)
   {
       SQLiteDatabase mydb=this.getWritableDatabase();
       mydb.execSQL("UPDATE "+STATUS_TABLE+" SET Status='"+Status+"' WHERE Status_ID="+StatusID);
       mydb.close();
   }
   public void RemoveStatus(int StatusID)
   {
       SQLiteDatabase mydb=this.getWritableDatabase();
       mydb.execSQL("DELETE FROM "+STATUS_TABLE+" WHERE Status_ID="+StatusID);
       mydb.close();
   }

}
