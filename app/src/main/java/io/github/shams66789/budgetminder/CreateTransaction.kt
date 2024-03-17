package io.github.shams66789.budgetminder

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import io.github.shams66789.budgetminder.databinding.ActivityCreateTransactionBinding
import io.github.shams66789.budgetminder.roomdb.entity.BudgetMinder
import io.github.shams66789.budgetminder.viewmodel.CreateTransactionViewModel
import java.text.SimpleDateFormat
import java.util.*

class CreateTransaction : AppCompatActivity() {

    val binding : ActivityCreateTransactionBinding by lazy {
        ActivityCreateTransactionBinding.inflate(layoutInflater)
    }

    var selectedOption: String = ""
    lateinit var calendar: Calendar
    lateinit var dateInputLayout: TextInputLayout
    lateinit var dateInputEditText: TextInputEditText
    lateinit var viewModel : CreateTransactionViewModel
    var budgetMinder = BudgetMinder()
    lateinit var selectCategory : String
    lateinit var selectCategory1 : String
    var amount : Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this).get(CreateTransactionViewModel::class.java)

        val amountGet = binding.editTextNumber

        calendar = Calendar.getInstance()
        dateInputLayout = binding.dateInputLayout
        dateInputEditText = binding.dateInputEditText
        dateInputEditText.setOnClickListener {
            showDatePickerDialog()
        }

        val filledExposedDropdown = binding.filledExposedDropdown
        val filledExposedDropdown1 = binding.filledExposedDropdown1


        // Define the array of dropdown options
        val options = arrayOf("Shopping", "Salary", "Movie", "Travelling", "Food", "Party")
        val options1 = arrayOf("Purchase some clothes", "Salary received", "Went for Movie", "Went for a trip", "Eating", "Club")

        // Create an ArrayAdapter using the options array and a simple dropdown layout
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, options)
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, options1)

        // Set the adapter to the AutoCompleteTextView
        filledExposedDropdown.setAdapter(adapter)
        filledExposedDropdown1.setAdapter(adapter1)

        // Set item click listener for the first AutoCompleteTextView
        filledExposedDropdown.setOnItemClickListener { parent, view, position, id ->
            // Retrieve the selected item
            val selectedItem = parent.getItemAtPosition(position).toString()
            // Do something with the selected item, e.g., store it in a variable
            selectCategory = selectedItem
            // You can also do whatever you need with the selected option here
        }

// Set item click listener for the second AutoCompleteTextView
        filledExposedDropdown1.setOnItemClickListener { parent, view, position, id ->
            // Retrieve the selected item
            val selectedItem = parent.getItemAtPosition(position).toString()
            // Do something with the selected item, e.g., store it in a variable
            selectCategory1 = selectedItem
            // You can also do whatever you need with the selected option here
        }

        binding.button.setOnClickListener { selectOption(it) }
        binding.button2.setOnClickListener { selectOption(it) }

        binding.button3.setOnClickListener {
            val amountText = binding.editTextNumber.text.toString().trim()
            if (selectedOption.isEmpty() || selectCategory.isEmpty() || selectCategory1.isEmpty() || amountText.isEmpty()) {
                Toast.makeText(
                    this,
                    "One or more field is Empty. Please fill all the fields",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val amount = amountText.toIntOrNull()
                if (amount == null) {
                    Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                } else {
                    var dateInputText = dateInputEditText.text.toString().trim()
                    if (dateInputText.isEmpty()) {
                        // Date is empty, set it to the current date
                        dateInputText = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                    }

                    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    val date = sdf.parse(dateInputText)

                    if (date != null) {
                        budgetMinder.data = date
                    }

                    budgetMinder.amount = amount
                    budgetMinder.category = selectCategory
                    budgetMinder.description = selectCategory1
                    budgetMinder.type = selectedOption

                    viewModel.storeData(budgetMinder) { insertedId ->
                        if (insertedId != null && insertedId > 0) {
                            Toast.makeText(
                                this@CreateTransaction,
                                "Transaction Created",
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        }
                    }
                }
            }
        }

    }


    private fun selectOption(view: View) {
        // Reset both buttons' background tints
        binding.button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.income)
        binding.button2.backgroundTintList = ContextCompat.getColorStateList(this, R.color.expense)

        // Highlight the selected button and update selectedOption
        when (view.id) {
            binding.button.id -> {
                view.backgroundTintList = ContextCompat.getColorStateList(this, R.color.income_selected)
                selectedOption = "Income"
            }
            binding.button2.id -> {
                view.backgroundTintList = ContextCompat.getColorStateList(this, R.color.expense_selected)
                selectedOption = "Expense"
            }
        }
    }

    private fun showDatePickerDialog() {
        // Create a DatePickerDialog to select a date
        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                // Update calendar with the selected date
                calendar.set(year, month, dayOfMonth)

                // Format the selected date
                val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val selectedDate = sdf.format(calendar.time)

                // Update the date input EditText with the selected date
                dateInputEditText.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Show the DatePickerDialog
        datePickerDialog.show()
    }
}