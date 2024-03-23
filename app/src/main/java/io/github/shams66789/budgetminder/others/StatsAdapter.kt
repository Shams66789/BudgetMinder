package io.github.shams66789.budgetminder.others

import android.content.res.Configuration
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.shams66789.budgetminder.R
import kotlin.random.Random


class StatsAdapter(private val data: List<Pair<String, Float>>) : RecyclerView.Adapter<StatsAdapter.StatsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stats_rv, parent, false)
        return StatsViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        val (category, amount) = data[position]
        holder.bind(category, amount)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class StatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryTextView: TextView = itemView.findViewById(R.id.textView14)
        private val amountTextView: TextView = itemView.findViewById(R.id.textView15)

        fun bind(category: String, amount: Float) {
            // Set category text
            categoryTextView.text = category

            // Set random text color for the category
//            categoryTextView.setTextColor(getRandomColor())

            // Set amount text
            amountTextView.text = "- ₹ $amount"

            val randomColor = getRandomColor()
            // Set text color based on device theme mode
            val nightModeFlags = itemView.context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
                // Dark mode
                categoryTextView.setTextColor(Color.WHITE) // Set light color for dark mode
            } else {
                // Light mode
//                categoryTextView.setTextColor(Color.BLACK) // Set dark color for light mode
                categoryTextView.setTextColor(randomColor)

            }
        }
    }

    private fun getRandomColor(): Int {
        return Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
    }
}