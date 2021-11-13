package com.rasenyer.notesthree.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rasenyer.notesthree.R
import com.rasenyer.notesthree.model.Note
import com.rasenyer.notesthree.ui.fragments.HomeFragmentDirections
import kotlinx.android.synthetic.main.item_note.view.*
import java.util.*

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Note>() {

        override fun areItemsTheSame(oldNote: Note, newNote: Note): Boolean {
            return oldNote.id == newNote.id
        }

        override fun areContentsTheSame(oldNote: Note, newNote: Note): Boolean {
            return oldNote == newNote
        }

    }

    val listNotes = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent,false)
        return NoteViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val note = listNotes.currentList[position]

        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.timeInMillis = note.date.toLong()
        val dateFormat = DateFormat.format("EEEE, d MMMM yyyy", calendar).toString()

        holder.itemView.apply {

            if (note.image.isNotEmpty()){
                mImageView.visibility = View.VISIBLE
                mImageView.setImageURI(Uri.parse(note.image))
            } else {
                mImageView.visibility = View.GONE
            }

            mTextViewTitle.text = note.title
            mTextViewDescription.text = note.description
            mTextViewDate.text = dateFormat

        }.setOnClickListener{ view ->

            val direction = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(note)
            view.findNavController().navigate(direction)

        }

    }

    override fun getItemCount(): Int { return listNotes.currentList.size }

}