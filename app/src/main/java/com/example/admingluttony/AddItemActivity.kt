package com.example.admingluttony

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.admingluttony.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
       binding.BackArrowImageButton.setOnClickListener(){
           finish()
       }
       binding.SelectImageTextView.setOnClickListener(){
            pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }
    val pickImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){ uri ->

        if(uri != null)
            binding.SelecteImageImageView.setImageURI(uri)
    }
}