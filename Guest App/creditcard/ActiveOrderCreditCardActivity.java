package com.aniqma.lokaventour.modul.activity.booking.creditcard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.helper.date.ExpiryDateTextWatcherHelper;

import java.util.Calendar;

public class ActiveOrderCreditCardActivity extends AppCompatActivity {

    private TextView title, btn_save;
    private ImageView back_button;
    private EditText etCardNumber, etCardHolderName, etExpDate, etCvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_order_credit_card);

        View toolbar = findViewById(R.id.toolbar);
        title = toolbar.findViewById(R.id.tvTitleToolbar);
        btn_save = toolbar.findViewById(R.id.tvSave);
        back_button = toolbar.findViewById(R.id.ivBackTour);

        title.setText(getString(R.string.text_title_credit_card));

        btn_save.setVisibility(View.GONE);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etCardNumber = findViewById(R.id.etCardNumber);
        etCardHolderName = findViewById(R.id.etCardHolderName);
        etExpDate = findViewById(R.id.etExpDate);
        etCvv = findViewById(R.id.etCvv);

        etExpDate.addTextChangedListener(new ExpiryDateTextWatcherHelper(ActiveOrderCreditCardActivity.this, etExpDate));
    }

    private TextWatcher mDateEntryWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String working = s.toString();
            boolean isValid = true;
            if (working.length()==2 && before ==0) {
                if (Integer.parseInt(working) < 1 || Integer.parseInt(working)>12) {
                    isValid = false;
                } else {
                    working+="/";
                    etExpDate.setText(working);
                    etExpDate.setSelection(working.length());
                }
            }
            else if (working.length()==7 && before ==0) {
                String enteredYear = working.substring(3);
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                if (Integer.parseInt(enteredYear) < currentYear) {
                    isValid = false;
                }
            } else if (working.length()!=7) {
                isValid = false;
            }

            if (!isValid) {
                etExpDate.setError("Enter a valid date: MM/YYYY");
            } else {
                etExpDate.setError(null);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    };

    @Override
    public void onBackPressed() {
        finish();
    }
}
