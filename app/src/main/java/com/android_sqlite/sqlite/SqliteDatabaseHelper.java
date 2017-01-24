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

    /**
     * called when constructor of this class is called
     * @param sqLiteDatabase
     *
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL("create table\t"+TABLE_NAME+"\n"+"("+
                COLUMN_ONE+"\t integer primary key ,\n"+
                COLUMN_TWO+"\t"+"varchar(50),\n"+
                COLUMN_THREE+"\t"+"varchar(20));");
    }

    /**
     *
     * @param customer
     * receives an object of Customer class and inserts it's record in database
     */
    public void insertRecord(Customer customer){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_ONE,customer.getCustomerId());
        cv.put(COLUMN_TWO,customer.getCustomerName());
        cv.put(COLUMN_THREE,customer.getCustomerNumber());
        db.insert(TABLE_NAME,null,cv);

    }

    /**
     *
     * @return
     * All existing records
     */
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

    /**
     *
     * @param sqLiteDatabase
     * @param i
     * @param i1
     * upgrade your database with new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     *
     * @param customerId takes customerID as primary key to delete a row
     * @return true/false return true if deleted and false if deletion failed
     *
     */
    public boolean delete(String customerId){
        boolean status=false;
        SQLiteDatabase db=getWritableDatabase();
        if(db!=null){

            status=db.delete(TABLE_NAME,COLUMN_ONE+"\t="+customerId,null)>0;
        }
        return status;
    }

    /**
     *
     * @param customerId
     * @param customerName
     * @param customerNumber
     * @return true/false if updated with new values where COLUMN_NAME=given customerID
     */
    public boolean update(String customerId,String customerName,String customerNumber){
        boolean status=false;
        SQLiteDatabase db=getWritableDatabase();
        if(db!=null){
            ContentValues cv=new ContentValues();
            cv.put(COLUMN_TWO,customerName);
            cv.put(COLUMN_THREE,customerNumber);
           status=db.update(TABLE_NAME,cv,COLUMN_ONE+"\t="+customerId,null)>0 ;
        }
        return status;
    }

}
