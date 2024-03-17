package io.github.shams66789.budgetminder.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.shams66789.budgetminder.DB_VERSION
import io.github.shams66789.budgetminder.others.Converters
import io.github.shams66789.budgetminder.roomdb.dao.Dao
import io.github.shams66789.budgetminder.roomdb.entity.BudgetMinder

@Database(entities = [BudgetMinder :: class], version = DB_VERSION, exportSchema = false)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {

    abstract fun getBudgetMinderDao(): Dao
}