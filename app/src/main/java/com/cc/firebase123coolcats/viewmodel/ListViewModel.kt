package com.cc.firebase123coolcats.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cc.firebase123coolcats.model.Note
import com.cc.firebase123coolcats.util.LogToConsole
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListViewModel (application: Application) : AndroidViewModel(application) {

    private val noteReference = FirebaseDatabase.getInstance().getReference("Note")

    fun newNoteAction () {
        val note = Note()
        noteReference.push().setValue(note)

    }



}