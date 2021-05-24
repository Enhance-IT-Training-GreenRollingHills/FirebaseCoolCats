package com.cc.firebase123coolcats.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import com.cc.firebase123coolcats.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    //private val postReference = FirebaseDatabase.getInstance().reference.child("Recipe")

    var isInProgress = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val dish = Food("mac and cheese", "American", 700, "Macaroni, Cheese, Butter, Almond Milk")

        //postReference.push().setValue(dish)

        //val key = reference.push()

        /*val postList = MutablelistOf<Food>()

        postReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange (snapshot : DataSnapshot) {

                snapshot.children.forEach {


                }
            }

            override fun onCancelled(error : DatabaseError) {

            }

        })*/

        if(savedInstanceState == null) {

            val currentUser = Firebase.auth.currentUser

            if (currentUser != null) {
                val noteListFragment = NoteListFragment()

                presentNextFragment(noteListFragment)
            } else {
                val loginFragment = LoginFragment()
                presentNextFragment(loginFragment)
            }


        }



    }

    fun presentNextFragment (fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(fragment::class.java.name)
                .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    //data class Food (val dishName:String, val type : String, val calories:Int, val receipe:String)

}