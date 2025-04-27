
package com.example.onlinecourseproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CheckoutActivity extends Activity {

    ListView checkoutListView;
    Button proceedToPaymentButton;
    // Use explicit type for ArrayList
    static ArrayList<CartActivity.CourseItem> checkoutItems = new ArrayList<CartActivity.CourseItem>(); // Explicit type specified
    CheckoutAdapter adapter;
    TextView totalPriceTextView;
    static double totalPrice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        checkoutListView = (ListView) findViewById(R.id.checkout_list);
        proceedToPaymentButton = (Button) findViewById(R.id.proceed_to_payment_button);
        totalPriceTextView = (TextView) findViewById(R.id.total_price);

        adapter = new CheckoutAdapter();
        checkoutListView.setAdapter(adapter);

        // Calculate total price
        calculateTotalPrice();

        // Display total price
        totalPriceTextView.setText("Total: $" + totalPrice);

        // Proceed to Payment button click
     // Proceed to Payment button click
        proceedToPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkoutItems.isEmpty()) {
                    Toast.makeText(CheckoutActivity.this, "No items to checkout!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(CheckoutActivity.this, PaymentActivity.class);
                    intent.putExtra("TOTAL_AMOUNT", totalPrice);
                    
                    // Pass purchased courses
                    ArrayList<String> purchasedCourses = new ArrayList<String>();
                    for (CartActivity.CourseItem item : checkoutItems) {
                        purchasedCourses.add(item.getCourseName());
                    }
                    intent.putStringArrayListExtra("PURCHASED_COURSES", purchasedCourses);

                    startActivity(intent);
                }
            }
        });


    }

    // Add course to checkout
    public static void addToCheckout(String courseName, int courseImageRes) {
        checkoutItems.add(new CartActivity.CourseItem(courseName, courseImageRes));
    }

    // Calculate total price for checkout
    private void calculateTotalPrice() {
        totalPrice = 0.0;
        for (CartActivity.CourseItem item : checkoutItems) {
            // Assuming each course costs $20 (you can change the pricing model)
            totalPrice += 500.0; // Replace with actual price logic
        }
    }

    class CheckoutAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return checkoutItems.size();
        }

        @Override
        public Object getItem(int position) {
            return checkoutItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.cart_item_layout, parent, false);
            }

            ImageView courseImage = (ImageView) convertView.findViewById(R.id.cart_course_image);
            TextView courseTitle = (TextView) convertView.findViewById(R.id.cart_course_title);
            Button removeButton = (Button) convertView.findViewById(R.id.remove_button); // Add remove button

            final CartActivity.CourseItem courseItem = checkoutItems.get(position);
            courseImage.setImageResource(courseItem.getImageResId());
            courseTitle.setText(courseItem.getCourseName());

            // Remove item from checkout when button is clicked
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkoutItems.remove(position);
                    notifyDataSetChanged();
                }
            });

            return convertView;
        }

    }
}

