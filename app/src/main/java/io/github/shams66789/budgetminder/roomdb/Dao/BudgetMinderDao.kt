package io.github.shams66789.budgetminder.roomdb.Dao

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
    fun createEntry(budgetMinder : BudgetMinder)

    @Update
    fun updateEntry(budgetMinder: BudgetMinder)

    @Query("SELECT * FROM BudgetMinder")
    fun getEntry()

    @Query("SELECT * FROM BudgetMinder WHERE type = :type")
    fun getEntry(type : String) : BudgetMinder

    @Delete
    fun deleteEntry(budgetMinder: BudgetMinder)
}