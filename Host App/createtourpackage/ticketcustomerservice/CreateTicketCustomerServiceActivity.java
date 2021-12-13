package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.ticketcustomerservice;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aniqma.lokaventour.host.R;

import java.util.ArrayList;
import java.util.List;

public class CreateTicketCustomerServiceActivity extends AppCompatActivity {

    private TextView title;
    private ImageView back_button;
    private Spinner spinHelp;
    private EditText etMessage;
    private Button btnCreateTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ticket_customer_service);

        View toolbar = findViewById(R.id.toolbar);
        title = toolbar.findViewById(R.id.titleToolbar);
        back_button = toolbar.findViewById(R.id.imgBackButton);

        title.setText(getString(R.string.text_create_ticket));

        back_button.setImageResource(R.drawable.ic_close);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnCreateTicket = findViewById(R.id.btnCreateTicket);

        btnCreateTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addItemHelp();
    }

    private void addItemHelp() {

        spinHelp = findViewById(R.id.spinHelp);
        List<String> list = new ArrayList<String>();
        list.add("Payment");
        list.add("Tours");
        list.add("Withdrawing");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinHelp.setAdapter(dataAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
