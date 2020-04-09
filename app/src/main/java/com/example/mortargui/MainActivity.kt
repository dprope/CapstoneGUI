package com.example.mortargui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gotocreatacc.setOnClickListener {
            startActivity(Intent(this, Createaccountactivity::class.java))
        }


        gotosigning.setOnClickListener {
            startActivity(Intent(this, Singin_activity::class.java))

        }

    }
}
