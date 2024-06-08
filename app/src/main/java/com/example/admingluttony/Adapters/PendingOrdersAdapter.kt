package com.example.admingluttony.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.admingluttony.databinding.ActivityPendingOrdersBinding
import com.example.admingluttony.databinding.PendingOrderItemBinding

class PendingOrdersAdapter(private val customerName:MutableList<String>, private val orderQuantity:MutableList<String>, private val ordersImage:MutableList<Int>, private val context : Context):RecyclerView.Adapter<PendingOrdersAdapter.PendingOrdersViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrdersViewHolder {
       val binding = PendingOrderItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return PendingOrdersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PendingOrdersViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int {
        return customerName.size
    }

    inner class PendingOrdersViewHolder(private val binding:PendingOrderItemBinding):RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false
        fun bind(position: Int) {
            binding.apply {

                PendingOrderCustomerName.text = customerName[position]
                PendingOrdersCount.text = orderQuantity[position]
                PendingOrderImageView.setImageResource(ordersImage[position])

                PendingOrdersAcceptButton.apply {
                    if(!isAccepted){
                        text = "Accept"
                }
                    else{
                        text = "Dispatch"
                    }
                    setOnClickListener(){
                        if(!isAccepted){
                            text = "Dispatch"
                            showToast("Order Has Been Accepted!")
                            isAccepted = true
                        }
                        else{
                            customerName.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("Order Has Been Dispatched!")
                        }
                    }
                }
            }

        }
       private fun showToast(message:String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT ).show()
        }
    }
}