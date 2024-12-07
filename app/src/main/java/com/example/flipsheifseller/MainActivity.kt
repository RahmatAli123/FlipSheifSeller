package com.example.flipsheifseller

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flipsheifseller.Fragment.HomeFragment
import com.example.flipsheifseller.Fragment.ProductFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottom_NavigationView)
        homeFragment(HomeFragment())
        bottomNavigationView.setOnItemSelectedListener { item->
            if (item.itemId==R.id.home){
                homeFragment(HomeFragment())
                return@setOnItemSelectedListener true

            }
            else if (item.itemId==R.id.product){
                homeFragment(ProductFragment())
                return@setOnItemSelectedListener true
            }
            else if (item.itemId==R.id.order){
                homeFragment(ProductFragment())
                return@setOnItemSelectedListener true
            }
            else{
                return@setOnItemSelectedListener false
            }


        }

    }

    private fun homeFragment(fragment: Fragment){
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment,fragment)
        transaction.commit()

    }
}