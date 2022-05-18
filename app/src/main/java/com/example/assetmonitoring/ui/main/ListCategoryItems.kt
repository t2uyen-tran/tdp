package com.example.assetmonitoring.ui.main

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.assetmonitoring.R
import com.example.assetmonitoring.model.Categories
import com.example.assetmonitoring.ui.council.OutstandingActivity
import com.facebook.appevents.suggestedevents.ViewOnClickListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

private const val TAG = "ListCategoryItems"

class ListCategoryItems : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onStart()called")
        setContentView(R.layout.list_category_items)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val categoryNameTV = findViewById<TextView>(R.id.categoryListTitle_textV)

        //SL: received the details of the selected category which is a parcelable object included in the intent
        val result = intent.getParcelableExtra<Categories>("result")
        val categoryName = result?.name
        val categoryItems: MutableList<String>
        categoryItems = result?.itemList!!

        categoryNameTV.text = categoryName?.let { getString(it) }

        val listView = findViewById<AutoCompleteTextView>(R.id.footpathitems_listV)
        val nextPage = findViewById<Button>(R.id.next_button)


        //create the list of items for selection
        //https://www.youtube.com/watch?v=rxRuW2qZ2ZY


        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            categoryItems
        )

        listView.setAdapter(arrayAdapter)

       // listView.choiceMode = ListView.CHOICE_MODE_SINGLE

        var selectedItemName = ""


        listView.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this, "Item Selected: " + categoryItems[i], Toast.LENGTH_SHORT)
                .show()
            selectedItemName = categoryItems[i].toString()
        }

        //SL: this is to pass the selected category name and item to the next activity - for saving to Database
        fun passIntent(){
            val i = Intent(this, ReportIssueActivity::class.java).apply{
                putExtra("category", categoryName)
                putExtra("selectedItem", selectedItemName)
            }
            startActivity(i)
        }

        nextPage.setOnClickListener {
            passIntent()
        }

        //SL: press "Back" button to back to previous page
        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            onBackPressed()
        }



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