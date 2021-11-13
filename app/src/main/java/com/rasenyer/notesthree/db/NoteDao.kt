package com.rasenyer.notesthree.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rasenyer.notesthree.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM note_table WHERE title LIKE :query")
    fun searchByTitle(query: String?): LiveData<List<Note>>

    @Query("SELECT * FROM note_table ORDER BY id DESC")
    fun getAll(): LiveData<List<Note>>

}