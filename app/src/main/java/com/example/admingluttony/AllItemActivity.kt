package com.example.admingluttony

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admingluttony.Adapters.AllItemAdapter
import com.example.admingluttony.databinding.ActivityAllItemBinding

class AllItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAllItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setAdapterData()
        binding.BackArrowImageButton.setOnClickListener(){
            finish()
        }
    }
    // Function with data and setting up the adapter
    private fun setAdapterData(){
        val foodName= arrayOf("Burger", "Pizza", "Fries", "Zinger Burger", "Momos", "Sandwich", "Zinger Sandwich")
        val foodPrice = arrayOf("$3", "$4", "$5", "$8","$9","$1", "$15","$12")
        val foodImage = arrayOf(R.drawable.menu4,R.drawable.menu2,R.drawable.menu3,R.drawable.menu1,R.drawable.menu4,R.drawable.menu1,R.drawable.menu3,R.drawable.menu2,)

        val adapter = AllItemAdapter(foodName.toMutableList(),foodPrice.toMutableList(), foodImage.toMutableList())
        binding.AllItemRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.AllItemRecyclerView.adapter = adapter
    }
}