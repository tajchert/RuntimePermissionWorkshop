package pl.tajchert.runpermissionswork;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_LOCATION = 124;

    @Bind(R.id.main_layout)
    View mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonLocation)
    public void clickButtLocation() {
        //We check if we have permission
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //We have! Yay!
            //Lets see if getting location will as well show result
            boolean hasAccess = Tools.accessLocation(this);
            Toast.makeText(this, "Access location = " + hasAccess, Toast.LENGTH_SHORT).show();
        } else {
            //We don't, lets ask for it. Result will be thrown in onRequestPermissionsResult()
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //User already refused to give us this permission or removed it
                //Now he/she can mark "never ask again" (sic!), so we better explain why we need it
                Snackbar.make(mLayout, "Here we explain to user why we need to know location",
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
                            }
                        })
                        .show();
            } else {
                //First time asking for permission
                // or phone doesn't offer permission
                // or user marked "never ask again"
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //Called when user presses "Allow" or "Deny" in permission request, array of permissions
        // and result is there as those request can be merged together - and then one result is
        // thrown for whole batch.
        boolean hasAccess;
        switch ( requestCode ) {
            case(REQUEST_CODE_LOCATION):
                hasAccess = Tools.accessLocation(this);
                Toast.makeText(this, "Access location = " + hasAccess, Toast.LENGTH_SHORT).show();
                break;
            //Here goes other permissions, for example if you ask for Phone call permission, here handle as well result
        }
    }
}
