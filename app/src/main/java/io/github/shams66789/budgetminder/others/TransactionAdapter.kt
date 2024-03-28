package io.github.shams66789.budgetminder.others

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.shams66789.budgetminder.R
import io.github.shams66789.budgetminder.roomdb.entity.BudgetMinder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionAdapter(private val context: Context) : ListAdapter<BudgetMinder, TransactionAdapter.TransactionViewHolder>(TransactionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = getItem(position)
        holder.bind(transaction)
    }

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView10)
        private val amountTextView: TextView = itemView.findViewById(R.id.textView27)
        private val categoryTextView: TextView = itemView.findViewById(R.id.textView25)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.textView26)
        private val dateTextView: TextView = itemView.findViewById(R.id.textView28)

        fun bind(transaction: BudgetMinder) {
            if (transaction.category == "Salary") {
                imageView.setImageResource(R.drawable.affordable_icon)
                imageView.setBackgroundResource(R.color.salary)
                categoryTextView.text = transaction.category
                val income = transaction.amount.toString()
                amountTextView.text = "+ ₹ $income"
                amountTextView.setTextColor(ContextCompat.getColor(context, R.color.income))
                descriptionTextView.text = transaction.description
                dateTextView.text = formatDate(transaction.data)
            }else {
                if (transaction.category == "Movie") {
                    imageView.setImageResource(R.drawable.outline_movie_24)
                    imageView.setBackgroundResource(R.color.movie)
                } else if (transaction.category == "Party") {
                    imageView.setImageResource(R.drawable.club_party_icon)
                    imageView.setBackgroundResource(R.color.party)
                } else if (transaction.category == "Shopping") {
                    imageView.setImageResource(R.drawable.outline_shopping_basket_24)
                    imageView.setBackgroundResource(R.color.shopping)
                } else if (transaction.category == "Food") {
                    imageView.setImageResource(R.drawable.outline_food_bank_24)
                    imageView.setBackgroundResource(R.color.food)
                } else if (transaction.category == "Travelling") {
                    imageView.setImageResource(R.drawable.outline_backpack_24)
                    imageView.setBackgroundResource(R.color.travelling)
                }
                categoryTextView.text = transaction.category
                descriptionTextView.text = transaction.description
                val expense = transaction.amount.toString()
                amountTextView.setText("- ₹ $expense")
                dateTextView.text = formatDate(transaction.data)
            }

        }
    }

    class TransactionDiffCallback : DiffUtil.ItemCallback<BudgetMinder>() {
        override fun areItemsTheSame(oldItem: BudgetMinder, newItem: BudgetMinder): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BudgetMinder, newItem: BudgetMinder): Boolean {
            return oldItem.amount == newItem.amount &&
                    oldItem.category == newItem.category &&
                    oldItem.description == newItem.description &&
                    oldItem.type == newItem.type &&
                    oldItem.data == newItem.data
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
