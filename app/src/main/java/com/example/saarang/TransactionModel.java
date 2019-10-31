package com.example.saarang;
import com.google.gson.annotations.SerializedName;

public class TransactionModel {

    @SerializedName("vendor_name")
    public String vendor_name;
    @SerializedName("vendor_id")
    public String vendor_id;
    @SerializedName("transaction_id")
    public String transaction_id;
    @SerializedName("timestamp")
    public String timestamp;
    @SerializedName("transaction_amount")
    public String transaction_amount;


    public TransactionModel(String vendor_name,String vendor_id,String transaction_id,String timestamp,String transaction_amount)
    {
        this.vendor_name=vendor_name;

        this.vendor_id=vendor_id;
        this.transaction_id=transaction_id;
        this.timestamp=timestamp;
        this.transaction_amount=transaction_amount;


    }
     public String getVendor_name(){
        return vendor_name;
     }
    public void setVendor_name(String vendor_name){
          this.vendor_name=vendor_name;
    }
    public String getVendor_id(){
        return vendor_id;
    }
    public void setVendor_id(String vendor_id){
        this.vendor_id=vendor_id;
    }
    public String getTransaction_id(){
        return transaction_id;
    }
    public void setTransaction_id(String transaction_id){
        this.transaction_id=transaction_id;
    }
    public String getTimestamp(){
        return timestamp;
    }
    public void setTimestamp(String timestamp){
        this.timestamp=timestamp;
    }

    public String getTransaction_amount(){
        return transaction_amount;
    }
    public void setTransaction_amount(String transaction_amount){
        this.transaction_amount=transaction_amount;
    }


}
