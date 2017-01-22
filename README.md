SQLite is an open-source relational database i.e. used to perform database operations on  devices such as storing, 
manipulating or retrieving  data from the database.

It is embedded in android by default. So, there is no need to perform any database setup .

Here, we are going to see the example of sqlite to store, fetch, update and delete the data. 

SQLiteOpenHelper class provides the functionality to use the SQLite database.
It is available in android.database.sqlite package, is used for database creation and version management. 
For performing any database operation, you have to provide the implementation of onCreate() and onUpgrade() methods.

To create a table 

    @Override
     public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL("create table\t"+TABLE_NAME+"\n"+"("+
                COLUMN_ONE+"\t integer primary key ,\n"+
                COLUMN_TWO+"\t"+"varchar(50),\n"+
                COLUMN_THREE+"\t"+"varchar(20));");
     }
    
    
 Inset a record in created table
    
    
      public void insertRecord(Customer customer){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_ONE,customer.getCustomerId());
        cv.put(COLUMN_TWO,customer.getCustomerName());
        cv.put(COLUMN_THREE,customer.getCustomerNumber());
        db.insert(TABLE_NAME,null,cv);
     }

Delete a row from the table

    public boolean delete(String customerId){
   
        boolean status=false;
        SQLiteDatabase db=getWritableDatabase();
        if(db!=null){

            status=db.delete(TABLE_NAME,COLUMN_ONE+"\t="+customerId,null)>0;
        }
        return status;
    }
    
    
   Update a record in a table
    
    
    
       public boolean update(String customerId,String customerName,String customerNumber){
        boolean status=false;
        String updateQuery="Update table\t"+TABLE_NAME+"\t set\t"+COLUMN_TWO+"\t="+customerName+"\t,"+
                            COLUMN_THREE+"\t="+customerNumber+"\t where\t"+COLUMN_ONE+"="+customerId;
        SQLiteDatabase db=getWritableDatabase();
        if(db!=null){
            ContentValues cv=new ContentValues();
            cv.put(COLUMN_TWO,customerName);
            cv.put(COLUMN_THREE,customerNumber);
           status=db.update(TABLE_NAME,cv,COLUMN_ONE+"\t="+customerId,null)>0 ;
        }
        return status;
    }


