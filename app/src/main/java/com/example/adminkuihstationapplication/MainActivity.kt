package com.example.adminkuihstationapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminkuihstationapplication.adapter.PendingOrderAdapter
import com.example.adminkuihstationapplication.databinding.ActivityMainBinding
import com.example.adminkuihstationapplication.model.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var database:FirebaseDatabase
    private lateinit var auth : FirebaseAuth
    private lateinit var completedOrderReference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.addMenu.setOnClickListener{
            val intent = Intent(this,AddItemActivity::class.java)
            startActivity(intent)
        }

        binding.allItemMenu.setOnClickListener{
            val intent = Intent(this,AllItemActivity::class.java)
            startActivity(intent)
        }

        binding.deliveryButton.setOnClickListener{
            val intent = Intent(this,DeliveryActivity::class.java)
            startActivity(intent)
        }

        binding.logoutButton.setOnClickListener {
            val  intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }


        binding.pendingOrderTextView.setOnClickListener {
            val intent = Intent(this,PendingOrderActivity::class.java)
            startActivity(intent)
        }

        pendingOrder()

        completedOrders()

        wholeTimeEarning()

    }

    private fun wholeTimeEarning() {
        var totalPay = mutableListOf<Int>()
        completedOrderReference = FirebaseDatabase.getInstance().reference.child("CompletedOrder")
        completedOrderReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot in snapshot.children){
                    var completeOrder = orderSnapshot.getValue(OrderDetails::class.java)

                    completeOrder?.totalPrice?.replace("RM","")?.toIntOrNull()
                        ?.let { i ->
                            totalPay.add(i)
                        }
                }
                binding.wholeTimeEarning.text = "RM" + totalPay.sum().toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun completedOrders() {
        var completedOrderReference = database.reference.child("CompletedOrder")
        var completedOrderItemCount = 0
        completedOrderReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                completedOrderItemCount = snapshot.childrenCount.toInt()
                binding.completeOrders.text = completedOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun pendingOrder() {
        database = FirebaseDatabase.getInstance()

            var pendingOrderReference = database.reference.child("OrderDetails")
            var pendingOrderItemCount = 0
            pendingOrderReference.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    pendingOrderItemCount = snapshot.childrenCount.toInt()
                    binding.pendingOrders.text = pendingOrderItemCount.toString()
                }

                override fun onCancelled(error: DatabaseError) {

                }
        })
    }
}