package io.github.shams66789.budgetminder.others

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.github.shams66789.budgetminder.R
import io.github.shams66789.budgetminder.databinding.HomeRvBinding
import io.github.shams66789.budgetminder.roomdb.entity.BudgetMinder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        val transaction = transactionList[position]

        if (transaction.type == "Income") {
            holder.binding.textView8.text = transaction.category
            holder.binding.textView9.text = transaction.description
            val income = transaction.amount.toString()
            holder.binding.textView10.setText("+ ₹ $income")
            holder.binding.textView10.setTextColor(ContextCompat.getColor(context, R.color.income))
            holder.binding.textView11.text = formatDate(transaction.data)
        } else {
            holder.binding.textView8.text = transaction.category
            holder.binding.textView9.text = transaction.description
            val expense = transaction.amount.toString()
            holder.binding.textView10.setText("- ₹ $expense")
            holder.binding.textView11.text = formatDate(transaction.data)
        }

    }

    private fun formatDate(date: Date?): String {
        return if (date != null) {
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            sdf.format(date)
        } else {
            ""
        }
    }
}