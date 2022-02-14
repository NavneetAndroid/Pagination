package com.example.retrofiturl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.retrofiturl.model.User
import com.example.retrofiturl.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var adapter: UsersAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var page=1
  private var totalPage=1
    private var isLoading=false
    val swipeRefresh=findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
    val rvUsers=findViewById<RecyclerView>(R.id.rvUsers)
    val progressbar=findViewById<ProgressBar>(R.id.progressBar)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutManager= LinearLayoutManager(this)
        swipeRefresh.setOnRefreshListener(this)
        setupRecyclerView()
        getUsers(false)
        rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val visibleItemCount=layoutManager.childCount
                val pastVisibleItem=layoutManager.findFirstVisibleItemPosition()
                val total=adapter.itemCount
                if (!isLoading && page<totalPage){
                    if (visibleItemCount +pastVisibleItem>=total){
                        page++
                        getUsers(false)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })



    }

    private fun getUsers(isOnRefresh:Boolean) {
        isLoading=true
        if (!isOnRefresh) progressbar.visibility=View.VISIBLE
        val parameters=HashMap<String,String>()
        parameters["page"]=page.toString()
        Handler().postDelayed({
            RetrofitClient.instance.getUsers(parameters).enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    totalPage=response.body()?.totalPages!!
                    val listResponse: ArrayList<User>? =response.body()?.data
                    if (listResponse!=null)
                    {
                        adapter.addList(listResponse)
                    }
                    if (page==totalPage){
                        progressbar.visibility=View.GONE
                    }
                    else{
                        progressbar.visibility=View.VISIBLE
                    }

                    isLoading=false
                    swipeRefresh.isRefreshing=false



                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity,"${t.message}",Toast.LENGTH_LONG).show()

                }

            })

        },4000)


    }

    private fun setupRecyclerView() {
        rvUsers.setHasFixedSize(true)
        rvUsers.layoutManager=layoutManager
        adapter= UsersAdapter()
        rvUsers.adapter=adapter

    }

    override fun onRefresh() {
        adapter.clear()
        page=1
        getUsers(true)

    }

}