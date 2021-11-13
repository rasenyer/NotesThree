package com.rasenyer.notesthree.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.rasenyer.notesthree.db.NoteDatabase
import com.rasenyer.notesthree.model.Note
import com.rasenyer.notesthree.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {

    private val noteRepository: NoteRepository

    fun insert(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insert(note)
        }
    }

    fun update(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.update(note)
        }
    }

    fun delete(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.delete(note)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteAll()
        }
    }

    fun searchByTitle(query: String?): LiveData<List<Note>> {
        return noteRepository.searchByTitle(query)
    }

    val getAll: LiveData<List<Note>>

    init {
        val noteDatabase = NoteDatabase.getDatabase(application)
        noteRepository = NoteRepository(noteDatabase)
        getAll = noteRepository.getAll
    }

}