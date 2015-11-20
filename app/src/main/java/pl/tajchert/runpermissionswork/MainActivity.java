package pl.tajchert.runpermissionswork;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;
import pl.tajchert.nammu.PermissionListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE_CALL = 32;

    @Bind(R.id.main_layout)
    View mLayout;//We will use it later

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Nammu.init(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Nammu.permissionCompare(new PermissionListener() {
            @Override
            public void permissionsChanged(String s) {
                //Called each time when some permission changes
                Log.d(TAG, "permissionsChanged: ");
            }

            @Override
            public void permissionsGranted(String s) {
                //New permission was granted to our app
                Log.d(TAG, "permissionsGranted: ");
            }

            @Override
            public void permissionsRemoved(String s) {
                //Most useful - we lost some permission, maybe check what it was and handle it?
                Log.e(TAG, "permissionsRemoved: ");
            }
        });
    }

    @OnClick(R.id.buttonCall)
    public void clickButtCall() {
        if(!Nammu.checkPermission(Manifest.permission.CALL_PHONE)) {
            Log.e(TAG, "We dont have permission for calling");
            Nammu.askForPermission(this, Manifest.permission.CALL_PHONE, permissionCallbackCallPhone);
        } else {
            callTest();
        }
    }

    private void callTest() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + "11122233"));
        try {
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    PermissionCallback permissionCallbackCallPhone = new PermissionCallback() {
        @Override
        public void permissionGranted() {
            callTest();
        }

        @Override
        public void permissionRefused() {
            Toast.makeText(MainActivity.this, "No permission, aboard", Toast.LENGTH_SHORT).show();
        }
    };
}
