package com.example.android.completecoffee;

import java.text.NumberFormat;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int numOfCC = 0;
    CheckBox wc, c;
    TextView quantityTextView, name;
    boolean wc_status, c_status;
    String priceText, wc_value, c_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void increment(View view) {
        display('+');
    }

    public void decrement(View view) {
        display('-');
    }

    public void wcClicked(View view) {
        wc = (CheckBox) findViewById(R.id.wc);
        c = (CheckBox) findViewById(R.id.c);
        c.setVisibility(View.VISIBLE);
    }

    public void submitOrder(View view) {
        name = (TextView) findViewById(R.id.name);

        String text = getString(R.string.name,name.getText()) + "\n" +
                getString(R.string.wc) + getString(R.string.space) + wc_value + "\n" +
                getString(R.string.choc) + getString(R.string.space) + c_value + "\n" +
                getString(R.string.qty) + getString(R.string.space) + quantityTextView.getText() + "\n" +
                priceText + "\n" +
                getString(R.string.ty);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"test@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(char ch) {

        int price;

        if (ch == '+')
            numOfCC = numOfCC + 1;
        else {
            if (numOfCC > 0)
                numOfCC = numOfCC - 1;
        }

        quantityTextView = (TextView) findViewById(R.id.quantity_text_view);

        wc_status = wc.isChecked();
        wc_value = wc_status ? getString(R.string.y) : getString(R.string.n);
        c_status = c.isChecked();
        c_value = c_status ? getString(R.string.y) : getString(R.string.n);

        if (wc_status && c_status)
            price = (numOfCC) * 8;
        else if (!wc_status && c_status)
            price = (numOfCC) * 7;
        else if (wc_status && !c_status)
            price = (numOfCC) * 6;
        else
            price = (numOfCC) * 5;

        quantityTextView.setText("" + numOfCC);
        priceText = getString(R.string.total,NumberFormat.getCurrencyInstance().format(price));
    }
}