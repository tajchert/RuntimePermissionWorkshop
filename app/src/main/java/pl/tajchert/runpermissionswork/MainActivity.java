package pl.tajchert.runpermissionswork;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonLocation)
    public void clickButtLocation() {
        //Ok so when targeting API=23 we will not have "out of the box" permission to do stuff.
        //Lets try ask user for it!
        //Try yourself if not move to next commit
        //Useful methods: ActivityCompat.checkSelfPermission(), ActivityCompat.requestPermissions(), and remember to handle result in onRequestPermissionsResult()
        //Good luck!
        boolean hasAccess = Tools.accessLocation(this);
        Toast.makeText(this, "Location access = " + hasAccess, Toast.LENGTH_SHORT).show();
    }
}
