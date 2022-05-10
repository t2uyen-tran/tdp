package com.example.assetmonitoring.ui.council
//SL

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.assetmonitoring.R
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class SelectReportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_report_activity)

        val reportDate = findViewById<EditText>(R.id.reportDate_ET)
        var reportDateText = ""
        reportDate.setText(reportDateText)

        //SL: Use the DatePickerDialog as method for Date input
        val mcalendar = Calendar.getInstance()
        val mday = mcalendar.get(Calendar.DAY_OF_MONTH)
        val mth = mcalendar.get(Calendar.MONTH)
        val myear = mcalendar.get(Calendar.YEAR)

        reportDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this, DatePickerDialog.OnDateSetListener
                { view, year, monthOfYear, dayOfMonth ->
                    reportDate.setText("Report start date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                }, myear, mth, mday
            )
            datePickerDialog.show()
            //SL: primary color is beige - not visible on DatePicker if not change the "OK", "Cancel" to darker color
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE)
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE)
        }

        //SL: press "Back" button to back to previous page
        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            //SL: go back to the Sign-In Activity as the previous activity
            startActivity(Intent(this, OutstandingActivity::class.java))
            finish()
        }


    }

}