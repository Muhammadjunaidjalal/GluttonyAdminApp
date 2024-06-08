package com.example.admingluttony

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.admingluttony.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAdminProfileBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.BackArrowImageButton.setOnClickListener(){
            finish()
        }
        binding.NameEditText.isEnabled = false
        binding.AddressEditText.isEnabled = false
        binding.PhoneEditText.isEnabled = false
        binding.EmailEditText.isEnabled = false
        binding.PasswordEditText.isEnabled = false

        var isEnable = false

        binding.ClickToEditTextView.setOnClickListener(){
            isEnable =! isEnable
            binding.NameEditText.isEnabled = true
            binding.AddressEditText.isEnabled = true
            binding.PhoneEditText.isEnabled = true
            binding.EmailEditText.isEnabled = true
            binding.PasswordEditText.isEnabled = true

            if(isEnable){
                binding.NameEditText.requestFocus()
            }

        }
    }
}