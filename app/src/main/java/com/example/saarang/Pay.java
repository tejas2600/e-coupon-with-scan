package com.example.saarang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Pay extends AppCompatActivity {
    Button pay_confirm ;
    TextView vendor;
    TextView amount_confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);

        pay_confirm=(Button) findViewById(R.id.pay_confirm);

        pay_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        vendor= (TextView) findViewById(R.id.Vendor_name);
        amount_confirm= (TextView) findViewById(R.id.Amount_confirm);


    }
}
