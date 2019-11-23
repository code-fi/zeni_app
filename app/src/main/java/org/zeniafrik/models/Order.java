package org.zeniafrik.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

public class Order implements Parcelable {

    protected final int id;
    public String product_name,placed_on,status;
    public  int quantity = 0;
    private double amount,price_per_item;
    private Customer customer;
    

    protected Order(Parcel in) {
        id = in.readInt();
        quantity = in.readInt();
        product_name = in.readString();
        placed_on = in.readString();
        status = in.readString();
        amount = in.readDouble();
        price_per_item = in.readDouble();
    }


    @Override
    public void writeToParcel(Parcel parcel,int i) {
        parcel.writeInt(id);
        parcel.writeInt(quantity);
        parcel.writeString(product_name);
        parcel.writeString(placed_on);
        parcel.writeString(status);
        parcel.writeDouble(amount);
        parcel.writeDouble(price_per_item);

    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {return new Order(in);}

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    

    public Order(int id, int quantity, String product_name, String placed_on, String status, double amount, double price_per_item,@Nullable Customer customer) {
        this.id = id;
        this.quantity = quantity;
        this.product_name = product_name;
        this.placed_on = placed_on;
        this.status = status;
        this.amount = amount;
        this.price_per_item = price_per_item;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public double getPrice_per_item() {
        return price_per_item;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static class Customer{
      String customer_name,customer_phone,customer_address,customer_remarks;
      public Customer(String customer_name,String customer_phone,String customer_address,@Nullable String customer_remarks){
          this.customer_name = customer_name;
          this.customer_phone = customer_phone;
          this.customer_address = customer_address;
          this.customer_remarks = customer_remarks;
      }

        public String getCustomer_name() {
            return customer_name;
        }

        public String getCustomer_phone() {
            return customer_phone;
        }

        public String getCustomer_address() {
            return customer_address;
        }

        public String getCustomer_remarks() {
            return customer_remarks;
        }
    }

    /*public final class OrderExt{
        final Customer customer;
        final int payment_method,product_id;
        public OrderExt(Customer customer, int payment_method, int product_id) {
            this.customer = customer;
            this.payment_method = payment_method;
            this.product_id = product_id;
        }

        public Customer getCustomer() {
            return customer;
        }

        public int getPayment_method() {
            return payment_method;
        }

        public int getProduct_id() {
            return product_id;
        }
    }*/
}
