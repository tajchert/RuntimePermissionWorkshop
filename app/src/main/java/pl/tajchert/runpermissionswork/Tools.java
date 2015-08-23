package pl.tajchert.runpermissionswork;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by Tajchert on 23.08.2015.
 */
public class Tools {
    public static boolean accessLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String bestProvider = locationManager.getBestProvider(criteria, false);
        if (bestProvider == null) {
            //No android.permission-group.LOCATION
            return false;
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if(location == null) {
            //it is either off or we lack of permission
            return false;
        }
        return true;
    }
}
