@file:Suppress("DEPRECATION")

package com.example.admingluttony

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.admingluttony.Models.UserModel
import com.example.admingluttony.R.string.default_web_client_id
import com.example.admingluttony.databinding.ActivityLogInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class LogInActivity() : AppCompatActivity() {
    private var name = "Unknown"
    private var restaurantName = "Unknown"
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(default_web_client_id))
            .requestEmail()
            .build()
        //Initializing Firebase auth, database and GoogleAuthentication
        auth = Firebase.auth
        database = Firebase.database.reference
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        binding.LogInButton.setOnClickListener() {
            //Getting the data from edit texts
            email = binding.LogInEmailEditText.text.toString().trim()
            password = binding.LogInPasswordEditText.text.toString().trim()
            //Toast will be shown is any edit text is blank
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Fill The Fields!", Toast.LENGTH_SHORT).show()
            } else {
                // create user or login
                createUserAccount(email, password)
            }
        }
        binding.GoogleButton.setOnClickListener(){
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }
        binding.DoNotHaveAccountButton.setOnClickListener() {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
    //create user function body
    private fun createUserAccount(email: String, password: String) {
        //if user exists then login and go to the main activity
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                Toast.makeText(this, "Login Successfully!", Toast.LENGTH_SHORT).show()
                updateUi(user)
            } else {
                //if user does not exists then create user first save it's data and then loging in and go to main activity
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(this, "Create Account & Login Successfully!", Toast.LENGTH_SHORT).show()
                        saveUserData()
                        updateUi(user)
                    } else {
                        Toast.makeText(this, "Authentication Failed! ", Toast.LENGTH_SHORT).show()
                        Log.d(
                            "Account",
                            "createUserAccount : Authentication Failed",
                            task.exception
                        )
                    }
                }
            }
        }
    }
    //save data to realtime database
    private fun saveUserData() {
        email = binding.LogInEmailEditText.text.toString().trim()
        password = binding.LogInPasswordEditText.text.toString().trim()
        val user = UserModel(name.toString(), restaurantName.toString(), email, password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        database.child("user").child(userId).setValue(user)
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if(result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if(task.isSuccessful){
                val account: GoogleSignInAccount = task.result
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener{
                    authTask->
                    if(authTask.isSuccessful){
                        Toast.makeText(this,"Successfully Sign_In With Google", Toast.LENGTH_SHORT).show()
                       updateUi(authTask.result?.user)
                        finish()
                    }
                    else{
                        Toast.makeText(this, "Google Sign_In Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        else {
            Toast.makeText(this, "Google Sign_In Failed", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
  // update ui
    private fun updateUi(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}