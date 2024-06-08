package com.example.admingluttony

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admingluttony.Adapters.DeliveryAdapter
import com.example.admingluttony.Adapters.PendingOrdersAdapter
import com.example.admingluttony.databinding.ActivityPendingOrdersBinding

class PendingOrdersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPendingOrdersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPendingOrdersBinding.inflate(layoutInflater)
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

        val OrderscustomerNames = arrayOf("Junaid", "Bilal", "Tuseef", "Usama", "Tanveer", "Moeen")
        val OrderQuantity = arrayOf("2","4","1", "5","8","9" )
        val OrdersfoodImage = arrayOf(R.drawable.menu4,R.drawable.menu2,R.drawable.menu3,R.drawable.menu1,R.drawable.menu4,R.drawable.menu1)
        val adapter = PendingOrdersAdapter(OrderscustomerNames.toMutableList(), OrderQuantity.toMutableList(), OrdersfoodImage.toMutableList(), this)
        binding.PendingOrdersRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.PendingOrdersRecyclerView.adapter = adapter
    }
}