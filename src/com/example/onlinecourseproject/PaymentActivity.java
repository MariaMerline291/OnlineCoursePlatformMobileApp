package com.example.onlinecourseproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class PaymentActivity extends Activity {

    TextView totalAmountTextView;
    EditText cardNumber, expiryDate, cvv, cardHolderName;
    Button payButton;
    TextView errorCardNumber, errorExpiryDate, errorCvv, errorCardHolder; // Error message TextViews

    ArrayList<String> purchasedCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        
        // Retrieve the list of purchased courses
        purchasedCourses = getIntent().getStringArrayListExtra("PURCHASED_COURSES");

        // Display purchased courses in UI (e.g., a ListView or RecyclerView)
        displayPurchasedCourses();
    

        // Get total amount from CheckoutActivity
        double totalAmount = getIntent().getDoubleExtra("TOTAL_AMOUNT", 0.0); // Fix: Get total amount via Intent

        // Initialize UI elements
        totalAmountTextView = (TextView) findViewById(R.id.total_amount);
        cardNumber = (EditText) findViewById(R.id.card_number);
        expiryDate = (EditText) findViewById(R.id.expiry_date);
        cvv = (EditText) findViewById(R.id.cvv);
        cardHolderName = (EditText) findViewById(R.id.card_holder_name);
        payButton = (Button) findViewById(R.id.pay_button);

        // Error text fields
        errorCardNumber = (TextView) findViewById(R.id.error_card_number);
        errorExpiryDate = (TextView) findViewById(R.id.error_expiry_date);
        errorCvv = (TextView) findViewById(R.id.error_cvv);
        errorCardHolder = (TextView) findViewById(R.id.error_card_holder);

        // Hide error messages initially
        hideErrors();

        // Display total amount
        totalAmountTextView.setText("Total Amount: $" + totalAmount);

        // Pay button click
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatePayment();
            }
        });
    }

    private void validatePayment() {
        boolean isValid = true;

        // Hide all previous error messages
        hideErrors();

        // Get input values
        String cardNum = cardNumber.getText().toString().trim();
        String expiry = expiryDate.getText().toString().trim();
        String cvvCode = cvv.getText().toString().trim();
        String cardHolder = cardHolderName.getText().toString().trim();

        // Validate Card Number (16 digits)
        if (!cardNum.matches("\\d{16}")) {
            errorCardNumber.setText("Invalid card number (must be 16 digits)");
            errorCardNumber.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // Validate Expiry Date (MM/YY format and not expired)
        if (!expiry.matches("(0[1-9]|1[0-2])/[0-9]{2}") || isExpired(expiry)) {
            errorExpiryDate.setText("Invalid expiry date (format: MM/YY, should not be expired)");
            errorExpiryDate.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // Validate CVV (3-digit)
        if (!cvvCode.matches("\\d{3}")) {
            errorCvv.setText("Invalid CVV (must be 3 digits)");
            errorCvv.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // Validate Card Holder Name (No numbers)
        if (!cardHolder.matches("^[A-Za-z ]+$")) {
            errorCardHolder.setText("Invalid name (letters only)");
            errorCardHolder.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // If all validations pass, process payment
        if (isValid) {
            Toast.makeText(PaymentActivity.this, "Payment Successful!", Toast.LENGTH_LONG).show();
            payButton.setEnabled(false);

            // Send purchased courses to MyLearningActivity
            Intent intent = new Intent(PaymentActivity.this, MyLearningActivity.class);
            intent.putStringArrayListExtra("MY_COURSES", purchasedCourses);
            startActivity(intent);
            finish();
        }

    }

    // Check if expiry date is in the past
    private boolean isExpired(String expiry) {
        String[] parts = expiry.split("/");
        int expMonth = Integer.parseInt(parts[0]);
        int expYear = Integer.parseInt(parts[1]) + 2000; // Convert YY to YYYY

        Calendar today = Calendar.getInstance();
        int currentYear = today.get(Calendar.YEAR);
        int currentMonth = today.get(Calendar.MONTH) + 1; // Calendar.MONTH is 0-based

        return (expYear < currentYear) || (expYear == currentYear && expMonth < currentMonth);
    }

    // Hide all error messages
    private void hideErrors() {
        errorCardNumber.setVisibility(View.GONE);
        errorExpiryDate.setVisibility(View.GONE);
        errorCvv.setVisibility(View.GONE);
        errorCardHolder.setVisibility(View.GONE);
    }
    
    private void displayPurchasedCourses() {
        ListView coursesListView = (ListView) findViewById(R.id.purchased_courses_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, purchasedCourses);
        coursesListView.setAdapter(adapter);
    }
}
