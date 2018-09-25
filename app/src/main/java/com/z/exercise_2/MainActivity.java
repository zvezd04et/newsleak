package com.z.exercise_2;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addDisclaimer();

        final EditText messageEdit = findViewById(R.id.et_message);
        final Button emailBtn = findViewById(R.id.btn_email);
        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String messageString = messageEdit.getText().toString();
                if (messageString.isEmpty()){
                    Toast.makeText(MainActivity.this, R.string.warning_empty_message, Toast.LENGTH_LONG).show();
                    return;
                }
                openEmailApp(messageString);
            }
        });

        final ImageView vkLogo = findViewById(R.id.iv_logo_vk);
        vkLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBrowser(getString(R.string.vk_url));
            }
        });

    }

    private void addDisclaimer() {

        RelativeLayout myLayout = findViewById(R.id.rl_content);
        int margin = getResources().getDimensionPixelOffset(R.dimen.space_half_size);
        int size = getResources().getDimensionPixelOffset(R.dimen.disclaimer_size);

        TextView tv = new TextView(this);
        tv.setText(R.string.disclaimer);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        tv.setTextColor(ContextCompat.getColor(this, R.color.colorCorporate));

        RelativeLayout.LayoutParams layoutParams  = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, // width
                ViewGroup.LayoutParams.WRAP_CONTENT); // height

        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.setMargins(margin, margin, margin, margin);
        tv.setLayoutParams(layoutParams );

        myLayout.addView(tv);

    }

    private void openEmailApp(@NonNull String messageString) {
        final Intent intent = new Intent(Intent.ACTION_SENDTO)
                .setData(Uri.parse(String.format("mailto:%s", getString(R.string.email_address))))
                .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
                .putExtra(Intent.EXTRA_TEXT, messageString);

        // Check if the system can handle this type of intent or startActivity will crash otherwise.
        if (intent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(this, R.string.error_no_email_app, Toast.LENGTH_LONG).show();
            return;
        }
        startActivity(intent);
    }

    private void openBrowser(@NonNull String url) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

}
