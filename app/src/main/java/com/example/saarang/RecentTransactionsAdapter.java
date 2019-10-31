package com.example.saarang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RecentTransactionsAdapter extends RecyclerView.Adapter<RecentTransactionsAdapter.ViewHolder> {
    private TransactionModel[] transactions;

    public RecentTransactionsAdapter(TransactionModel[] transactions) {
       this.transactions = transactions;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {

        LinearLayoutCompat view = (LinearLayoutCompat) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_layout, parent, false);

       return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.vendor_name.setText(transactions[position].getVendor_name());
        holder.vendor_id.setText(transactions[position].getVendor_id());
        holder.transaction_id.setText(transactions[position].getTransaction_id());
        holder.timestamp.setText(transactions[position].getTimestamp());
        holder.transaction_amount.setText(transactions[position].getTransaction_amount());

    }


    @Override
    public int getItemCount() {
        return transactions.length;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView vendor_name ;
        TextView vendor_id ;
        TextView transaction_id ;
        TextView timestamp;
        TextView transaction_amount;
        LinearLayoutCompat transaction_layout;

        public ViewHolder(View view){
            super(view);
            vendor_name = itemView.findViewById(R.id.vendor_name);
            vendor_id = itemView.findViewById(R.id.vendor_id);
            transaction_id = itemView.findViewById(R.id.transaction_id);
            timestamp = itemView.findViewById(R.id.timestamp);
            transaction_amount = itemView.findViewById(R.id.transaction_amount);

            transaction_layout=itemView.findViewById(R.id.transaction_layout);


        }
    }
}
