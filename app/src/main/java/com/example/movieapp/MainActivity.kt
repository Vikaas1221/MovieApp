package com.example.movieapp

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private val data: ArrayList<MovieData>? = null
    private var adapter: DataAdapter? = null
    internal var list: ArrayList<MovieData>? = null
    private  var textview:TextView?=null;
    private var intnetStatus:ImageView?=null;
    private var refreshSreenlayout:SwipeRefreshLayout?=null;
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);


        initViews();
        if (!isConnected())  // this part is to check for intennet connectivity
        {

            intnetStatus?.setVisibility(View.VISIBLE)
            refreshScreenIfConnected("Top_Rated")
        } else
        {
            intnetStatus?.setVisibility(View.GONE)
            parseJson("Top_Rated")
            refreshSreenlayout?.setOnRefreshListener {
                refreshSreenlayout?.isRefreshing=false;
            }
        }

    }

    private fun initViews()  // here inilizing the views
    {
        recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        textview=findViewById(R.id.type);
        intnetStatus=findViewById(R.id.InternetStatusimage);
        refreshSreenlayout=findViewById(R.id.refreshScreen);
        recyclerView!!.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(getApplicationContext())
        recyclerView!!.setLayoutManager(layoutManager)

    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        getMenuInflater().inflate(R.menu.menu, menu)
        return true

    }

    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        val id = item.getItemId()

        when (id) {

            R.id.Top_rated ->
            {
                if (!isConnected()) {    // In this if block   I am checking if the internet is on/ off
                    // If internet is off the internet off icon is shown on screen and if user  connects the intenet and try to load the screen by swiping the screen
                    // so again inner if block will again check for internet connection  and if internet connection is ON then it will load the data from api

                    intnetStatus?.setVisibility(View.VISIBLE)
                    recyclerView?.setVisibility(View.GONE)
                    refreshSreenlayout?.setOnRefreshListener {
                        if (isConnected())
                        {
                            intnetStatus?.setVisibility(View.GONE)
                            recyclerView?.setVisibility(View.VISIBLE)
                            parseJson("Top_Rated")
                            textview?.setText("Top Rated Movies");
                            refreshSreenlayout?.isRefreshing=false;

                        }
                        else
                        {
                            refreshSreenlayout?.isRefreshing=false;
                        }


                    }

                } else
                {
                    intnetStatus?.setVisibility(View.GONE)
                    recyclerView?.setVisibility(View.VISIBLE)
                    parseJson("Top_Rated")
                    textview?.setText("Top Rated Movies");
                    refreshSreenlayout?.isRefreshing=false
                }


            }
            R.id.Upcoming ->
            {
                if (!isConnected()) { // Same as the above if block working , the only difference is that here it load the data of upcoming movies

                    intnetStatus?.setVisibility(View.VISIBLE)
                    recyclerView?.setVisibility(View.GONE)
                    refreshSreenlayout?.setOnRefreshListener {
                        if (isConnected())
                        {
                            intnetStatus?.setVisibility(View.GONE)
                            recyclerView?.setVisibility(View.VISIBLE)
                            parseJson("Upcoming")
                            textview?.setText("Upcoming Movies");
                            refreshSreenlayout?.isRefreshing=false;

                        }
                        else
                        {
                            refreshSreenlayout?.isRefreshing=false;
                        }


                    }

                } else
                {
                    intnetStatus?.setVisibility(View.GONE)
                    recyclerView?.setVisibility(View.VISIBLE)
                    parseJson("Upcoming")
                    textview?.setText("Upcoming Movies")
                    refreshSreenlayout?.isRefreshing=false
                }


            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun isConnected(): Boolean // This is a function to check for mobile phone internet state
    {
        var connected = false
        try
        {
            val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = cm.activeNetworkInfo
            connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected
            return connected
        } catch (e: Exception)
        {
            Log.e("Connectivity Exception", e.message)
        }

        return connected
    }
    fun refreshScreenIfConnected(type: String)
    {
        refreshSreenlayout?.setOnRefreshListener {
            if (isConnected())
            {
                parseJson(type)
                intnetStatus?.setVisibility(View.GONE)
                refreshSreenlayout?.isRefreshing = false;

            } else
            {
                refreshSreenlayout?.isRefreshing = false;
            }
        }
    }

    private fun parseJson(type: String) { // This function here is to fetch the data from RESTFUL api using Retrofit
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val request = retrofit.create(RequestInterface::class.java)
        var call1: Call<SampleResponce>? = null
        if (type.equals("Top_Rated")) {   // Here in this if block I am checking  what user has selected from optionMenu and displays the data according to the user prefrence in same activity  just by changing the dataset in arraylist
            call1 = request.json
        } else if (type.equals("Upcoming")) {
            call1 = request.upcoming
        }

        call1!!.enqueue(object : Callback<SampleResponce>
        {
            @Override
            override fun onResponse(call: Call<SampleResponce>, response: Response<SampleResponce>) {
                val sampleResponce = response.body()
                list = sampleResponce?.movieData as ArrayList<MovieData>?
                adapter = DataAdapter(list)
                recyclerView!!.setAdapter(adapter)
                adapter!!.notifyDataSetChanged()
            }

            @Override
           override fun onFailure(call: Call<SampleResponce>, t: Throwable) {
                Toast.makeText(this@MainActivity, ""+ t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}
