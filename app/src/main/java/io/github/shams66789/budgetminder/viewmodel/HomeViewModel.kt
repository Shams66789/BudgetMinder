package io.github.shams66789.budgetminder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.github.shams66789.budgetminder.roomdb.entity.BudgetMinder

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    var repo : Repo
    var data : LiveData<List<BudgetMinder>>
    init {
        repo= Repo(application)
        data = repo.getData()!!
    }
}