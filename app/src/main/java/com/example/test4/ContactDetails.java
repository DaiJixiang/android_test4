package com.example.test4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
        final String phoneNumber = bundle.getString("phoneNumber");

        TextView tv_name = this.findViewById(R.id.detailsName);
        TextView tv_phoneNumber = this.findViewById(R.id.detailsPhoneNumber);
        tv_name.setText(name);
        tv_phoneNumber.setText(phoneNumber);

        ImageView image_message = this.findViewById(R.id.detailsMessage);
        ImageView image_phone = this.findViewById(R.id.detailsPhone);
        image_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPhone = new Intent(Intent.ACTION_DIAL);
                intentPhone.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intentPhone);
            }
        });

        image_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMessage = new Intent(Intent.ACTION_SENDTO);
                intentMessage.setData(Uri.parse("smsto:" + phoneNumber));
                startActivity(intentMessage);
            }
        });

    }
}
