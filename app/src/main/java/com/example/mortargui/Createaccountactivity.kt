package com.example.mortargui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.accountb.*

class Createaccountactivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.accountb)

        goToSignedIn.setOnClickListener { startActivity(Intent(this, Singin_activity:: class.java)) }

    }
}