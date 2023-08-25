package com.example.loginapirest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.loginapirest.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setListeners()
//        getMembers()
    }

    private fun setListeners() {
        mBinding.swType.setOnCheckedChangeListener { button, isChecked ->
            button.text =
                if (isChecked) getString(R.string.main_type_login)
                else getString(R.string.main_type_register)
            mBinding.btnLogin.text = button.text
        }
        mBinding.btnLogin.setOnClickListener {
            login()
        }
        mBinding.btnMembers.setOnClickListener {
            launchMembersFragment()
        }
    }

    private fun launchMembersFragment() {
        val fragment = MembersFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

//        Indicar fragmento y donde
        fragmentTransaction.add(R.id.containerMain, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun login() {
        val typeMethod =
            if (mBinding.swType.isChecked) Constants.LOGIN_PATH
            else Constants.REGISTER_PATH
        val url = Constants.BASE_URL + Constants.API_PATH + typeMethod
        val email = mBinding.etEmail.text.toString().trim()
        val password = mBinding.etPassword.text.toString().trim()
        val jsonParams = JSONObject()
        if (email.isNotEmpty()) {
            jsonParams.put(Constants.EMAIL_PARAM, email)
        }
        if (password.isNotEmpty()) {
            jsonParams.put(Constants.PASSWORD_PARAM, password)
        }
        val jsonObjectRequest = object : JsonObjectRequest
            (Request.Method.POST, url, jsonParams, { response ->
            Log.i("Response", response.toString())
            //obtener valores
            val id = response.optString(Constants.ID_PROPERTY, Constants.ERROR_VALUE)
            val token = response.optString(Constants.TOKEN_PROPERTY, Constants.ERROR_VALUE)
            val result = if (id.equals(Constants.ERROR_VALUE))
                "${Constants.TOKEN_PROPERTY}:$token"
            else
                "${Constants.ID_PROPERTY}:$id, ${Constants.TOKEN_PROPERTY}:$token"
            updateUI(result)
        }, {
            it.printStackTrace()
            if (it.networkResponse.statusCode == 400)
                updateUI(getString(R.string.main_error_server))
        }) { // apartado para configurar bodyContent o headers
            override fun getHeaders(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["Content-Type"] = "application/json"
                return super.getHeaders()
            }
        }
        LoginApplication.reqResAPI.addToRequestQueue(jsonObjectRequest)
    }

    private fun updateUI(result: String) {
        mBinding.tvResult.visibility = View.VISIBLE
        mBinding.tvResult.text = result
    }
}