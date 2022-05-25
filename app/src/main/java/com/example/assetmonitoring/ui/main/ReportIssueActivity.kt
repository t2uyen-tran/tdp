package com.example.assetmonitoring.ui.main

import android.Manifest
import android.app.Activity
import android.app.usage.ExternalStorageStats
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
import com.example.assetmonitoring.model.Case
import com.example.assetmonitoring.model.CaseContributor
import com.example.assetmonitoring.model.StaffJob
import com.example.assetmonitoring.model.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.*
import java.sql.Timestamp
import java.util.*


class ReportIssueActivity : AppCompatActivity(),View.OnClickListener{

    private lateinit var database: DatabaseReference
    private lateinit var database1: DatabaseReference
    private lateinit var database2: DatabaseReference
    private lateinit var database3: DatabaseReference
    private lateinit var category: String
    private lateinit var item: String
    private lateinit var location: String
    private lateinit var descText: String
    private lateinit var saveImageToInternalStorageString: String
    private lateinit var nameText: String
    private lateinit var mobileText: String
    private lateinit var emailText: String
    private lateinit var radioSexGroup: RadioGroup
    private lateinit var radioSexButton: RadioButton

    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var saveImageToInternalStorageURI :Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.report_issue_activity)

        /*val current = LocalDateTime.now()
        Log.e("now", "$current")*/
        val bundle = intent.extras
        if (bundle != null){
            category = bundle.getString("category").toString()
            item = bundle.getString("selectedItem").toString()
            location = bundle.getString("location").toString()
        }

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
        // camera and upload btn
        val uploadBtn = findViewById<Button>(R.id.uploadBtn)
        val cameraBtn = findViewById<ImageButton>(R.id.camera)
        //upload photo button and take photo button
        uploadBtn.setOnClickListener(this)
        cameraBtn.setOnClickListener(this)

        //db
        val submitBtn = findViewById<Button>(R.id.submit_buton)
        submitBtn.setOnClickListener(this)

        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()


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

            R.id.submit_buton -> {
                val descET = findViewById<EditText>(R.id.issueDescriptionInput_ET)
                descText = descET.text.toString()
                val nameET = findViewById<EditText>(R.id.nameInput_ET)
                nameText = nameET.text.toString()
                val mobileET = findViewById<EditText>(R.id.mobileInput_ET)
                mobileText = mobileET.text.toString()
                val emailET: EditText  = findViewById(R.id.emailInput_ET)
                emailText = emailET.text.toString()

                var categoryDB = category
                var itemDB = item
                var lastUpdated = ""
                var locationDB = location
                var descriptionDB = descText
                var photoURLDB = saveImageToInternalStorageString
                var statusDB = "New Case"
                var userIDDB = nameText
                var userMobileDB = mobileText
                var userEmailDB =emailText// get selected radio button from radioGroup
                var notifyDB = false
                // get selected radio button from radioGroup
                radioSexGroup =  findViewById(R.id.radio_group);
                val selectedId: Int = radioSexGroup.getCheckedRadioButtonId()

                // find the radiobutton by returned id

                // find the radiobutton by returned id
                radioSexButton = findViewById<View>(selectedId) as RadioButton

                Toast.makeText(
                    this@ReportIssueActivity,
                    radioSexButton.getText(), Toast.LENGTH_SHORT
                ).show()

                if(radioSexButton.getText() == "yes"){
                    var notify = true
                    notifyDB = notify

                }else {
                    var notify = false
                    notifyDB =notify
                }



                Toast.makeText(this@ReportIssueActivity, "Submit", Toast.LENGTH_SHORT)
                    .show()

                database = FirebaseDatabase.getInstance().getReference("cases")
                var key = database.push().getKey().toString()

                database1 = FirebaseDatabase.getInstance().getReference("cases/"+ key + "/contributors")
                var key1 = database1.push().getKey().toString()

                database2 = FirebaseDatabase.getInstance().getReference("users")
                val currentTime = Calendar.getInstance().time
                Log.e("currentTime: ", "$currentTime")

                database3 = FirebaseDatabase.getInstance().getReference("")
                var key3 = database3.push().getKey().toString()

                val ctb = CaseContributor(key1,Timestamp(currentTime.time).time,notifyDB,descriptionDB,photoURLDB)
                val contributors = listOf<CaseContributor>(ctb)

                val Case = Case(key,categoryDB,itemDB,locationDB,contributors,statusDB)
                val user = User(key1,userEmailDB,userMobileDB,userIDDB)
                val staffJob = StaffJob(key3,key1,key,"","")

                database.child(key).setValue(Case).addOnSuccessListener {
                    Toast.makeText(this@ReportIssueActivity, "Successfully insert", Toast.LENGTH_SHORT)
                        .show()

                }.addOnFailureListener{
                    Toast.makeText(this@ReportIssueActivity, "Failed insert", Toast.LENGTH_SHORT)
                        .show()
                }
                database.child(key).child("caseID").removeValue();


                database1.child(key1).setValue(ctb).addOnSuccessListener {
                    Toast.makeText(this@ReportIssueActivity, "Successfully insert", Toast.LENGTH_SHORT)
                        .show()

                }.addOnFailureListener{
                    Toast.makeText(this@ReportIssueActivity, "Failed insert", Toast.LENGTH_SHORT)
                        .show()
                }

                database1.child(key1).child("userID").removeValue();
                database1.child("0").removeValue();

                database2.child(key1).setValue(user).addOnSuccessListener {
                    Toast.makeText(this@ReportIssueActivity, "Successfully insert", Toast.LENGTH_SHORT)
                        .show()

                }.addOnFailureListener{
                    Toast.makeText(this@ReportIssueActivity, "Failed insert", Toast.LENGTH_SHORT)
                        .show()
                }

                database2.child(key1).child("userID").removeValue();

                val staffJobValues = staffJob.toMap()

                val childUpdates = hashMapOf<String, Any>(
                    "/jobs/$key3" to staffJobValues,
                )

                database3.updateChildren(childUpdates)

                /*database3.child(key3).setValue(staffJob).addOnSuccessListener {
                    Toast.makeText(this@ReportIssueActivity, "Successfully insert", Toast.LENGTH_SHORT)
                        .show()

                }.addOnFailureListener{
                    Toast.makeText(this@ReportIssueActivity, "Failed insert", Toast.LENGTH_SHORT)
                        .show()
                }
                database3.child(key3).child("jobID").removeValue();*/


                /*Log.e("Saved Image : ", "$caseIDDB")
                Log.e("Saved Image : ", "$categoryDB ")
                Log.e("Saved Image : ", "$itemDB")
                Log.e("Saved Image : ", "$locationDB")
                Log.e("Saved Image : ",  "$descriptionDB")
                Log.e("Saved Image : ",  "$photoURLDB")
                Log.e("Saved Image : ",  "$statusDB")
                Log.e("Saved Image : ",  "$userIDDB")
                Log.e("Saved Image : ",  "$userMobileDB")
                Log.e("Saved Image : ",  "$notify")*/
            }

        }

    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val ivPlaceImage  = findViewById<ImageView>(R.id.iv_place_image)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY) {
                if (data != null) {
                    val contentURI = data?.data!!
                    try {
                        // Here this is used to get an bitmap from URI
                        @Suppress("DEPRECATION")
                        val selectedImageBitmap =
                            MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                        val saveImageToInternalStorage =
                            saveImageToInternalStorage(selectedImageBitmap)
                        ivPlaceImage.setImageBitmap(selectedImageBitmap)
                        Log.e("Saved Image : ", "Path :: $saveImageToInternalStorage")
                        //saveImageToInternalStorageURI = contentURI
                        //saveImageToInternalStorageString = saveImageToInternalStorageURI.toString()
                        Log.e("ddd","$contentURI")

                        uploadPicture()

                        // Set the selected image from GALLERY to imageView.
                        Toast.makeText(this@ReportIssueActivity, "Successfully added from upload", Toast.LENGTH_SHORT)
                            .show()
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(this@ReportIssueActivity, "Failed!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }else if (requestCode == CAMERA) {
                val thumbnail: Bitmap = data!!.extras!!.get("data") as Bitmap // Bitmap from camera
                val bytes: ByteArrayOutputStream = ByteArrayOutputStream()
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
                val  bb = bytes.toByteArray()

                val saveImageToInternalStorage =
                    saveImageToInternalStorage(thumbnail)
                ivPlaceImage.setImageBitmap(thumbnail)


                //saveImageToInternalStorageURI = saveImageToInternalStorage
                //saveImageToInternalStorageString = saveImageToInternalStorage.toString()
                Log.e("Saved Image : ", "Path :: $saveImageToInternalStorage")


                uploadPictureFromCamera(bb)
                Toast.makeText(this@ReportIssueActivity, "Successfully added from camera", Toast.LENGTH_SHORT)
                    .show() // Set to the imageView.
            }

        }
    }

    private fun uploadPicture() {
        val randomKey = UUID.randomUUID().toString()
        // Create a reference to "mountains.jpg"
        val mountainsRef = storageReference.child("/" + randomKey)
        mountainsRef.putFile(saveImageToInternalStorageURI).addOnSuccessListener {
            Toast.makeText(this@ReportIssueActivity, "Successfully add to storage", Toast.LENGTH_SHORT)
                .show() // Set to the imageView.
            saveImageToInternalStorageString = "gs://asset-monitoring-6b16b.appspot.com/" + randomKey
        }.addOnFailureListener {
            Toast.makeText(this@ReportIssueActivity, "Failed add to storage", Toast.LENGTH_SHORT)
                .show()
        }
        // Create a reference to 'images/mountains.jpg'

    }
    fun uploadPictureFromCamera(bb:ByteArray?) {
        val randomKey = UUID.randomUUID().toString()
        // Create a reference to "mountains.jpg"
        val mountainsRef = storageReference.child("/" + randomKey)
        mountainsRef.putBytes(bb!!).addOnSuccessListener {
            Toast.makeText(this@ReportIssueActivity, "Successfully add to storage", Toast.LENGTH_SHORT)
                .show() // Set to the imageView.
            saveImageToInternalStorageString = "gs://asset-monitoring-6b16b.appspot.com/" + randomKey
        }.addOnFailureListener {
            Toast.makeText(this@ReportIssueActivity, "Failed add to storage", Toast.LENGTH_SHORT)
                .show()
        }
        // Create a reference to 'images/mountains.jpg'

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
                        Toast.makeText(this@ReportIssueActivity, "camera", Toast.LENGTH_SHORT).show()

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

