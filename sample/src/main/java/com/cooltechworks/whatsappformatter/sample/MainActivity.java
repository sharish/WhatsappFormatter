package com.cooltechworks.whatsappformatter.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cooltechworks.views.WhatsappViewCompat;

public class MainActivity extends AppCompatActivity {

    LinearLayout mContainer;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }
        mContainer = (LinearLayout) findViewById(R.id.container);
        mEditText = (EditText) findViewById(R.id.whatsapp_edit_view);



//        TextWatcher watcher = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.d("MainActivity", "beforeTextChanged Called");
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d("MainActivity", "onTextChanged Called");
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                Log.d("MainActivity", "afterTextChanged Called");
//            }
//        };

        WhatsappViewCompat.applyFormatting(mEditText);

        findViewById(R.id.send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView();
            }
        });
    }

    private void addView() {

        String text = mEditText.getText().toString();

        TextView textView = new TextView(this);

        textView.setBackgroundResource(R.drawable.ic_whatsapp_chathead);
        textView.setText(text);
        textView.setPadding(10, 10, 40, 10);
        mContainer.addView(textView);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
        layoutParams.gravity = Gravity.END;
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.topMargin = layoutParams.rightMargin = layoutParams.bottomMargin = layoutParams.leftMargin = 20;
        textView.setGravity(Gravity.START | Gravity.CENTER);
        textView.setLayoutParams(layoutParams);

        WhatsappViewCompat.applyFormatting(textView);

        mEditText.setText("");
    }

}
