package io.github.shams66789.budgetminder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.shams66789.budgetminder.roomdb.entity.BudgetMinder

class CreateTransactionViewModel(application : Application) : AndroidViewModel(application) {
    var repo: Repo
    init {

        repo = Repo(application)

    }

    fun storeData(budgetMinder: BudgetMinder, function:(i : Long?)-> Unit){
        val i = repo.addData(budgetMinder)
        function(i)
    }
}