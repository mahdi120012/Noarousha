package hozorghiyab.listCityACT;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    public final String Name = "main_db";
    public SQLiteDatabase mydb;
    private Context mycontext = null;
    public String path;

    public Database(Context context) {
        super(context, "main_db", null, 1);
        mycontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
    }

    public void useable() {

        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(mycontext,Name);
        mydb = helper.getReadableDatabase();
        path = mydb.getPath();
        mydb.close();

        boolean checkdb = checkdb();
        if (checkdb) {

        } else {
            //this.getReadableDatabase();
            File db = new File(path);
            if (db.exists()) {
                db.delete();
            }
            try {
                copydatabase();
            }
            catch (IOException e) {

            }
        }
    }

    public void open() {
        mydb = SQLiteDatabase.openDatabase(path + Name, null, SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public void close() {
        mydb.close();
    }

    public boolean checkdb() {
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(path + Name, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch (SQLException e) {

        }
        return db != null ? true : false;
    }

    public void copydatabase() throws IOException {

        OutputStream myOutput = new FileOutputStream(path + Name);
        byte[] buffer = new byte[1024];
        int lenght;
        InputStream myInput = mycontext.getAssets().open(Name);
        while ((lenght = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, lenght);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();
    }

    public void addClass(String table,String schoolName ,String className) {
        ContentValues values = new ContentValues();
        values.put("schoolName", schoolName);
        values.put("className", className);
        mydb.insert(table, null, values);
    }

    public Integer shomaresh_field(String table, String field) {

        Cursor Cursor = mydb.rawQuery("SELECT * FROM " + table + "", null);
        int i = Cursor.getCount();
        return i;
    }

 /*   public String namayesh_fasl(String table, int row) {
        Cursor Cursor = mydb.rawQuery("SELECT * FROM " + table + "", null);
        Cursor.moveToPosition(row);
        String s = Cursor.getString(1);
        return s;

    }*/

    public String namayesh_fasl2(String table, int row, int field) {
        Cursor Cursor = mydb.rawQuery("SELECT * FROM " + table + "", null);
        Cursor.moveToPosition(row);
        String save = Cursor.getString(field);
        return save;
    }

/*    public List<HashMap<String, Object>> search(String text,String method) {

        List<HashMap<String, Object>> all_data = new ArrayList<>();
            Cursor result = mydb.rawQuery("select * from tbl_data where name like '%" + text + "%' group by name", null);
            while (result.moveToNext()) {
                HashMap<String, Object> temp = new HashMap<String, Object>();
                temp.put("onvan", result.getString(1));
                temp.put("season", result.getString(4));
                all_data.add(temp);
            }

        return all_data;
    }*/



    public String findEnglishNameCity(String table,String city,  int row) {
        Cursor Cursor = mydb.rawQuery("SELECT * FROM " + table + " where name like '%" + city + "%' ", null);
        Cursor.moveToPosition(row);
        String s = Cursor.getString(2);
        return s;
    }

}


class MySQLiteOpenHelper extends SQLiteOpenHelper {

    MySQLiteOpenHelper(Context context, String databaseName) {
        super(context, databaseName, null, 2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}