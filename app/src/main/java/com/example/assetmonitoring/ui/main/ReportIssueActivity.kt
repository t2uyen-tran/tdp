package com.example.assetmonitoring.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.assetmonitoring.R

class ReportIssueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.report_issue_activity)


        //https://stackoverflow.com/questions/60481808/kotlin-how-to-save-radio-button-and-display-values-->
        val radio_group = findViewById<RadioGroup>(R.id.radio_group)

        // Get radio group selected item using on checked change listener
        radio_group.setOnCheckedChangeListener { radio_group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (checkedId == R.id.radio_yes) {
                //check if Email is provided if the user wants to be notified updates
                val emailET: EditText = findViewById(R.id.emailInput_ET)
                if (emailET.text.toString() == "") {
                    Toast.makeText(
                        this,
                        "Please make sure to provide your email address to receive updates",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }

    }

}