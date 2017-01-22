package com.android_sqlite.screens;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
        final List<Customer> customers=sqliteDBhelper.getRecords();
        final CustomerAdapter adapter=new CustomerAdapter(context,R.layout.layout_customer,customers);
        list_customers.setAdapter(adapter);
        list_customers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                PopupMenu menu=new PopupMenu(context,view);
                menu.getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());
                menu.show();
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.delete:
                                Customer customer=customers.get(i);
                                boolean deleteStatus=sqliteDBhelper.delete(customer.getCustomerId());
                                if(deleteStatus==true){
                                    customers.remove(customer);
                                    adapter.notifyDataSetChanged();
                                }

                                break;
                            case R.id.update:
                                showDailog(customers.get(i),adapter);
                                break;
                        }
                        return false;
                    }
                });
                return false;
            }
        });

    }

    /**
     * show dialog to update customer record
     * @param customer to update name and number
     * @param adapter to notify the list of records
     */
    private void showDailog(final Customer customer, final CustomerAdapter adapter){

    final Dialog updateDialog=new Dialog(context);
    updateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    updateDialog.setContentView(R.layout.layout_add_new_customer);
    updateDialog.show();
    AppCompatEditText edt_customer_id=(AppCompatEditText)updateDialog.findViewById(R.id.edt_customer_id);
    final AppCompatEditText edt_customer_name=(AppCompatEditText)updateDialog.findViewById(R.id.edt_customer_name);
    final AppCompatEditText edt_customer_number=(AppCompatEditText)updateDialog.findViewById(R.id.edt_customer_number);
    AppCompatButton btn_upadte=(AppCompatButton) updateDialog.findViewById(R.id.btn_add_record);
    btn_upadte.setText("UPDATE");
    edt_customer_id.setText(customer.getCustomerId());
    edt_customer_id.setEnabled(false);
    edt_customer_name.setText(customer.getCustomerName());
    edt_customer_number.setText(customer.getCustomerNumber());
    btn_upadte.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String customerName=edt_customer_name.getEditableText().toString().trim();
            String customerNumber=edt_customer_number.getEditableText().toString().trim();
            if(customerName.equals("")){
                edt_customer_name.setError("Mandatory");
            }
            else if(customerNumber.equals("")){
                edt_customer_number.setError("Mandatory");
            }
            else{
                 boolean isUpdated=sqliteDBhelper.update(customer.getCustomerId(),customerName,customerNumber);
                if(isUpdated==true){
                   customer.setCustomerName(customerName);
                    customer.setCustomerNumber(customerNumber);
                    adapter.notifyDataSetChanged();
                    updateDialog.dismiss();
                }
                else{
                    Toast.makeText(context,"Try again",Toast.LENGTH_SHORT).show();
                }

            }
        }
    });
}

}
