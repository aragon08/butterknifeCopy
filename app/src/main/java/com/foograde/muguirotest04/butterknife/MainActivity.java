package com.foograde.muguirotest04.butterknife;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.imvPhoto)
    ImageView imvPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnWeb)
    public void goToPage() {
        Intent intWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.foograde.com"));
        startActivity(intWeb);
    }

    @OnClick(R.id.btnPhone)
    public void makeCall() {
        try {
            Intent intCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:4611776644"));
            startActivity(intCall);
        } catch(SecurityException e) {
            Toast.makeText(this, "Calling not authorized", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnOther)
    public void launchActivity() {
        Intent intActivity = new Intent(getBaseContext(), About.class);
        startActivity(intActivity);
    }

    @OnClick({ R.id.btnSms, R.id.btnMail, R.id.btnPhoto })
    public void doAction(View v) {
        switch (v.getId()) {
            case R.id.btnSms:
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage("4611776644", null, "Ora Cocho!", null, null);
                break;
            case R.id.btnMail:
                String emails[] = { "federico.balderas@foograde.com", "adan.muguiro@foograde.com" };
                Intent intMail = new Intent(Intent.ACTION_SEND);
                intMail.setType("text/plain");
                intMail.putExtra(Intent.EXTRA_SUBJECT, "Que hay");
                intMail.putExtra(Intent.EXTRA_EMAIL, emails);
                intMail.putExtra(Intent.EXTRA_TEXT, "Ora cocho! Mojarras en el río");
                startActivity(intMail);
                break;
            case R.id.btnPhoto:
                Intent intPhoto = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intPhoto, 10);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imvPhoto.setImageBitmap(photo);
            } else {
                Toast.makeText(this, "No photo was taken", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
