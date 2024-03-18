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

        if (transaction.category == "Salary") {
            holder.binding.imageView2.setImageResource(R.drawable.affordable_icon)
            holder.binding.imageView2.setBackgroundResource(R.color.salary)
            holder.binding.textView8.text = transaction.category
            holder.binding.textView9.text = transaction.description
            val income = transaction.amount.toString()
            holder.binding.textView10.setText("+ ₹ $income")
            holder.binding.textView10.setTextColor(ContextCompat.getColor(context, R.color.income))
            holder.binding.textView11.text = formatDate(transaction.data)
        } else {
            if (transaction.category == "Movie") {
                holder.binding.imageView2.setImageResource(R.drawable.outline_movie_24)
                holder.binding.imageView2.setBackgroundResource(R.color.movie)
            } else if (transaction.category == "Party") {
                holder.binding.imageView2.setImageResource(R.drawable.club_party_icon)
                holder.binding.imageView2.setBackgroundResource(R.color.party)
            } else if (transaction.category == "Shopping") {
                holder.binding.imageView2.setImageResource(R.drawable.outline_shopping_basket_24)
                holder.binding.imageView2.setBackgroundResource(R.color.shopping)
            } else if (transaction.category == "Food") {
                holder.binding.imageView2.setImageResource(R.drawable.outline_food_bank_24)
                holder.binding.imageView2.setBackgroundResource(R.color.food)
            } else if (transaction.category == "Travelling") {
                holder.binding.imageView2.setImageResource(R.drawable.outline_backpack_24)
                holder.binding.imageView2.setBackgroundResource(R.color.travelling)
            }
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