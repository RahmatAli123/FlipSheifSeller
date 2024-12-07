package com.example.flipsheifseller

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private var auth=FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
Handler(Looper.getMainLooper()).postDelayed({
    if (auth.currentUser!=null){
        startActivity(Intent(this,Upload_Product_Activity::class.java))

    }
    else{
        startActivity(Intent(this,LoginActivity::class.java))
    }
    finish()
}, 2000)


    }
}