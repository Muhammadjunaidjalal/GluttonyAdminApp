package com.example.admingluttony

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.admingluttony.Models.UserModel
import com.example.admingluttony.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignUpActivity : AppCompatActivity() {

    //Necessory Variables
    private lateinit var ownerName :String
    private lateinit var restaurantName :String
    private lateinit var email : String
    private lateinit var password: String
    private lateinit var auth : FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Initializing Firebae auth and database veriables
        auth = Firebase.auth
        database = Firebase.database.reference

        binding.CreateAccountButton.setOnClickListener(){

            //initializing data variables with data of edit text
            ownerName = binding.OwnerNameEditText.text.toString().trim()
            restaurantName = binding.RestaurantNameEditText.text.toString().trim()
            email = binding.SignUpEmailEditText.text.toString().trim()
            password = binding.SignUpPasswordEditText.text.toString().trim()

            //Checking if any edit text is empty or not
            if(ownerName.isBlank()|| restaurantName.isBlank()|| email.isBlank() || password.isBlank()){
                Toast.makeText(this, "Please Fill All Details!", Toast.LENGTH_SHORT).show()
            }
            else{
                createAccount(email,password)
            }
        }

        binding.AlreadyHaveAccountTextView.setOnClickListener(){
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }


        val locationList = arrayOf("Lahore","Islamabad", "Karachi","Faislabad", "Rawalpindi")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        val autoCompleteTextView = binding.ChooseLocationTextView
        autoCompleteTextView.setAdapter(adapter)

    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{task->
            if(task.isSuccessful){
                Toast.makeText(this, "Account Is Created!", Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this, LogInActivity::class.java)
                startActivity(intent)
                finish()

            }
            else{
                Toast.makeText(this, "Account Creation is Failed!", Toast.LENGTH_SHORT).show()
                Log.d("Account","createAccount: Failure", task.exception)
            }
        }
    }

    //To save Data into the Database
    private fun saveUserData() {

        binding.apply {
            ownerName = OwnerNameEditText.text.toString().trim()
            restaurantName = RestaurantNameEditText.text.toString().trim()
            email = SignUpEmailEditText.text.toString().trim()
            password = SignUpPasswordEditText.text.toString().trim()
            val user = UserModel(ownerName,restaurantName,email,password)
            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            //Save Data into Firebase Database
            database.child("user").child(userId).setValue(user)
        }
    }
}