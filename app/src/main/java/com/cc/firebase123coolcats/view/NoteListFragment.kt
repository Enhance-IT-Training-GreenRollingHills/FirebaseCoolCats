package com.cc.firebase123coolcats.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cc.firebase123coolcats.databinding.NoteListBinding
import com.cc.firebase123coolcats.model.Note
import com.cc.firebase123coolcats.util.LogToConsole
import com.cc.firebase123coolcats.viewmodel.ListViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NoteListFragment : Fragment() {

    private lateinit var binding : NoteListBinding

    private val viewModel by viewModels<ListViewModel>()

    private val postReference = FirebaseDatabase.getInstance().reference.child("Note")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = NoteListBinding.inflate(inflater, container, false)
        return binding.root
        //return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        val adapter = NoteListAdapter()

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter



        postReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                LogToConsole.log("onDataChange, snapshot : $snapshot")

                if (snapshot.children.count() == 0) {
                    viewModel.newNoteAction()
                } else {
                    val newList = mutableListOf<Note>()

                    snapshot.children.forEach { data ->
                        LogToConsole.log("snapshot : $data")
                        LogToConsole.log("snapshot key : ${data.key}")

                        data.getValue(Note::class.java)?.let {
                            it.key = data.key
                            newList.add(it)
                        }
                    }
                    adapter.update(newList)
                }
            }
        })


        binding.newNoteButton.setOnClickListener {
            viewModel.newNoteAction()
        }

    }



}