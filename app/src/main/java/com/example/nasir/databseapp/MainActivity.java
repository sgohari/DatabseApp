package com.example.nasir.databseapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText search,name,phone,address;
    String _search,_name,_phone,_address;

    SQLiteDatabase sqLiteDatabase;
    Button Save, SelectAll,Select,Delete,Update;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create database name
        sqLiteDatabase=openOrCreateDatabase("RegistrationDB", Context.MODE_PRIVATE,null);

        //create table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Reservation(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(255), phone VARCHAR(255), address VARCHAR (255));");
        //sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Reg(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(255),phone VARCHAR(255),adress VARCHAR (255));");

        search=(EditText)findViewById(R.id.searchET);
        name=(EditText)findViewById(R.id.nameET);
        phone=(EditText)findViewById(R.id.phoneET);
        address=(EditText)findViewById(R.id.addressET);


        //buttons
        Save=(Button)findViewById(R.id.btnSave);
        SelectAll=(Button)findViewById(R.id.btnSelectAll);
        Select=(Button)findViewById(R.id.btnSelect);
        Delete=(Button)findViewById(R.id.btnDelete);
        Update=(Button)findViewById(R.id.btnUpdate);

        Save.setOnClickListener(this);
        SelectAll.setOnClickListener(this);
        Select.setOnClickListener(this);
        Delete.setOnClickListener(this);
        Update.setOnClickListener(this);

    }

    @Override
    public void onClick (View view) {

        if (view.getId()==R.id.btnSave){

            _name=name.getText().toString();
            _phone=phone.getText().toString();
            _address=address.getText().toString();

            if (_name.equals("")||_phone.equals("")||_address.equals("")){
                Toast.makeText(this,"Fields are Required",Toast.LENGTH_LONG).show();
                return;
            }else {

                sqLiteDatabase.execSQL("Insert Into Reservation(name,phone,address)VALUES('" +_name+ "','" +_phone+ "','" +_address+ "'); ");
                //sqLiteDatabase.execSQL("Insert Into Reg(name, phone, address)VALUES('" + _name + "','" + _phone + "','" + _address + "'); ");
                Toast.makeText(this,"Record Saved ",Toast.LENGTH_LONG).show();

                name.setText("");
                phone.setText("");
                address.setText("");

                return;

            }
        }
        else if (view.getId()==R.id.btnSelectAll){

            Cursor cursor=sqLiteDatabase.rawQuery("Select * from Reservation",null);

            if (cursor.getCount()==0){

                Toast.makeText(MainActivity.this,"DB is Empty",Toast.LENGTH_LONG).show();
                return;
            }
            StringBuffer stringBuffer=new StringBuffer();
            while (cursor.moveToNext()){
                stringBuffer.append("The Name is "+ cursor.getString(1)+"\n");
                stringBuffer.append("The Phone is "+ cursor.getString(2)+"\n");
                stringBuffer.append("The Address is "+ cursor.getString(3)+"\n");

            }

            Toast.makeText(this,stringBuffer.toString(),Toast.LENGTH_LONG).show();
        }
        else  if (view.getId()==R.id.btnSelect){

            _search=search.getText().toString().trim();

            if (_search.equals("")){

                Toast.makeText(MainActivity.this,"Enter a Name to Search",Toast.LENGTH_LONG).show();
                return;
            }

            Cursor c =sqLiteDatabase.rawQuery("Select * From Reservation Where name='" + _search + "'",null);

            if (c.moveToFirst()){

                name.setText(c.getString(1));
                phone.setText(c.getString(2));
                address.setText(c.getString(3));
            }
            else {

                Toast.makeText(MainActivity.this,"Data not found ",Toast.LENGTH_LONG).show();
                return;
            }

        }
        else if (view.getId()==R.id.btnUpdate)
        {
            _search=search.getText().toString().trim();
            _name=name.getText().toString().trim();
            _phone=phone.getText().toString().trim();
            _address=address.getText().toString().trim();

            if (_search.equals("")){

                Toast.makeText(MainActivity.this,"Please Type",Toast.LENGTH_LONG).show();
                return;
            }

            Cursor cursorUpdate =sqLiteDatabase.rawQuery("Select * From Reservation Where name='" + _search + "'",null);

            if (cursorUpdate.moveToFirst()) {
                if (_name.equals("") || _phone.equals("") || _address.equals("")) {
                    Toast.makeText(this, "Fields are Required", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    sqLiteDatabase.execSQL("Update Reservation set name='" + _name + "', phone='" + _phone + "', address='" + _address + "' Where name='"+_search+"'");
                    Toast.makeText(this,"Record Modified ",Toast.LENGTH_LONG).show();
                    return;

                }
            }else {
                Toast.makeText(this,"Record is not found!! ",Toast.LENGTH_LONG).show();
            }

        }
        else if (view.getId()==R.id.btnDelete){

            _search=search.getText().toString().trim();

            if (_search.equals("")){

                Toast.makeText(MainActivity.this,"Type name to delete ",Toast.LENGTH_LONG).show();
                return;
            }

            Cursor cursorDelete =sqLiteDatabase.rawQuery("Select * From Reservation Where name='" + _search + "'",null);

            if (cursorDelete.moveToFirst()){

               sqLiteDatabase.execSQL("Delete From Reservation Where name='"+_search+"'");
                Toast.makeText(MainActivity.this,"Record Deleted ",Toast.LENGTH_LONG).show();
                return;
            }
            else {

                Toast.makeText(MainActivity.this,"Data not found ",Toast.LENGTH_LONG).show();
                return;
            }

        }


    }
}
