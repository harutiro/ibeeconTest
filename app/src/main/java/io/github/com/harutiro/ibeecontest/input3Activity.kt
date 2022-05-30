package io.github.com.harutiro.ibeecontest

import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.altbeacon.beacon.*


class input3Activity : AppCompatActivity(), BeaconConsumer {

    private val TAG: String = "input3Activity"

    private val BEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"
    private val UUID = "48534442-4C45-4144-80C0-1800FFFFFFFF"

    private var beaconManager: BeaconManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input3)
        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager!!.beaconParsers.add(
            BeaconParser().setBeaconLayout(BEACON_FORMAT)
        )
    }

    override fun onResume() {
        super.onResume()
        beaconManager!!.bind(this)
    }

    override fun onPause() {
        super.onPause()
        beaconManager!!.unbind(this)
    }

    override fun onBeaconServiceConnect() {
        // 初期化。これがないと画面を２回以上開いた時に２重でデータを受信してしまう
        beaconManager?.removeAllMonitorNotifiers()
        beaconManager?.removeAllRangeNotifiers()
        beaconManager?.rangedRegions?.forEach {region ->
            beaconManager?.stopRangingBeaconsInRegion(region)
        }

        val uuid: Identifier = Identifier.parse(UUID)
        val mRegion = Region("beacon-test", uuid, null, null)
        beaconManager!!.addMonitorNotifier(object : MonitorNotifier {
            override fun didEnterRegion(region: Region?) {
                Log.d("beacon", "入(EnterRegion)")
            }

            override fun didExitRegion(region: Region?) {
                Log.d("beacon", "出(ExitRegion)")
            }

            override fun didDetermineStateForRegion(i: Int, region: Region?) {
                Log.d("beacon", "変(DetermineStateForRegion)")
            }
        })
        try {
            beaconManager!!.foregroundScanPeriod = 1000L //スキャン間隔を設定
            beaconManager!!.startMonitoringBeaconsInRegion(mRegion)
        } catch (ex: RemoteException) {
            Log.e("beacon", "error", ex)
        }

        //スキャン間隔ごとにビーコンを検知したら呼ばれる　
        beaconManager!!.addRangeNotifier { beacons, region ->
            Log.d(TAG, beacons.size.toString())
            Log.d(TAG, beacons.toString())
        }
    }
}