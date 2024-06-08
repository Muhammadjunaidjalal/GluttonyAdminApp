package com.example.admingluttony.Adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.admingluttony.databinding.DeliveryItemBinding

class DeliveryAdapter(private val customerName: MutableList<String>, private val paymentStatus:MutableList<String>):RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
       val binding = DeliveryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeliveryViewHolder(binding)
    }
    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int {
        return customerName.size
    }
    inner class DeliveryViewHolder (private val binding: DeliveryItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                CustomerNameTextView.text = customerName[position]
                PaymentConfirmationTextView.text = paymentStatus[position]

                val colorMap = mapOf("Received" to Color.GREEN, "Not Received" to Color.RED
                ,"Pending" to Color.GRAY)

                PaymentConfirmationTextView.setTextColor(colorMap[paymentStatus[position]]?: Color.BLACK)
                StatusColor.backgroundTintList = ColorStateList.valueOf(colorMap[paymentStatus[position]]?:Color.BLACK)
            }

        }

    }
}
