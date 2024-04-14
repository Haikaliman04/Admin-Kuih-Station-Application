package com.example.adminkuihstationapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    //initialize all component
    private lateinit var loginButton: Button
    private lateinit var donthaveacc: TextView
    private lateinit var email: EditText
    private lateinit var password: EditText

    //declare firebase
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //declare all component
        loginButton = findViewById(R.id.loginButtonAdmin)
        donthaveacc = findViewById(R.id.signUpAdmin)
        email = findViewById(R.id.emailLoginAdmin)
        password = findViewById(R.id.passwordLoginAdmin)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("Admin")

        //function to SignUpActivity
        donthaveacc.setOnClickListener{
            val i = Intent (this, SignUpActivity::class.java)
            startActivity(i)
        }

        loginButton.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                login(email, password)
            }else {
                Toast.makeText(this@LoginActivity, "All fields are mandatory", Toast.LENGTH_LONG).show()
            }
        }

    }

    //create the function login
    //this function read data to firebase
    //p - password
    //e - email
    private fun login(email:String, password:String)
    {
        databaseReference.orderByChild("adminEmail").equalTo(email).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (adminSnapshot in dataSnapshot.children) {
                        val model = adminSnapshot.getValue(Model::class.java)

                        if (model != null && model.adminPassword == password){
                            Toast.makeText(this@LoginActivity, "Login Successfully", Toast.LENGTH_LONG).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                            return
                        }
                    }
                }

                Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_LONG).show()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@LoginActivity, "Database Error: ${databaseError.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}