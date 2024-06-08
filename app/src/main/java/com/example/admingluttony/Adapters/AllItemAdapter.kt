package com.example.admingluttony.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.admingluttony.databinding.ItemItemBinding

class AllItemAdapter(private val itemFoodName: MutableList<String>, private val itemFoodPrice: MutableList<String>, private val itemFoodImage: MutableList<Int>):RecyclerView.Adapter<AllItemAdapter.AllItemViewHolder>() {

    private val itemQuantities = IntArray(itemFoodName.size){1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllItemViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return AllItemViewHolder(binding)
    }



    override fun onBindViewHolder(holder: AllItemViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int {
        return itemFoodName.size
    }
    inner class AllItemViewHolder(private val binding: ItemItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                CountOfItem.text = quantity.toString()
                AllItemFoodName.text = itemFoodName[position]
                AllItemFoodPrice.text = itemFoodPrice[position]
                AllItemImageView.setImageResource(itemFoodImage[position])

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
            itemFoodName.removeAt(position)
            itemFoodPrice.removeAt(position)
            itemFoodImage.removeAt(position)

            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemFoodName.size)
        }

    }
}


