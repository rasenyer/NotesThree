package com.rasenyer.notesthree.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rasenyer.notesthree.R
import com.rasenyer.notesthree.adapter.NoteAdapter
import com.rasenyer.notesthree.databinding.FragmentHomeBinding
import com.rasenyer.notesthree.model.Note
import com.rasenyer.notesthree.ui.activity.MainActivity
import com.rasenyer.notesthree.vm.NoteViewModel

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel

        binding.mSearchView.setOnQueryTextListener(this)
        binding.mImageViewDeleteAll.setOnClickListener { deleteAll() }
        binding.mButtonAdd.setOnClickListener { findNavController().navigate(R.id.action_HomeFragment_to_AddFragment) }

        setUpRecyclerView()

    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        if (query != null) { searchByTitle(query) }
        return true

    }

    override fun onQueryTextChange(newText: String?): Boolean {

        if (newText != null){ searchByTitle(newText) }
        return true

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerView(){

        noteAdapter = NoteAdapter()

        binding.mRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        activity?.let {
            noteViewModel.getAll.observe(viewLifecycleOwner, { listNotes->
                noteAdapter.listNotes.submitList(listNotes)
                updateUI(listNotes)
            })
        }

    }

    private fun updateUI(listNotes: List<Note>){

        if (listNotes.isNotEmpty()){
            binding.mRecyclerView.visibility = View.VISIBLE
            binding.mTextViewNoNotes.visibility = View.GONE
        } else {
            binding.mRecyclerView.visibility = View.GONE
            binding.mTextViewNoNotes.visibility = View.VISIBLE
        }

    }

    private fun searchByTitle(query: String?){

        val searchQuery = "%$query%"

        noteViewModel.searchByTitle(searchQuery).observe(this, { listNotes ->
            noteAdapter.listNotes.submitList(listNotes)
        })

    }

    private fun deleteAll(){

        AlertDialog.Builder(activity).apply {
            setTitle(R.string.delete_all_notes)
            setMessage(R.string.are_you_sure_you_want_to_delete_all_notes)
            setCancelable(false)
            setPositiveButton(R.string.delete) { _, _ -> noteViewModel.deleteAll() }
            setNegativeButton(R.string.cancel, null)
        }.create().show()

    }

}