package io.github.shams66789.budgetminder.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import io.github.shams66789.budgetminder.R
import io.github.shams66789.budgetminder.databinding.FragmentTransactionBinding

class TransactionFragment : Fragment() {

    private val binding : FragmentTransactionBinding by lazy {
        FragmentTransactionBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        return binding.root
    }

}