package com.example.loginapirest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.loginapirest.databinding.FragmentMembersBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MembersFragment : Fragment() {
    private lateinit var mBinding: FragmentMembersBinding
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: MemberAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentMembersBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()

    }

    private fun setupRecycler() {
        mAdapter = MemberAdapter(mutableListOf())
        mLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        getMembers()
        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mLinearLayoutManager
            adapter = mAdapter
        }

    }

    private fun getMembers() {
        val url = Constants.BASE_URL + Constants.API_PATH + Constants.USERS_PATH
        var memberList = mutableListOf<Member>()
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            val jsonList = response.optJSONArray(Constants.MEMBER_PROPERTY)?.toString()
            if (jsonList != null) {
                val listType = object : TypeToken<List<Member>>() {}.type
                memberList = Gson().fromJson(jsonList, listType)
                mAdapter.setMembers(memberList)
            }
        }, {
            it.printStackTrace()
        })
        LoginApplication.reqResAPI.addToRequestQueue(jsonObjectRequest)
    }


}