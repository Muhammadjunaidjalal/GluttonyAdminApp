package com.example.admingluttony

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admingluttony.Adapters.DeliveryAdapter
import com.example.admingluttony.databinding.ActivityOutForDeliveryBinding

class OutForDeliveryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOutForDeliveryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOutForDeliveryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.BackArrowImageButton.setOnClickListener(){
            finish()
        }

        setAdapterData()
    }

    private fun setAdapterData(){

        val customerNames = arrayOf("Junaid", "Bilal", "Tuseef", "Usama", "Tanveer", "Moeen")
        val paymentStatus = arrayOf("Received","Not Received","Pending", "Received","Not Received","Pending" )

        val adapter = DeliveryAdapter(customerNames.toMutableList(), paymentStatus.toMutableList())
        binding.OutForDeliveryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.OutForDeliveryRecyclerView.adapter = adapter
    }
}