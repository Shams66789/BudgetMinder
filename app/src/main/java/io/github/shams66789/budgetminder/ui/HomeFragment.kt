package io.github.shams66789.budgetminder.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.shams66789.budgetminder.CreateTransaction
import io.github.shams66789.budgetminder.R
import io.github.shams66789.budgetminder.databinding.FragmentHomeBinding
import io.github.shams66789.budgetminder.others.HomeAdapter
import io.github.shams66789.budgetminder.roomdb.entity.BudgetMinder
import io.github.shams66789.budgetminder.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private val binding : FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    var viewModel : HomeViewModel? = null
    var transactionList = ArrayList<BudgetMinder>()
    lateinit var adapter : HomeAdapter
    var totalIncome = 0.0
    var totalExpense = 0.0
    val income = "Income"
    val expense = "Expense"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel =ViewModelProvider(this)[HomeViewModel::class.java]


        binding.rv.layoutManager = LinearLayoutManager(context)
        adapter = HomeAdapter(transactionList, requireContext())
        binding.rv.adapter = adapter



        viewModel!!.data.observeForever{
            transactionList.clear()
            it.map {
                transactionList.add(it)
            }
            getIncome()
            getExpense()
            adapter.notifyDataSetChanged()
            updateVisibility()
        }

        binding.textView7.setOnClickListener {
            startActivity(Intent(requireContext(), TransactionFragment::class.java))
        }

        // Inflate the layout for this fragment
        return binding.root


    }

    fun updateVisibility() {
        if (binding.rv.adapter?.itemCount == 0) {
            binding.rv.visibility  = View.GONE
            binding.textView7.visibility = View.GONE
            binding.imageView.visibility = View.VISIBLE
        } else {
            binding.rv.visibility  = View.VISIBLE
            binding.textView7.visibility = View.VISIBLE
            binding.imageView.visibility = View.GONE
        }
    }

    fun getIncome() {
        val incomeList = if (transactionList.size == 0) {
            transactionList
        } else {
            transactionList.filter {
                it.type?.contains(income, ignoreCase = true)  == true
            }
        }
        totalIncome = 0.0

        if (incomeList.isEmpty()) {
            totalIncome = 0.0
        } else {
            incomeList.forEach {
                totalIncome += it.amount!!
            }
        }
        val income = totalIncome.toString()
        binding.textView2.setText("₹ $income")
    }

    fun getExpense() {
        val expenseList = if (transactionList.size == 0) {
            transactionList
        } else {
            transactionList.filter {
                it.type?.contains(expense, ignoreCase = true)  == true
            }
        }
        totalExpense = 0.0
        if (expenseList.isEmpty()) {
            totalExpense = 0.0
        } else {
            expenseList.forEach {
                totalExpense += it.amount!!
            }
        }

        val expense = totalExpense.toString()
        binding.textView4.setText("₹ $expense")
    }


}