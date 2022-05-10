package com.example.assetmonitoring.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.assetmonitoring.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ReportIssueActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

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

        //SL: press "Back" button to back to previous page
        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            onBackPressed()
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-37.783, 144.95)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL)
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16F))
        mMap.getUiSettings().setZoomControlsEnabled(true)
        mMap.getUiSettings().setCompassEnabled(true)
        mMap.getUiSettings().setZoomGesturesEnabled(true)
        mMap.getUiSettings().setScrollGesturesEnabled(true)
        mMap.getUiSettings().setRotateGesturesEnabled(true)
    }

}

