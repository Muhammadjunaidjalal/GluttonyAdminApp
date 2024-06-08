package com.example.admingluttony

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.admingluttony.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.MenuCardView.setOnClickListener(){

            val intent = Intent(this, AddItemActivity::class.java)

            startActivity(intent)
        }
        binding.AllItemMenuCardView.setOnClickListener(){
            val intent = Intent(this, AllItemActivity::class.java)
            startActivity(intent)
        }
        binding.OrderDispatchCardView.setOnClickListener(){

            val intent = Intent(this, OutForDeliveryActivity::class.java)
            startActivity(intent)
        }
        binding.ProfileCardView.setOnClickListener(){
            val intent = Intent(this, AdminProfileActivity::class.java)
            startActivity(intent)
        }
        binding.NewUserCardView.setOnClickListener(){
            val intent = Intent(this, CreateUserActivity::class.java)
            startActivity(intent)
        }
        binding.PendingOrdersTitleTextView.setOnClickListener(){
            val intent = Intent(this, PendingOrdersActivity::class.java)
            startActivity(intent)
        }
    }
}