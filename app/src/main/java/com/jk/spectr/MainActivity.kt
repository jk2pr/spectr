package com.jk.spectr

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity() {


    lateinit var searchView: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }




}
