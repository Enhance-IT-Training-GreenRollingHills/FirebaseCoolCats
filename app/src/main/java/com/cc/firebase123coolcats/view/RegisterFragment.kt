package com.cc.firebase123coolcats.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cc.firebase123coolcats.databinding.RegisterLayoutBinding
import com.cc.firebase123coolcats.util.LogToConsole
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {

    private lateinit var binding : RegisterLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.sendRegisterEmailButton.setOnClickListener {
            val email = binding.editTextEmailForRegisteration.text.toString()
            val password = binding.editTextPasswordForRegister.text.toString()
            if(email.isNotBlank() && password.isNotBlank()) {
                createAccount(email, password)

            }
        }

    }

    private fun createAccount (email:String, password:String) {
        val auth = Firebase.auth

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //create success
                    LogToConsole.log("createUser with email success")
                    val activity = context as? MainActivity
                    activity?.presentNextFragment(LoginFragment())
                } else {
                    LogToConsole.log("createUser with email failure : ${task.exception}")
                    Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

}