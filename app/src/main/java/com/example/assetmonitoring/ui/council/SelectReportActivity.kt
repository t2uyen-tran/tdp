package com.example.assetmonitoring.ui.council
//SL

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assetmonitoring.R
import java.util.*
import kotlin.collections.ArrayList

class SelectReportActivity : AppCompatActivity() {
    //SL: this activity is to allow a council worker to make selection as in what type of cases he/she want to view in a report by
    //e.g. picking the startdate of the report from a datePicker, ticking check boxes for options in "category", as well as in "status"

    var selectedStatus = ArrayList<String>()
    var selectedCategory = ArrayList<String>()
    var selectedDay = ""
    var selectedMth = ""
    var selectedYear = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_report_activity)

        val reportDate = findViewById<EditText>(R.id.reportDate_ET)
        var reportDateText = ""
        reportDate.setText(reportDateText)

        //SL: Use the DatePickerDialog as method for Date input - to show current date as default
        val mcalendar = Calendar.getInstance()
        val mday = mcalendar.get(Calendar.DAY_OF_MONTH)          //get current day of mth
        val mth = mcalendar.get(Calendar.MONTH)                  //get current mth
        val myear = mcalendar.get(Calendar.YEAR)                 //get current year
        selectedDay = mday.toString()                       //set selectedDay to current day - prevent null if no date picked
        selectedMth = (mth+1).toString()
        selectedYear = myear.toString()

        //get the selected day from the DatePickerDialog
        reportDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this, DatePickerDialog.OnDateSetListener
                { view, year, monthOfYear, dayOfMonth ->
                    reportDate.setText("Report start date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                    selectedDay = dayOfMonth.toString()
                    selectedMth = (monthOfYear+1).toString()
                    selectedYear = year.toString()
                }, myear, mth, mday
            )
            datePickerDialog.show()
            //SL: primary color is beige - not visible on DatePicker if not change the "OK", "Cancel" to darker color
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE)
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE)
        }

        //put all check boxes for Category into an ArrayList
        val categoryCheckBox1 = findViewById<CheckBox>(R.id.category1_checkBox)
        val categoryCheckBox2 = findViewById<CheckBox>(R.id.category2_checkBox)
        val categoryCheckBox3 = findViewById<CheckBox>(R.id.category3_checkBox)
        val categoryCheckBox4 = findViewById<CheckBox>(R.id.category4_checkBox)
        val categoryCheckBoxList = arrayListOf<CheckBox>(
            categoryCheckBox1,
            categoryCheckBox2,
            categoryCheckBox3,
            categoryCheckBox4)

        //put all check boxes for Status into an ArrayList
        val statusCheckBox1 = findViewById<CheckBox>(R.id.status1_checkBox)
        val statusCheckBox2 = findViewById<CheckBox>(R.id.status2_checkBox)
        val statusCheckBox3 = findViewById<CheckBox>(R.id.status3_checkBox)
        val statusCheckBoxList =
            arrayListOf<CheckBox>(statusCheckBox1, statusCheckBox2, statusCheckBox3)


        //SL: click "Display Report" button to go to the "DisplayReportActivity" page
        val displayReportButton: Button = findViewById(R.id.displayReport_button)
        displayReportButton.setOnClickListener {
            //SL: save multiple value in an intent and pass them to DisplayReportActivity
            selectedCategory = getCheckedText(categoryCheckBoxList)        //get selected options for Category into a list
            selectedStatus = getCheckedText(statusCheckBoxList)            //get selected options for Status into a list
            val intent = Intent(this, DisplayReportActivity::class.java).apply {
                putExtra("StartDateMth", selectedMth)
                putExtra("StartDateDay", selectedDay)
                putExtra("StartDateYear", selectedYear)
                //https://stackoverflow.com/questions/43332656/how-to-pass-multiple-arrayliststring-to-another-activity
                putStringArrayListExtra("SelectedStatus", selectedStatus)
                putStringArrayListExtra("SelectedCategory", selectedCategory)
            }
            startActivity(intent)
        }

        //SL: press "Back" button to back to previous page
        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            //SL: go back to the Sign-In Activity as the previous activity
            startActivity(Intent(this, OutstandingActivity::class.java))
            finish()
        }

    }

    //get all selected check box options and put them in an ArrayList
    fun getCheckedText(checkBoxes: List<CheckBox>) : ArrayList<String> {
        var checkedTextList = ArrayList<String>()
        for (item in checkBoxes) {
            if (item.isChecked) {
                checkedTextList.add(item.getText().toString())
            }
        }
        return checkedTextList
    }
}



