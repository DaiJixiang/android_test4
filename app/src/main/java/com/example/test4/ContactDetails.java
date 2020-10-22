package com.example.test4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ContactDetails extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        Intent detailsIntent = getIntent();
        Bundle bundle = detailsIntent.getBundleExtra("userInfo");
        assert bundle != null;
        String name = bundle.getString("userName");
        String phoneNumber = bundle.getString("phoneNumber");

        TextView tv_name = this.findViewById(R.id.detailsName);
        TextView tv_phoneNumber = this.findViewById(R.id.detailsPhoneNumber);
        tv_name.setText(name);
        tv_phoneNumber.setText(phoneNumber);

    }
}
