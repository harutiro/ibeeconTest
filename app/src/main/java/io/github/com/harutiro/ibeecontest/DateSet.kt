package io.github.com.harutiro.ibeecontest

import java.io.Serializable
//BLEビーコン１個のUUID　Major　Minor　のデータを保持する
class DataSet : Serializable {
    //JSONにしたり、Intentで渡せるようにするためにシリアライザブル継承
    private val KEY = "DATASET_KEY"
    var uuid: String? = null
    var major: String? = null
    var minor: String? = null
    var memo: String? = null
    var rssi = 0
    var isExist = false

    constructor() {}
    constructor(uuid: String?, major: String?, minor: String?) {
        this.uuid = uuid
        this.major = major
        this.minor = minor
    }

    constructor(uuid: String?, major: String?, minor: String?, memo: String?) {
        this.uuid = uuid
        this.major = major
        this.minor = minor
        this.memo = memo
    }

    override fun toString(): String {
        return "DataSet{" +
                "KEY='" + KEY + '\'' +
                ", uuid='" + uuid + '\'' +
                ", major='" + major + '\'' +
                ", minor='" + minor + '\'' +
                ", memo='" + memo + '\'' +
                ", rssi=" + rssi +
                ", exist=" + isExist +
                '}'
    }
}