package com.example.admingluttony

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.admingluttony.Models.AllMenu
import com.example.admingluttony.databinding.ActivityAddItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage


class AddItemActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddItemBinding

    // Food Details variables
    private lateinit var foodName :String
    private lateinit var foodPrice : String
    private lateinit var foodDescription: String
    private lateinit var foodIngredients : String
    private var foodImageUri : Uri? = null

    //firebase variables
    private lateinit var auth : FirebaseAuth
    private lateinit var database: FirebaseDatabase

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
        //initialize firebase variables
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.AddItemButton.setOnClickListener(){
            foodName = binding.ItemNameEditText.text.toString().trim()
            foodPrice = binding.ItemPriceEditText.text.toString().trim()
            foodDescription = binding.DescriptionEditText.text.toString().trim()
            foodIngredients = binding.IngredientsEditText.text.toString().trim()

            if(!(foodName.isBlank()|| foodPrice.isBlank()||foodDescription.isBlank()||foodIngredients.isBlank())){
                uploadData()
                Toast.makeText(this,"Item Added Successfully!", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this, "Fill All The Details!", Toast.LENGTH_SHORT).show()

            }
        }
       binding.BackArrowImageButton.setOnClickListener(){
           finish()
       }
       binding.SelectImageTextView.setOnClickListener(){
            pickImage.launch("image/*")
        }
    }

    private fun uploadData() {
        //get a refernce of menu node in database
        val menuRef = database.getReference("menu")
        //generate unique key for new menu item
        val newItemKey  = menuRef.push().key

        if(foodImageUri!=null){
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri!!)
            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                    downloadUrl->

                    val newItem = AllMenu(
                        foodName = foodName,
                        foodPrice = foodPrice,
                        foodDescription = foodDescription,
                        foodIngredients = foodIngredients,
                        foodImage = downloadUrl.toString()
                    )
                    newItemKey?.let {
                        key->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this, "Data Added Successfully!", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener{
                            Toast.makeText(this, "Data Is Not Added!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Image Upload Failed!", Toast.LENGTH_SHORT).show()
            }
        }
        else{
                Toast.makeText(this, "Please Select An Image!", Toast.LENGTH_SHORT).show()
            }
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->

        if(uri != null)
            binding.SelecteImageImageView.setImageURI(uri)
            foodImageUri= uri
    }
}