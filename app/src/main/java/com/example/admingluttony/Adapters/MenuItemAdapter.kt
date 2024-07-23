package com.example.admingluttony.Adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.admingluttony.Models.AllMenu
import com.example.admingluttony.databinding.ItemItemBinding
import com.google.firebase.database.DatabaseReference

class MenuItemAdapter(
    private val context: Context,
    private val menuList: ArrayList<AllMenu>,
    databaseReference: DatabaseReference
):RecyclerView.Adapter<MenuItemAdapter.AllItemViewHolder>() {

    private val itemQuantities = IntArray(menuList.size){1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllItemViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return AllItemViewHolder(binding)
    }



    override fun onBindViewHolder(holder: AllItemViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int {
        return menuList.size
    }
    inner class AllItemViewHolder(private val binding: ItemItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                val menuItem = menuList[position]
                val uriString = menuItem.foodImage
                val uri = Uri.parse(uriString)
                CountOfItem.text = quantity.toString()
                AllItemFoodName.text = menuItem.foodName
                AllItemFoodPrice.text = menuItem.foodPrice
                Glide.with(context).load(uri).into(AllItemImageView)

                MinusImageButton.setOnClickListener(){

                    decreaseQuantity(position)
                }
                PlusImageButton.setOnClickListener(){
                    increaseQuantity(position)
                }
                DeleteItem.setOnClickListener(){
                    deleteItem(position)
                }
            }
        }
        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position]<10){
                itemQuantities[position]++
                binding.CountOfItem.text = itemQuantities[position].toString()
            }
        }

        private fun decreaseQuantity(position: Int) {
            if (itemQuantities[position]>1){
                itemQuantities[position]--
                binding.CountOfItem.text = itemQuantities[position].toString()
            }
        }
        private fun deleteItem(position: Int) {
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)

            notifyItemRemoved(position)
            notifyItemRangeChanged(position, menuList.size)
        }

    }
}


