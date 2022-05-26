package io.github.com.harutiro.ibeecontest

import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseSettings
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.BeaconTransmitter


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val beacon = Beacon.Builder()
            .setId1("780106b8-c7e3-4bc1-84b0-3ba1686726e8")
            .setId2("1")
            .setId3("56562")
            .setManufacturer(0x004C)
            .build()
        val beaconParser = BeaconParser()
            .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")
        val beaconTransmitter = BeaconTransmitter(applicationContext, beaconParser)



        beaconTransmitter.startAdvertising(beacon, object : AdvertiseCallback() {
            override fun onStartSuccess(settingsInEffect: AdvertiseSettings) {
                super.onStartSuccess(settingsInEffect)
                //成功
                Log.d("debag","OK")
            }

            override fun onStartFailure(errorCode: Int) {
                //失敗
                Log.d("debag","NO")
            }
        })

        findViewById<Button>(R.id.button).setOnClickListener {
            Log.d("debag",beaconTransmitter.isStarted.toString())
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            beaconTransmitter.stopAdvertising()
        }

        findViewById<Button>(R.id.button3).setOnClickListener {
            //送信開始
            beaconTransmitter.startAdvertising(beacon)
        }

    }


//    //iBeacon認識のためのフォーマット設定
//    private val IBEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // TODO: Add code here to obtain location permission from user
//        // TODO: Add beaconParsers for any properietry beacon formats you wish to detect
//
//        val beaconManager =  BeaconManager.getInstanceForApplication(this)
//        val region = Region("iBeacon", null, null, null)
//        // Set up a Live Data observer so this Activity can get ranging callbacks
//        // observer will be called each time the monitored regionState changes (inside vs. outside region)
//        beaconManager.getRegionViewModel(region).rangedBeacons.observe(this, rangingObserver)
//        beaconManager.startRangingBeacons(region)
//        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout(IBEACON_FORMAT))
//    }
//
//    val rangingObserver = Observer<Collection<Beacon>> { beacons ->
//        Log.d(TAG, "Ranged: ${beacons.count()} beacons")
//        for (beacon: Beacon in beacons) {
//            Log.d(TAG, "$beacon about ${beacon.distance} meters away")
//        }
//    }



//    //iBeacon認識のためのフォーマット設定
//    private val IBEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"
//
//    private var beaconManager: BeaconManager? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        beaconManager = BeaconManager.getInstanceForApplication(this)
//        beaconManager!!.beaconParsers.add(BeaconParser().setBeaconLayout(IBEACON_FORMAT))
//    }

//    override fun onResume() {
//        super.onResume()
//        beaconManager!!.bind(this)
//        //サービス開始
//    }
//
//    override fun onPause() {
//        super.onPause()
//        beaconManager!!.unbind(this)
//        //サービス終了
//    }
//
//    override fun onBeaconServiceConnect() {
//        val mRegion = Region("iBeacon", null, null, null)
//        beaconManager!!.addMonitorNotifier(object : MonitorNotifier {
//            override fun didEnterRegion(region: Region?) {
//                //領域への入場を検知
//                Log.d("iBeacon", "Enter Region")
//            }
//
//            override fun didExitRegion(region: Region?) {
//                //領域からの退場を検知
//                Log.d("iBeacon", "Exit Region")
//            }
//
//            override fun didDetermineStateForRegion(i: Int, region: Region?) {
//                //領域への入退場のステータス変化を検知
//                Log.d("MyActivity", "Determine State$i")
//
//            }
//        })
//
//        try {
//            //Beacon情報の監視を開始
//            beaconManager!!.startMonitoringBeaconsInRegion(mRegion)
//        } catch (e: RemoteException) {
//            e.printStackTrace()
//        }
//    }


}