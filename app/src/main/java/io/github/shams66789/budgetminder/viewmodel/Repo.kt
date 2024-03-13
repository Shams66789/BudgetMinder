package io.github.shams66789.budgetminder.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import io.github.shams66789.budgetminder.roomdb.Database
import io.github.shams66789.budgetminder.roomdb.DbBuilder
import io.github.shams66789.budgetminder.roomdb.entity.BudgetMinder

class Repo(context: Context) {
    var database : Database? = null

    init {
        database = DbBuilder.getDb(context)
    }

    fun getData(): LiveData<List<BudgetMinder>>? {
        return database?.getBudgetMinderDao()?.getEntry()
    }

    fun addData(budgetMinder : BudgetMinder) : Long? {
        return database?.getBudgetMinderDao()?.createEntry(budgetMinder)
    }

    fun deleteData(budgetMinder: BudgetMinder) {
        database?.getBudgetMinderDao()?.deleteEntry(budgetMinder)
    }

    fun  updateData(budgetMinder: BudgetMinder): Int? {
        return database?.getBudgetMinderDao()?.updateEntry(budgetMinder)
    }
}