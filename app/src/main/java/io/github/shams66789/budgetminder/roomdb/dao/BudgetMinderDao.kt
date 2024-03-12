package io.github.shams66789.budgetminder.roomdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.github.shams66789.budgetminder.roomdb.entity.BudgetMinder

@Dao
interface BudgetMinderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createEntry(budgetMinder : BudgetMinder) : Long

    @Update
    fun updateEntry(budgetMinder: BudgetMinder) : Int

    @Query("SELECT * FROM BudgetMinder")
    fun getEntry() : LiveData<List<BudgetMinder>>

    @Query("SELECT * FROM BudgetMinder WHERE type = :type")
    fun getEntry(type : String) : BudgetMinder

    @Delete
    fun deleteEntry(budgetMinder: BudgetMinder)
}