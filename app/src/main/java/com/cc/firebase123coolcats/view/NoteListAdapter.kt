package com.cc.firebase123coolcats.view

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cc.firebase123coolcats.databinding.NoteItemBinding
import com.cc.firebase123coolcats.model.Note
import com.cc.firebase123coolcats.util.LogToConsole
import com.google.firebase.database.FirebaseDatabase

class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {

    private var list = emptyList<Note>()

    private val noteReference = FirebaseDatabase.getInstance().getReference("Note")

    private var lastTouchRow:Int? = null

    inner class NoteViewHolder(binding: NoteItemBinding, watcher: MyCustomEditTextListener) :RecyclerView.ViewHolder (binding.root) {

        val editText = binding.editTextNote
        val textWatcher = watcher
        init {
            editText.addTextChangedListener(textWatcher)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding, MyCustomEditTextListener())
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val data = list[position]
        LogToConsole.log("onBindViewHolder")
        LogToConsole.log("position : $position")
        LogToConsole.log("data string : ${data.content}")
        LogToConsole.log("lastTouch : $lastTouchRow")

        holder.textWatcher.updatePosition(position)

        holder.editText.setText(data.content)

        holder.editText.setOnTouchListener(null)

        holder.editText.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_UP -> {
                        LogToConsole.log("editText touched")
                        lastTouchRow = position

                    }
                }

                return v?.onTouchEvent(event) ?: true
            }

        })



        if (lastTouchRow == position) {
            LogToConsole.log("about to request focus")
            holder.editText.requestFocus()
            holder.editText.setSelection(holder.editText.text.toString().length)
            //lastTouchRow = null
        }


    }

    fun update (list: List<Note>) {
        /* TODO : implement a better update method.
        *   Use a list adapter and diffUtil. Or at update one row at
        *   a time.  */

        this.list = list
        LogToConsole.log("update, list is : ${this.list}")

        notifyDataSetChanged()
    }

    inner class MyCustomEditTextListener : TextWatcher {
        private var position = 0
        fun updatePosition(position: Int) {
            this.position = position
        }

        override fun beforeTextChanged(
            charSequence: CharSequence,
            i: Int,
            i2: Int,
            i3: Int
        ) {
            // no op
        }

        override fun onTextChanged(
            charSequence: CharSequence,
            i: Int,
            i2: Int,
            i3: Int
        ) {
            // no op

        }


        override fun afterTextChanged(editable: Editable) {
            LogToConsole.log("afterTextChanged")
            LogToConsole.log("position : $position")
            val data = list[position]

            data.key?.let {key ->
                LogToConsole.log("key : $key")
                val contentRef = noteReference.child("$key/content")

                contentRef.setValue(editable.toString())
            }

        }
    }
}