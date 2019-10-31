package com.example.saarang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;



public class MainActivity extends AppCompatActivity {

    LinearLayoutCompat pay_icon_layout;
    LinearLayoutCompat transfer_icon_layout;

    TextView balance;


    Intent proceed_pay;
    Intent proceed_transfer;


    private ProgressBar pBaramount;
    private ProgressBar pBartransaction;



    final Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pay_icon_layout= findViewById(R.id.pay_icon_layout);
        transfer_icon_layout= findViewById(R.id.transfer_icon_layout);

        pBaramount=findViewById(R.id.pBaramount);
        pBartransaction=findViewById(R.id.pBartransaction);

        pBaramount.setVisibility(View.VISIBLE);
        pBartransaction.setVisibility(View.VISIBLE);





        pay_icon_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceed_pay=new Intent(context,scan.class);
                context.startActivity(proceed_pay);

            }
        });




        transfer_icon_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceed_transfer=new Intent(context,Transfer.class);
                context.startActivity(proceed_transfer);

            }
        });





        String response =   "{\n" +
                "  \"balance\": 400,\n" +
                "  \"transactions\": [\n" +
                "    {\n" +
                "      \"vendor_name\": \"ABC\",\n" +
                "      \"vendor_id\": 123,\n" +
                "      \"transaction_id\": \"ABC123\",\n" +
                "      \"timestamp\": \"12-09-2019 00:00:00\",\n" +
                "      \"transaction_amount\": 100\n" +
                "    }\n" +
                "  ]\n" +
                "}";


                responseHandle(response);





    }

    private void responseHandle(String response) {
        if(response==null) {
            System.out.println("Error");
        }
        else{
            Gson gson =new Gson();


            Content content= gson.fromJson(response, Content.class);

            pBaramount.setVisibility(View.GONE);
            pBartransaction.setVisibility(View.GONE);

            balance=findViewById(R.id.balance);











            RecyclerView recyclerView =  findViewById(R.id.my_recycler_view);
            RecentTransactionsAdapter recentTransactionsAdapter =new RecentTransactionsAdapter(content.transactions);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(recentTransactionsAdapter);
            if(Integer.parseInt(content.balance)<0 ){
                Toast.makeText(getApplicationContext(),"Invalid balance", Toast.LENGTH_SHORT).show();
            }
            if(Integer.parseInt(content.balance)==0 ){
                Toast.makeText(getApplicationContext(),"Zero balance", Toast.LENGTH_SHORT).show();
            }


            balance.setText(String.valueOf(content.balance));


        }
    }
}
