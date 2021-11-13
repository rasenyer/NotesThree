package com.rasenyer.notesthree.ui.fragments

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rasenyer.notesthree.R
import com.rasenyer.notesthree.databinding.FragmentDetailsBinding
import com.rasenyer.notesthree.model.Note
import com.rasenyer.notesthree.ui.activity.MainActivity
import com.rasenyer.notesthree.vm.NoteViewModel
import java.util.*

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel
    private val detailsFragmentArgs: DetailsFragmentArgs by navArgs()
    private lateinit var currentNote: Note

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel
        currentNote = detailsFragmentArgs.note!!

        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.timeInMillis = currentNote.date.toLong()
        val dateFormat = DateFormat.format("EEEE, d MMMM yyyy, HH:mm:ss", calendar).toString()

        binding.mImageView.setImageURI(Uri.parse(currentNote.image))
        binding.mTextViewTitle.text = currentNote.title
        binding.mTextViewDescription.text = currentNote.description
        binding.mTextViewDate.text = dateFormat

        binding.mButtonEdit.setOnClickListener {
            val direction = DetailsFragmentDirections.actionDetailsFragmentToEditFragment(currentNote)
            view.findNavController().navigate(direction)
        }
        binding.mButtonDelete.setOnClickListener { delete() }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun delete(){

        AlertDialog.Builder(activity).apply {
            setTitle(R.string.delete_note)
            setMessage(R.string.are_you_sure_you_want_to_permanently_delete_this_note)
            setCancelable(false)
            setPositiveButton(R.string.delete) { _, _ ->
                noteViewModel.delete(currentNote)
                if (!findNavController().popBackStack()) activity?.finish()
            }
            setNegativeButton(R.string.cancel, null)
        }.create().show()

    }

}