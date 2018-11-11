package com.z.newsleak.features.about_info;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import com.z.newsleak.R;
import com.z.newsleak.model.SocialNetworks;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private RelativeLayout rlBase;

    public static void start(@NonNull Context context) {
        final Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        rlBase = findViewById(R.id.about_rl_root);
        addDisclaimer();

        final EditText messageEdit = findViewById(R.id.about_et_message);
        final Button emailBtn = findViewById(R.id.about_btn_email);
        emailBtn.setOnClickListener(btn -> {

            final String messageString = messageEdit.getText().toString();
            if (messageString.isEmpty()) {
                Snackbar.make(rlBase, R.string.warning_empty_message, Snackbar.LENGTH_SHORT).show();
                return;
            }
            openEmailApp(messageString);
        });

        final ImageView vkLogo = findViewById(R.id.about_iv_vk_logo);
        vkLogo.setOnClickListener(iv -> openSocialNetwork(SocialNetworks.VK));

        final ImageView tmLogo = findViewById(R.id.about_iv_telegram_logo);
        tmLogo.setOnClickListener(iv -> openSocialNetwork(SocialNetworks.TELEGRAM));

        final ImageView gitLogo = findViewById(R.id.about_iv_github_logo);
        gitLogo.setOnClickListener(iv -> openSocialNetwork(SocialNetworks.GITHUB));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void addDisclaimer() {

        final RelativeLayout myLayout = findViewById(R.id.rl_content);
        final int margin = getResources().getDimensionPixelOffset(R.dimen.space_half_size);
        final int size = getResources().getDimensionPixelOffset(R.dimen.disclaimer_size);

        final TextView tv = new TextView(this);
        tv.setText(R.string.disclaimer);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        tv.setTextColor(ContextCompat.getColor(this, R.color.colorCorporate));

        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, // width
                ViewGroup.LayoutParams.WRAP_CONTENT); // height

        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.setMargins(margin, margin, margin, margin);
        tv.setLayoutParams(layoutParams);

        myLayout.addView(tv);

    }

    private void openEmailApp(@NonNull String messageString) {

        final String mailto = "mailto:" + getString(R.string.email_address) + "?cc=" +
                "&subject=" + Uri.encode(getString(R.string.email_subject)) +
                "&body=" + Uri.encode(messageString);

        final Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse(mailto));

        // Check if the system can handle this type of intent or startActivity will crash otherwise.
        if (intent.resolveActivity(getPackageManager()) == null) {
            Snackbar.make(rlBase, R.string.error_no_email_app, Snackbar.LENGTH_SHORT).show();
            return;
        }
        startActivity(intent);
    }

    private void openBrowser(@NonNull String url) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        // Check if the system can handle this type of intent or startActivity will crash otherwise.
        if (intent.resolveActivity(getPackageManager()) == null) {
            Snackbar.make(rlBase, R.string.error_no_browser_app, Snackbar.LENGTH_SHORT).show();
            return;
        }
        startActivity(intent);
    }

    private boolean openSpecificApp(@NonNull String stringUrl, @NonNull String appPackage) {

        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(stringUrl));
        intent.setPackage(appPackage);

        if (intent.resolveActivity(getPackageManager()) == null) {
            return false;
        }

        startActivity(intent);
        return true;
    }

    private void openSocialNetwork(@NonNull SocialNetworks socialNetwork) {

        final String[] appPackages = socialNetwork.getAppPackages();
        final String url = socialNetwork.getUrl();
        if (appPackages == null) {
            openBrowser(url);
            return;
        }

        for (String appPackage : appPackages) {
            if (openSpecificApp(url, appPackage)) {
                return;
            }
        }
        openBrowser(url);
    }

}
