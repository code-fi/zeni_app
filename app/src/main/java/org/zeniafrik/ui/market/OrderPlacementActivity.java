package org.zeniafrik.ui.market;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.zeniafrik.R;
import org.zeniafrik.di.Injectable;
import org.zeniafrik.extras.Validator;
import org.zeniafrik.factory.ViewModelFactory;
import org.zeniafrik.helper.BaseActivity;
import org.zeniafrik.models.BaseProduct;
import org.zeniafrik.models.Order;
import org.zeniafrik.ui.extras.ActivityIndicatorDialog;
import org.zeniafrik.ui.extras.GenericAlertDialog;
import org.zeniafrik.ui.market.ViewModels.OrderPlacementViewModel;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.util.Objects.requireNonNull;


public class OrderPlacementActivity extends BaseActivity implements Injectable {

    private ActivityIndicatorDialog indicatorDialog;
    private BaseProduct product;

    private double amount = 0;
    private int quantity = 1;

    @Inject
    ViewModelFactory factory;

    @Inject
    OrderPlacementViewModel viewModel;

    @BindString(R.string.valid_gh_number_prompt)
    public String valid_gh_number_prompt;

    @BindString(R.string.order_cancel_question)
    String order_cancel_question;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.product_name)
    public TextView product_name;

    @BindView(R.id.product_price)
    public TextView product_price;

    @BindView(R.id.quantity_input)
    public EditText quantity_input;

    @BindView(R.id.c_first_name)
    public EditText c_first_name;

    @BindView(R.id.c_last_name)
    public EditText c_last_name;

    @BindView(R.id.c_phone)
    public EditText c_phone;

    @BindView(R.id.c_address)
    public EditText c_address;

    @BindView(R.id.order_remarks)
    public EditText order_remarks;

    @BindString(R.string.order_message)
    public String order_message;


    @BindString(R.string.valid_address)
    public String valid_address;

    @BindString(R.string.ghana_cedi)
    public String ghana_cedi;

    @BindString(R.string.f_n_n_l_n_required)
    public String f_n_l_required;

    @OnClick(R.id.increase_btn)
    public void onIncreaseClick() {
        String quantityString = quantity_input.getText().toString();
        quantity = quantityString.isEmpty() ? 1 : Integer.parseInt(quantityString);
        double price = product.getPrice();
        if (quantity < 50) {
            quantity += 1;
            amount = price * quantity;
            quantity_input.setText(String.valueOf(quantity));
        } else {
            amount = price * 50;
            quantity_input.setText(String.valueOf(50));
        }
        product_price.setText(String.format(ghana_cedi, amount));
    }

    @OnClick(R.id.decrease_btn)
    public void onDecreaseClick() {
        String quantityString = quantity_input.getText().toString();
        quantity = quantityString.isEmpty() ? 1 : Integer.parseInt(quantityString);
        double price = product.getPrice();
        if (quantity > 1) {
            quantity -= 1;
            amount = price * quantity;
            quantity_input.setText(String.valueOf(quantity));
        } else {
            amount = price;
            quantity_input.setText(String.valueOf(1));
        }
        product_price.setText(String.format(ghana_cedi, amount));

    }

    @OnClick(R.id.cancel_action)
    public void onCancelClick() {
        onBackPressed();
    }


    @OnClick(R.id.submit_btn)
    public void onSubmitClick() {
        String first_name = c_first_name.getText().toString(),
                last_name = c_last_name.getText().toString(),
                address = c_address.getText().toString(),
                phone = c_phone.getText().toString(),
                remarks = order_remarks.getText().toString(),
                error_msg = null;

        View v = null;

        if (first_name.isEmpty() || last_name.isEmpty()) {
            error_msg = f_n_l_required;
            v = c_first_name;
        }else if (!Validator.isValidGhanaNumber(phone)) {
            error_msg = valid_gh_number_prompt;
            v = c_phone;
        } else if (Validator.isLessThan(address, 10)) {
            error_msg = valid_address;
            v = c_address;
        }

        if (error_msg != null) {
            v.requestFocus();
            Toast.makeText(OrderPlacementActivity.this, error_msg, Toast.LENGTH_LONG).show();
        } else {
            double price = product.getPrice();
            amount = amount == 0 ? price : amount;
            String full_name = first_name.concat(" ".concat(last_name));
            Order.Customer customer = new Order.Customer(full_name, phone, address, remarks);

            Order simpleOrder = new Order(product.getId(), quantity, product.name, "now", "unapproved", amount, price, customer);
            indicatorDialog.show();
            viewModel.placeOrder(simpleOrder);

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_form_component);
        ButterKnife.bind(this);

        indicatorDialog = new ActivityIndicatorDialog(this);

        viewModel = ViewModelProviders.of(OrderPlacementActivity.this, factory).get
                (OrderPlacementViewModel.class);

        viewModel.orderResponseSubscription().observe(OrderPlacementActivity.this, orderPlacement_response -> {
            switch (requireNonNull(orderPlacement_response).status) {
                case SUCCESS:
                    new GenericAlertDialog(OrderPlacementActivity.this, true, dialogInterface -> finish(), order_message, 1).show();
                    indicatorDialog.dismiss();
                    break;
                case ERROR:
                    new GenericAlertDialog(OrderPlacementActivity.this, orderPlacement_response.message, 2).show();
                    indicatorDialog.hide();
                    break;
            }
        });

        setSupportActionBar(toolbar);
        requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        product = getIntent().getParcelableExtra("org.zeniafrik.models.BaseProduct");

        product_name.setText(product.name);
        product_price.setText(String.format("Ghc %s", product.getPrice()));


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(indicatorDialog.isShowing()) indicatorDialog.dismiss();
        indicatorDialog = null;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(OrderPlacementActivity.this)
                .setTitle(order_cancel_question)
                .setNegativeButton("No", (dialogInterface, i) -> super.onBackPressed())
                .setPositiveButton("Yes",(di,i)->di.dismiss())
                .show();
    }
}
