package com.android_sqlite.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.android_sqlite.R;
import com.android_sqlite.adapters.CustomerAdapter;
import com.android_sqlite.gettersetter.Customer;
import com.android_sqlite.sqlite.SqliteDatabaseHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private AppCompatButton btn_insert_record, btn_get_record;
    private SqliteDatabaseHelper sqliteDBhelper;
    private RelativeLayout layout_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=MainActivity.this;
        sqliteDBhelper=new SqliteDatabaseHelper(context);
        btn_get_record=(AppCompatButton)findViewById(R.id.btn_get_record);
        btn_insert_record=(AppCompatButton)findViewById(R.id.btn_insert_record);
        layout_main=(RelativeLayout) findViewById(R.id.layout_main);
        btn_get_record.setOnClickListener(getRecordClickListner);
        btn_insert_record.setOnClickListener(insertRecordClickListener);


    }

    View.OnClickListener insertRecordClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            loadInsertRecordView();
        }
    };

    View.OnClickListener getRecordClickListner=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           loadExistingRecordView();
        }
    };

    /**
     * load view which enable user to add a new record in database
     */
    private void loadInsertRecordView(){
        layout_main.removeAllViews();
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_add_new_customer, layout_main);
        final AppCompatEditText edt_customer_id=(AppCompatEditText)view.findViewById(R.id.edt_customer_id);
        final AppCompatEditText edt_customer_name=(AppCompatEditText)view.findViewById(R.id.edt_customer_name);
        final AppCompatEditText edt_customer_number=(AppCompatEditText)view.findViewById(R.id.edt_customer_number);
        AppCompatButton btn_add_record=(AppCompatButton)view.findViewById(R.id.btn_add_record);
        /**
         * add a new record new customer record in database
         */
        btn_add_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String customerId=edt_customer_id.getEditableText().toString().trim();
                String customerName=edt_customer_name.getEditableText().toString().trim();
                String customerNumber=edt_customer_number.getEditableText().toString().trim();
                if(customerId.equals("")){
                    edt_customer_id.setError("Mandatory");
                }
                else if(customerName.equals("")){
                    edt_customer_name.setError("Mandatory");
                }
                else if(customerNumber.equals("")){
                    edt_customer_number.setError("Mandatory");
                }
                else{
                    Customer custoner=new Customer();
                    custoner.setCustomerId(customerId);
                    custoner.setCustomerName(customerName);
                    custoner.setCustomerNumber(customerNumber);
                    sqliteDBhelper.insertRecord(custoner);
                    edt_customer_id.setText("");
                    edt_customer_name.setText("");
                    edt_customer_number.setText("");
                }

            }
        });

    }

    /**
     * load view which load customers record in a list view
     */
    private void loadExistingRecordView(){
        layout_main.removeAllViews();
        LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.layout_list_customers, layout_main);
        ListView list_customers=(ListView)view.findViewById(R.id.list_customers);
        List<Customer> customers=sqliteDBhelper.getRecords();
        CustomerAdapter adapter=new CustomerAdapter(context,R.layout.layout_customer,customers);
        list_customers.setAdapter(adapter);

    }



}
