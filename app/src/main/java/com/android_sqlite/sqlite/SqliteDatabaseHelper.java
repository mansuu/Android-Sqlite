package com.android_sqlite.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.android_sqlite.gettersetter.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by himansh on 20/1/17.
 */

public class SqliteDatabaseHelper extends SQLiteOpenHelper {
    private final String COLUMN_ONE="customer_id";
    private final String COLUMN_TWO="customer_name";
    private final String COLUMN_THREE="customer_number";
    private static final String DATABASE_NAME="customers.db";
    private final String TABLE_NAME="customer_record";

    public SqliteDatabaseHelper(Context context) {
       super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL("create table\t"+TABLE_NAME+"\n"+"("+
                COLUMN_ONE+"\t integer primary key ,\n"+
                COLUMN_TWO+"\t"+"varchar(50),\n"+
                COLUMN_THREE+"\t"+"varchar(20));");
    }

    public void insertRecord(Customer customer){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_ONE,customer.getCustomerId());
        cv.put(COLUMN_TWO,customer.getCustomerName());
        cv.put(COLUMN_THREE,customer.getCustomerNumber());
        db.insert(TABLE_NAME,null,cv);

    }

    public List<Customer> getRecords(){
        List<Customer> customers=null;
        SQLiteDatabase db=getReadableDatabase();
        if(db!=null){
            Cursor result=db.rawQuery("select * from\t"+TABLE_NAME,null);
            if(result!=null){
                customers=new ArrayList<Customer>();
                if(result.moveToFirst()){
                    do{
                        Customer customer=new Customer();
                        customer.setCustomerId(result.getString(result.getColumnIndex(COLUMN_ONE)));
                        customer.setCustomerName(result.getString(result.getColumnIndex(COLUMN_TWO)));
                        customer.setCustomerNumber(result.getString(result.getColumnIndex(COLUMN_THREE)));
                        customers.add(customer);
                    }while(result.moveToNext());
                }
            }
        }
        return customers;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
