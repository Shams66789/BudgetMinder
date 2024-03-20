package io.github.shams66789.budgetminder.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import io.github.shams66789.budgetminder.R
import io.github.shams66789.budgetminder.roomdb.entity.BudgetMinder
import io.github.shams66789.budgetminder.viewmodel.HomeViewModel
import kotlin.random.Random


class StatisticsFragment : Fragment() {

    private lateinit var barChart: BarChart
    private lateinit var viewModel: HomeViewModel
    private lateinit var pieChart: PieChart
    private lateinit var toggleButton: ToggleButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)
        barChart = view.findViewById(R.id.barChart)
        pieChart = view.findViewById(R.id.pieChart)
        toggleButton = view.findViewById(R.id.toggleButton)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set initial visibility
        barChart.visibility = View.VISIBLE
        pieChart.visibility = View.GONE

        // Set OnClickListener for the toggle button
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Show PieChart
                pieChart.visibility = View.VISIBLE
                barChart.visibility = View.GONE
                displayPieChart(viewModel.data.value ?: emptyList())
            } else {
                // Show BarChart
                pieChart.visibility = View.GONE
                barChart.visibility = View.VISIBLE
                displayBarChart(viewModel.data.value ?: emptyList())
            }
        }

        // Observe data changes
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.data.observe(viewLifecycleOwner, Observer { budgetMinders ->
            if (toggleButton.isChecked) {
                displayPieChart(budgetMinders)
            } else {
                displayBarChart(budgetMinders)
            }
        })
    }

    private fun displayBarChart(budgetMinders: List<BudgetMinder>) {
        // Implementation for BarChart
        // ...

        val categoryAmountMap = mutableMapOf<String, Float>()

        // Calculate total amount for each category excluding those containing "Salary"
        for (budgetMinder in budgetMinders) {
            budgetMinder.category?.let { category ->
                // Exclude categories containing "Salary"
                if (!category.contains("Salary")) {
                    val currentAmount = categoryAmountMap[category] ?: 0f
                    categoryAmountMap[category] = currentAmount + (budgetMinder.amount ?: 0).toFloat()
                }
            }
        }

        // Populate BarChart
        val entries = mutableListOf<BarEntry>()
        val labels = mutableListOf<String>()
        var index = 0
        for ((category, amount) in categoryAmountMap) {
            entries.add(BarEntry(index.toFloat(), amount))
            labels.add(category)
            index++
        }

        val barDataSet = BarDataSet(entries, "Category Amount")
        val data = BarData(barDataSet)
        barChart.data = data

        val xAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(true)
        xAxis.setGranularity(1f)
        xAxis.setCenterAxisLabels(true)
        xAxis.setLabelCount(labels.size)

        barChart.animateY(2000)
        barChart.invalidate()
    }

    private fun displayPieChart(budgetMinders: List<BudgetMinder>) {
        val categoryAmountMap = mutableMapOf<String, Float>()

        // Calculate total amount for each category excluding those containing "Salary"
        for (budgetMinder in budgetMinders) {
            budgetMinder.category?.let { category ->
                // Exclude categories containing "Salary"
                if (!category.contains("Salary")) {
                    val currentAmount = categoryAmountMap[category] ?: 0f
                    categoryAmountMap[category] = currentAmount + (budgetMinder.amount ?: 0).toFloat()
                }
            }
        }

        // Populate PieChart
        val entries = mutableListOf<PieEntry>()
        val colors = mutableListOf<Int>() // List to hold colors for each category

        // Generate a random color for each category
        for (i in 0 until categoryAmountMap.size) {
            entries.add(PieEntry(0f)) // Add empty entries to be filled later with actual values
            colors.add(getRandomColor())
        }

        var index = 0
        for ((category, amount) in categoryAmountMap) {
            entries[index] = PieEntry(amount, category) // Fill entries with actual values
            index++
        }

        val pieDataSet = PieDataSet(entries, "Category Amount")
        pieDataSet.colors = colors // Set colors for the dataset

        val data = PieData(pieDataSet)
        pieChart.data = data

        pieChart.invalidate()
    }

    private fun getRandomColor(): Int {
        return Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
    }
}