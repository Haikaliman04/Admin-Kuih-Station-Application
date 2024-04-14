package com.example.adminkuihstationapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    //declare  to connect with database
    private lateinit var dbRef: DatabaseReference

    //initialize all component
    private lateinit var signupButton: Button
    private lateinit var haveaccButton: TextView
    private lateinit var nameAdmin: EditText
    private lateinit var passwordAdmin : EditText
    private lateinit var emailAdmin: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //declare all component
        signupButton = findViewById(R.id.signupBtnAdmin)
        haveaccButton = findViewById(R.id.signInbuttonAdmin)
        nameAdmin = findViewById(R.id.eTNameAdmin)
        passwordAdmin = findViewById(R.id.eTPasswordAdmin)
        emailAdmin = findViewById(R.id.eTEmailAdmin)

        signupButton.setOnClickListener {
            val nameText = nameAdmin.text.toString().trim()
            val emailText = emailAdmin.text.toString().trim()
            val passwordText = passwordAdmin.text.toString().trim()

            if (nameText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(this@SignUpActivity, "Please fill all information", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Call function saveEmployeeData
            // Parameters - change the input data to string
            saveData(emailText, nameText, passwordText)
        }

        haveaccButton.setOnClickListener{
            val i = Intent (this, LoginActivity::class.java)
            startActivity(i)
        }

    }

    //create the function saveData
    // this function send data to firebase
    // n - name
    //p - password
    //e - email
    private fun saveData(e:String, n:String, p:String) {
        //getInstance = get object
        //customer refer to table
        //Customer can change to other name
        //link database named admin
        dbRef = FirebaseDatabase.getInstance().getReference("Admin")

        //produce auto generate customer id
        //!! refer to must had record id or id cannot null
        val adminId = dbRef.push().key!!

        //push the data to database
        //adminId will autogenerate
        //data will output by user
        //input name, password, phone, email
        val em = Model(e, adminId, n, p)

        //setting to push data inside table
        dbRef.child(adminId).setValue(em)

            //success record
            .addOnCompleteListener {
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                //fail
            }.addOnFailureListener{
                Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show()

            }

        //declare variable i
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)

    }
}