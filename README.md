SQLite is an open-source relational database i.e. used to perform database operations on  devices such as storing, 
manipulating or retrieving  data from the database.

It is embedded in android by default. So, there is no need to perform any database setup .

Here, we are going to see the example of sqlite to store, fetch, update and delete the data. 

SQLiteOpenHelper class provides the functionality to use the SQLite database.
It is available in android.database.sqlite package, is used for database creation and version management. 
For performing any database operation, you have to provide the implementation of onCreate() and onUpgrade() methods.





public class SqliteDatabaseHelper extends SQLiteOpenHelper

{

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
}
