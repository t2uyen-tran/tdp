package com.example.assetmonitoring.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assetmonitoring.ui.council.OutstandingActivity
import com.example.assetmonitoring.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        when(val currentUser = auth.currentUser) {
            null -> auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        startActivity(Intent(this@LauncherActivity, MainActivity::class.java))
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        finishAffinity()
                    }
                }
            else -> {
                startActivity(Intent(this@LauncherActivity,
                    if (currentUser.isAnonymous) MainActivity::class.java
                    else OutstandingActivity::class.java)).also {
                    finish()
                }
            }
        }
    }
}