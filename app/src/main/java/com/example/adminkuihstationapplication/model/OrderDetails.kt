package com.example.adminkuihstationapplication.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class OrderDetails() :Serializable {
    var customerId:String?=null
    var customerName:String?=null
    var foodNames:MutableList<String>?=null
    var foodImages:MutableList<String>?=null
    var foodPrices:MutableList<String>?=null
    var foodQuantities:MutableList<Int>?=null
    var address:String?=null
    var totalPrice:String?=null
    var phoneNumber:String?=null
    var customerDate:String?=null
    var orderAccepted:Boolean=false
    var paymentReceived:Boolean=false
    var itemPushKey:String?=null
    var currentTime:Long=0

    constructor(parcel: Parcel) : this() {
        customerId = parcel.readString()
        customerName = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        customerDate = parcel.readString()
        orderAccepted = parcel.readByte() != 0.toByte()
        paymentReceived = parcel.readByte() != 0.toByte()
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }

     fun describeContents(): Int {
        TODO("Not yet implemented")
    }

     fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }
}