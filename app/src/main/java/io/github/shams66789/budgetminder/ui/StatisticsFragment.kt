package io.github.shams66789.budgetminder.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.google.android.material.tabs.TabLayout
import io.github.shams66789.budgetminder.R
import io.github.shams66789.budgetminder.roomdb.entity.BudgetMinder
import io.github.shams66789.budgetminder.viewmodel.HomeViewModel
import kotlin.random.Random


class StatisticsFragment : Fragment() {

    private lateinit var barChart: BarChart
    private lateinit var pieChart: PieChart
    private lateinit var tabLayout: TabLayout
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)
        barChart = view.findViewById(R.id.barChart)
        pieChart = view.findViewById(R.id.pieChart)
        tabLayout = view.findViewById(R.id.tabLayout)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Set initial visibility
        barChart.visibility = View.VISIBLE
        pieChart.visibility = View.GONE

        // Add tabs
        tabLayout.addTab(tabLayout.newTab()
            .setIcon(R.drawable.column_chart_icon)
//            .setText("Bar Chart")
        )
        tabLayout.addTab(tabLayout.newTab()
            .setIcon(R.drawable.pie_chart)
//            .setText("Pie Chart")
        )

        // Set tab selected listener
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> showBarChart()
                    1 -> showPieChart()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // Observe data changes
        viewModel.data.observe(viewLifecycleOwner, Observer { budgetMinders ->
            if (tabLayout.selectedTabPosition == 1) {
                displayPieChart(budgetMinders)
            } else {
                displayBarChart(budgetMinders)
            }
        })

        // Display initial chart
        displayBarChart(viewModel.data.value ?: emptyList())
    }

    private fun showBarChart() {
        barChart.visibility = View.VISIBLE
        pieChart.visibility = View.GONE
        displayBarChart(viewModel.data.value ?: emptyList())
    }

    private fun showPieChart() {
        pieChart.visibility = View.VISIBLE
        barChart.visibility = View.GONE
        displayPieChart(viewModel.data.value ?: emptyList())
    }

    private fun displayBarChart(budgetMinders: List<BudgetMinder>) {
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