package com.example.mortargui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.sign_in.*


class Singin_activity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in)

        goToResponseContact.setOnClickListener { startActivity(Intent(this, Sample_Activity:: class.java)) }




    }
}
