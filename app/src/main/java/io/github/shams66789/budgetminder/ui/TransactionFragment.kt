package io.github.shams66789.budgetminder.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.shams66789.budgetminder.R
import io.github.shams66789.budgetminder.databinding.FragmentTransactionBinding
import io.github.shams66789.budgetminder.others.TransactionAdapter
import io.github.shams66789.budgetminder.roomdb.entity.BudgetMinder
import io.github.shams66789.budgetminder.viewmodel.HomeViewModel
import java.util.Calendar

class TransactionFragment : Fragment() {

    private lateinit var todayAdapter: TransactionAdapter
    private lateinit var yesterdayAdapter: TransactionAdapter
    private lateinit var earlierAdapter: TransactionAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_transaction, container, false)

        // Initialize RecyclerView adapters
        todayAdapter = TransactionAdapter(requireContext())
        yesterdayAdapter = TransactionAdapter(requireContext())
        earlierAdapter = TransactionAdapter(requireContext())

        // Set up RecyclerViews
        view.findViewById<RecyclerView>(R.id.rvTodayTransactions).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todayAdapter
        }
        view.findViewById<RecyclerView>(R.id.rvYesterdayTransactions).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = yesterdayAdapter
        }
        view.findViewById<RecyclerView>(R.id.rvEarlierTransactions).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = earlierAdapter
        }

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Observing transaction data
        viewModel.data.observe(viewLifecycleOwner) { transactions ->
            transactions?.let {
                // Group transactions by date
                val groupedTransactions = it.groupBy { transaction ->
                    val cal = Calendar.getInstance()
                    cal.time = transaction.data
                    when {
                        isToday(cal) -> "Today"
                        isYesterday(cal) -> "Yesterday"
                        else -> "Earlier"
                    }
                }

                // Update RecyclerView adapters and visibility of sections
                updateAdapterWithVisibility(
                    groupedTransactions["Today"],
                    todayAdapter,
                    R.id.tvTodayHeading,
                    R.id.rvTodayTransactions
                )

                updateAdapterWithVisibility(
                    groupedTransactions["Yesterday"],
                    yesterdayAdapter,
                    R.id.tvYesterdayHeading,
                    R.id.rvYesterdayTransactions
                )

                updateAdapterWithVisibility(
                    groupedTransactions["Earlier"],
                    earlierAdapter,
                    R.id.tvEarlierHeading,
                    R.id.rvEarlierTransactions
                )

                // Show imageViewEmptyState if no transactions are available
                if (groupedTransactions.values.all { it.isNullOrEmpty() }) {
                    view.findViewById<ImageView>(R.id.imageViewEmptyState).visibility = View.VISIBLE
                } else {
                    view.findViewById<ImageView>(R.id.imageViewEmptyState).visibility = View.GONE
                }
            }
        }

        return view
    }

    // Helper function to update adapter with visibility
    private fun updateAdapterWithVisibility(
        transactions: List<BudgetMinder>?,
        adapter: TransactionAdapter,
        headingId: Int,
        recyclerViewId: Int
    ) {
        if (transactions.isNullOrEmpty()) {
            view?.findViewById<View>(headingId)?.visibility = View.GONE
            view?.findViewById<View>(recyclerViewId)?.visibility = View.GONE
        } else {
            view?.findViewById<View>(headingId)?.visibility = View.VISIBLE
            view?.findViewById<View>(recyclerViewId)?.visibility = View.VISIBLE
            adapter.submitList(transactions)
        }
    }

    // Helper functions to check if a date is today or yesterday
    private fun isToday(cal: Calendar): Boolean {
        val today = Calendar.getInstance()
        return today.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
                today.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)
    }

    private fun isYesterday(cal: Calendar): Boolean {
        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DAY_OF_YEAR, -1)
        return yesterday.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
                yesterday.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)
    }
}