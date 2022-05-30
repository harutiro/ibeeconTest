package io.github.com.harutiro.ibeecontest

import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.altbeacon.beacon.*
import java.util.*


class input2Activity : AppCompatActivity(), BeaconConsumer {
    private val TAG: String = "input2Activity"


    //フォーマット　こういうモノ
    private val IBEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"

    private var beaconManager //ビーコンのモニタリングを制御する人
            : BeaconManager? = null
    private var mRegion //監視範囲の定義
            : Region? = null

    private val datas //ビーコンの情報を格納するList
            : ArrayList<DataSet>? = null


    //OnCreate() OnPause()　などは「Android　ライフサイクル」で調べて。
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input2) //Viewのセット


        //ビーコン受信のための設定
        mRegion = Region("iBeacon", null, null, null)
        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager!!.beaconParsers.add(BeaconParser().setBeaconLayout(IBEACON_FORMAT))

        //minorでソート

        datas?.add(DataSet("ef43f8dc-be95-4e78-850f-d9172bbadf76","1","12345"))

        //minorでソート
        datas?.sortWith(Comparator { p0, p1 -> p0?.minor?.toInt()!! - p1?.minor?.toInt()!! })


        //スタートボタンを押した時の動作定義
        findViewById<Button>(R.id.button4).setOnClickListener { //開始時間取得


            //レンジングの開始
            try {
                //スキャン間隔の設定
                beaconManager!!.foregroundScanPeriod = 1000L //スキャン間隔を設定
                beaconManager!!.startRangingBeaconsInRegion(mRegion!!) //監視の開始
            } catch (re: RemoteException) {
            }

        }


//        //ストップボタンを押した時の動作定義
//        button_stop.setOnClickListener {
//            try {
//                //レンジングの停止
//                beaconManager!!.stopRangingBeaconsInRegion(mRegion!!)
//            } catch (re: RemoteException) {
//            }
//        }
    }


    override fun onResume() {
        super.onResume()
        // サービスの開始
        beaconManager!!.bind(this)
    }

    override fun onPause() {
        super.onPause()
        // サービスの停止
        beaconManager!!.unbind(this)
    }


    //ビーコンの監視を初めた時
    override fun onBeaconServiceConnect() {
        beaconManager!!.addMonitorNotifier(object : MonitorNotifier {
            override fun didEnterRegion(region: Region) {
                // 領域侵入
                Log.d("MYE", "Enter")
            }

            override fun didExitRegion(region: Region) {
                // 領域退出
                Log.d("MYE", "EXit")
            }

            override fun didDetermineStateForRegion(i: Int, region: Region) {
                // 領域に対する状態が変化
                Log.d("MYE", "didDetermineStateForRegion")
            }
        })

        
        //スキャン間隔ごとにビーコンを検知したら呼ばれる　
        beaconManager!!.addRangeNotifier { beacons, region ->

            Log.d(TAG, beacons.size.toString())
            Log.d(TAG, beacons.toString())
        }
    }





}