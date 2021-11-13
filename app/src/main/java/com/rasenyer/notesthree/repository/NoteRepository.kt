package com.rasenyer.notesthree.repository

import androidx.lifecycle.LiveData
import com.rasenyer.notesthree.db.NoteDatabase
import com.rasenyer.notesthree.model.Note

class NoteRepository(noteDatabase: NoteDatabase) {

    private val noteDao = noteDatabase.getNoteDao()

    suspend fun insert(note: Note) { noteDao.insert(note) }

    suspend fun update(note: Note) { noteDao.update(note) }

    suspend fun delete(note: Note) { noteDao.delete(note) }

    suspend fun deleteAll() { noteDao.deleteAll() }

    fun searchByTitle(query: String?): LiveData<List<Note>> { return noteDao.searchByTitle(query) }

    val getAll: LiveData<List<Note>> = noteDao.getAll()

}