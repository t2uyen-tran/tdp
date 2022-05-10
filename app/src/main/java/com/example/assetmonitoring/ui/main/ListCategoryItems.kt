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

class ListCategoryItems : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onStart()called")
        setContentView(R.layout.list_category_items)

        val categoryNameTV = findViewById<TextView>(R.id.categoryListTitle_textV)

        //SL: received the details of the selected category which is a parcelable object included in the intent
        val result = intent.getParcelableExtra<Categories>("result")
        val categoryName = result?.name
        val categoryItems: MutableList<String>
        categoryItems = result?.itemList!!

        categoryNameTV.text = categoryName?.let { getString(it) }

        val listView = findViewById<ListView>(R.id.footpathitems_listV)
        val nextPage = findViewById<Button>(R.id.next_button)

        // camera and upload btn
        val uploadBtn = findViewById<Button>(R.id.uploadBtn)
        val cameraBtn = findViewById<ImageButton>(R.id.camera)

        //create the list of items for selection
        //https://www.youtube.com/watch?v=rxRuW2qZ2ZY


        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_single_choice,
            categoryItems
        )

        listView.adapter = arrayAdapter

        listView.choiceMode = ListView.CHOICE_MODE_SINGLE

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


        //upload photo button and take photo button
        uploadBtn.setOnClickListener(this)
        cameraBtn.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.uploadBtn -> {
                val pictureDialog = AlertDialog.Builder(this)
                pictureDialog.setTitle("Select Action")
                val pictureDialogItems =
                    arrayOf("Select photo from gallery")
                pictureDialog.setItems(
                    pictureDialogItems
                ) { _, which ->
                    when (which) {
                        // Here we have create the methods for image selection from GALLERY
                        0 -> choosePhotoFromGallery()
                    }
                }
                pictureDialog.show()
            }
            R.id.camera -> {
                val pictureDialog = AlertDialog.Builder(this)
                pictureDialog.setTitle("Select Action")
                val pictureDialogItems =
                    arrayOf("Capture photo from camera")
                pictureDialog.setItems(
                    pictureDialogItems
                ) { _, which ->
                    when (which) {
                        // Here we have create the methods for image selection from GALLERY
                        0 -> takePhotoFromCamera()
                    }
                }
                pictureDialog.show()
            }
        }

    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val ivPlaceImage  = findViewById<ImageView>(R.id.iv_place_image)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY) {
                if (data != null) {
                    val contentURI = data.data
                    try {
                        // Here this is used to get an bitmap from URI
                        @Suppress("DEPRECATION")
                        val selectedImageBitmap =
                            MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                        val saveImageToInternalStorage =
                            saveImageToInternalStorage(selectedImageBitmap)
                        ivPlaceImage.setImageBitmap(selectedImageBitmap)
                        Log.e("Saved Image : ", "Path :: $saveImageToInternalStorage")

                        // Set the selected image from GALLERY to imageView.
                        Toast.makeText(this@ListCategoryItems, "Successfully added from upload", Toast.LENGTH_SHORT)
                            .show()
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(this@ListCategoryItems, "Failed!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }else if (requestCode == CAMERA) {

                val thumbnail: Bitmap = data!!.extras!!.get("data") as Bitmap // Bitmap from camera
                val saveImageToInternalStorage =
                    saveImageToInternalStorage(thumbnail)
                ivPlaceImage.setImageBitmap(thumbnail)
                Log.e("Saved Image : ", "Path :: $saveImageToInternalStorage")
                Toast.makeText(this@ListCategoryItems, "Successfully added from camera", Toast.LENGTH_SHORT)
                    .show() // Set to the imageView.
            }

        }
    }

    private fun takePhotoFromCamera() {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    // Here after all the permission are granted launch the CAMERA to capture an image.
                    if (report!!.areAllPermissionsGranted()) {
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(intent, CAMERA)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    showRationalDialogForPermissions()
                }
            }).onSameThread()
            .check()
        Toast.makeText(this, "camera", Toast.LENGTH_SHORT).show()
    }

    private fun choosePhotoFromGallery() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

                    // Here after all the permission are granted launch the gallery to select and image.
                    if (report!!.areAllPermissionsGranted()) {
                        val galleryIntent = Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )

                        startActivityForResult(galleryIntent, GALLERY)
                        Toast.makeText(this@ListCategoryItems, "camera", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    showRationalDialogForPermissions()
                }
            }).onSameThread()
            .check()
    }
    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this)
            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog,
                                           _ ->
                dialog.dismiss()
            }.show()
    }
    private fun saveImageToInternalStorage(bitmap: Bitmap): Uri {

        // Get the context wrapper instance
        val wrapper = ContextWrapper(applicationContext)

        // Initializing a new file
        // The bellow line return a directory in internal storage
        /**
         * The Mode Private here is
         * File creation mode: the default mode, where the created file can only
         * be accessed by the calling application (or all applications sharing the
         * same user ID).
         */
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)

        // Create a file to save the image
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // Compress bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            // Flush the stream
            stream.flush()

            // Close stream
            stream.close()
        } catch (e: IOException) { // Catch the exception
            e.printStackTrace()
        }

        // Return the saved image uri
        return Uri.parse(file.absolutePath)
    }
    companion object {
        private const val GALLERY = 1
        private const val CAMERA = 2
        private const val IMAGE_DIRECTORY = "AssetImages"
        // A constant variable for place picker
        private const val PLACE_AUTOCOMPLETE_REQUEST_CODE = 3
    }
}