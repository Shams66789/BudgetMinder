package io.github.shams66789.budgetminder.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity
class BudgetMinder(
    @PrimaryKey var id: Int? = null,
    var amount: Int? = null,
    var category: String? = null,
    var description: String? = null,
    var type: String? = null,
    var data: Date? = null
): Serializable