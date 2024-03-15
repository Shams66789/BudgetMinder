package io.github.shams66789.budgetminder.others

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.shams66789.budgetminder.databinding.HomeRvBinding
import io.github.shams66789.budgetminder.roomdb.entity.BudgetMinder

class HomeAdapter(var transactionList : List<BudgetMinder>, var context: Context) : RecyclerView
    .Adapter<HomeAdapter.MyViewHolder>(){

    inner class MyViewHolder(var binding: HomeRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =HomeRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var transaction = transactionList[position]

        holder.binding.textView8.text = transaction.category
        holder.binding.textView9.text = transaction.description
        holder.binding.textView10.text = transaction.amount.toString()
    }

}