package com.example.adminkuihstationapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminkuihstationapplication.adapter.OrderDetailsAdapter
import com.example.adminkuihstationapplication.databinding.ActivityOrderDetailsBinding
import com.example.adminkuihstationapplication.model.OrderDetails

class OrderDetailsActivity : AppCompatActivity() {
    private val binding: ActivityOrderDetailsBinding by lazy{
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }

    private var customerName :String?=null
    private var address : String?=null
    private var phoneNumber : String?=null
    private var date : String?=null
    private var totalPrice : String?=null
    private  var foodNames : ArrayList<String> = arrayListOf()
    private  var foodImages : ArrayList<String> = arrayListOf()
    private  var foodQuantity : ArrayList<Int> = arrayListOf()
    private  var foodPrices : ArrayList<String> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imageBtn.setOnClickListener {
            finish()
        }
        getDataFromIntent()

    }

    private fun getDataFromIntent() {
        val receivedOrderDetails = intent.getSerializableExtra("CustomerOrderDetails") as OrderDetails
        receivedOrderDetails?.let{orderDetails ->
                customerName = receivedOrderDetails.customerName
                foodNames = receivedOrderDetails.foodNames as ArrayList<String>
                foodImages = receivedOrderDetails.foodImages as ArrayList<String>
                foodQuantity = receivedOrderDetails.foodQuantities as ArrayList<Int>
                address = receivedOrderDetails.address
                phoneNumber = receivedOrderDetails.phoneNumber
                date = receivedOrderDetails.customerDate
                foodPrices = receivedOrderDetails.foodPrices as ArrayList<String>
                totalPrice = receivedOrderDetails.totalPrice

                setCustomerDetails()
                setAdapter()

        }
    }

    private fun setAdapter() {
        binding.orderDetailRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = OrderDetailsAdapter(this, foodNames,foodImages,foodQuantity,foodPrices)
        binding.orderDetailRecyclerView.adapter = adapter
    }

    private fun setCustomerDetails() {
        binding.name.text = customerName
        binding.address.text = address
        binding.phone.text = phoneNumber
        binding.date.text = date
        binding.totalPay.text = totalPrice


    }
}