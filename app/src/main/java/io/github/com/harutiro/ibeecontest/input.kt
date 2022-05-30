package io.github.com.harutiro.ibeecontest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.Region


class input : AppCompatActivity(){

    private val TAG: String = "HogeActivity"
    // ビーコンマネージャ宣言
    private lateinit var mBeaconManager: BeaconManager
    // iBeaconのデータを認識するためのParserフォーマット
    val IBEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        // TODO: Add code here to obtain location permission from user

        val mRegion = Region("unique-id-001", null, null, null)

        val beaconManager =  BeaconManager.getInstanceForApplication(this)
        // Set up a Live Data observer so this Activity can get ranging callbacks
        // observer will be called each time the monitored regionState changes (inside vs. outside region)
        beaconManager.getRegionViewModel(mRegion).rangedBeacons.observe(this, rangingObserver)
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout(IBEACON_FORMAT))
        beaconManager.startRangingBeacons(mRegion)
    }

    val rangingObserver = Observer<Collection<Beacon>> { beacons ->
        Log.d(TAG, "Ranged: ${beacons.count()} beacons")
        for (beacon: Beacon in beacons) {
            Log.d(TAG, "$beacon about ${beacon.distance} meters away")
        }
    }
}