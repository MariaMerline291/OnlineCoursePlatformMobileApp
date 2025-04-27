
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

public class CartActivity extends Activity {

    ListView cartListView;
    Button proceedToPaymentButton;
    static ArrayList<CourseItem> cartItems = new ArrayList<CourseItem>();
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView = (ListView) findViewById(R.id.cart_list);
        proceedToPaymentButton = (Button)findViewById(R.id.proceed_to_payment_button);

        adapter = new CartAdapter();
        cartListView.setAdapter(adapter);

        proceedToPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartItems.isEmpty()) {
                    Toast.makeText(CartActivity.this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    // Clear existing checkout items to prevent duplication
                    CheckoutActivity.checkoutItems.clear();

                    // Add items to checkout list
                    for (CartActivity.CourseItem item : cartItems) {
                        CheckoutActivity.addToCheckout(item.getCourseName(), item.getImageResId());
                    }

                    // Navigate to CheckoutActivity
                    startActivity(new Intent(CartActivity.this, CheckoutActivity.class));
                }
            }
        });


    }

    // Add course to cart
    public static void addToCart(String courseName, int courseImageRes) {
        cartItems.add(new CourseItem(courseName, courseImageRes));
    }

    class CartAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return cartItems.size();
        }

        @Override
        public Object getItem(int position) {
            return cartItems.get(position);
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
            Button removeButton = (Button) convertView.findViewById(R.id.remove_button);

            CourseItem courseItem = cartItems.get(position);
            courseImage.setImageResource(courseItem.getImageResId());
            courseTitle.setText(courseItem.getCourseName());

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartItems.remove(position);
                    notifyDataSetChanged();
                }
            });

            return convertView;
        }
    }

    static class CourseItem {
        private String courseName;
        private int imageResId;

        public CourseItem(String courseName, int imageResId) {
            this.courseName = courseName;
            this.imageResId = imageResId;
        }

        public String getCourseName() {
            return courseName;
        }

        public int getImageResId() {
            return imageResId;
        }
    }
}