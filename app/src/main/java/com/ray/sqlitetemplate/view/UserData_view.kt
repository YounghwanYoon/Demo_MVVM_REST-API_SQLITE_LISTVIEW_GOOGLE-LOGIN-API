package com.ray.sqlitetemplate.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.ray.sqlitetemplate.R
import com.ray.sqlitetemplate.repository.model.UserData
import com.ray.sqlitetemplate.view.adapter.UserRecycleAdapter
import com.ray.sqlitetemplate.view_model.UserDataViewModel

class UserData_view : AppCompatActivity() {
    //UserData
    private lateinit var mUserData:ArrayList<UserData>
    private lateinit var mUserRecyclerAdapter: UserRecycleAdapter
    private lateinit var mProgressBar:ProgressBar
    private lateinit var mUserDataViewModel:UserDataViewModel

    companion object {
        // private val TAG = UserData_view::class!!.simpleName
        private val TAG = "UserData_view"
    }

    override fun onCreate(savedInstanceState: Bundle?) {1
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_data_view)

        mProgressBar = findViewById(R.id.progress_bar)

        mUserDataViewModel = ViewModelProviders.of(this).get(UserDataViewModel::class.java /*type of viewmodel*/)
        mUserDataViewModel.getUserDatas().observe(this, object: Observer<List<UserData>>{
            override fun onChanged(t: List<UserData>?) {
                mUserRecyclerAdapter.notifyDataSetChanged()
            }
        })

       // initUserData()
        initRecycleView()
    }

    //Call ViewModel to request userData
    /*private fun initUserData(){
        mUserData = ArrayList()
        mUserData.add(UserData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSKH3Qd3RP33Q5XxcRMrLXYhYGRu_dxvpJCIBEU_MlAudC1ev-P8A","twiter@twiter.com"))
        mUserData.add(UserData("https://cdn.pixabay.com/photo/2019/05/11/19/24/chicks-4196423_1280.jpg", "ray", "password", "ray.yoon87@gmail.com"))
        mUserData.add(UserData("https://images.unsplash.com/photo-1489084917528-a57e68a79a1e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80",  "Ray", "Yoon", "ray.yoon87@gmail.com"))
        mUserData.add(UserData("data:image/png;base64,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", "Stella", "Park", "ray.yoon87@gmail.com"))

    }
*/
    private fun initRecycleView(){
        val userRecycleView: RecyclerView = findViewById(R.id.recycler_view)
        //inside UseRecycleAdapter, we pass in array/list of data in general.
        // In MVVM design, data must retrieve from ViewModel, therefore, we pass in ViewModel and call get method to retrieve LiveData.
        mUserRecyclerAdapter = UserRecycleAdapter(mUserDataViewModel.getUserDatas().value as ArrayList<UserData>, this)
        userRecycleView.adapter = mUserRecyclerAdapter
        userRecycleView.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
    }

    private fun showProgressBar(){ this.mProgressBar.visibility= VISIBLE }
    private fun hideProgressBar(){this.mProgressBar.visibility= GONE}

}
