package com.android_sqlite.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.android_sqlite.R;
import com.android_sqlite.gettersetter.Customer;

import java.util.List;

/**
 * Created by himansh on 20/1/17.
 */

public class CustomerAdapter extends BaseAdapter {
    private Context context;
    private int targetLayout;
    private List<Customer> customers;
    private LayoutInflater inflater;
    public CustomerAdapter(Context context,int targetLayout,List<Customer> customers){
        this.context=context;
        this.targetLayout=targetLayout;
        this.customers=customers;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return customers.size();
    }

    @Override
    public Customer getItem(int position) {
        return customers.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class RecordHOlder{
        AppCompatTextView txt_customer_id,txt_customer_name,txt_customer_number;
    }
    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        RecordHOlder holder=new RecordHOlder();
        if(view==null){
            view=inflater.inflate(targetLayout,null);
            holder.txt_customer_id=(AppCompatTextView)view.findViewById(R.id.txt_customer_id);
            holder.txt_customer_name=(AppCompatTextView)view.findViewById(R.id.txt_customer_name);
            holder.txt_customer_number=(AppCompatTextView)view.findViewById(R.id.txt_customer_number);
            view.setTag(holder);
        }
        else{
            holder=(RecordHOlder)view.getTag();
        }

        Customer customer=customers.get(index);
        holder.txt_customer_id.setText(customer.getCustomerId());
        holder.txt_customer_name.setText(customer.getCustomerName());
        holder.txt_customer_number.setText(customer.getCustomerNumber());
        return view;
    }
}
