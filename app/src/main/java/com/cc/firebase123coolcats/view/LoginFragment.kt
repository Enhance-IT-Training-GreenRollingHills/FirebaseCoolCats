package com.cc.firebase123coolcats.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cc.firebase123coolcats.databinding.LoginLayoutBinding
import com.cc.firebase123coolcats.util.LogToConsole
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private lateinit var binding: LoginLayoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            if (email.isNotBlank() && password.isNotBlank()) {
                signIn(email, password)

            }
        }

        binding.registerButton.setOnClickListener {
            val activity = context as? MainActivity

            activity?.presentNextFragment(RegisterFragment())
        }
    }

    private fun signIn (email:String, password:String) {
        val auth = Firebase.auth
        val mainActivity = context as? MainActivity
        mainActivity?.isInProgress = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
               mainActivity?.isInProgress = false
                if (task.isSuccessful) {
                    //Sign in success, update UI with the signed - in user's information
                    LogToConsole.log("sign in with email success")
                    val activity = context as? MainActivity
                    activity?.presentNextFragment(NoteListFragment())
                } else {
                    //if sign in fails, display a message to user.
                    LogToConsole.log("sign in with email failure : ${task.exception}")
                    Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }



}