package com.rasenyer.notesthree.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note_table")
data class Note(

    @PrimaryKey(autoGenerate = true)
    var id          :   Int,
    var title       :   String,
    var description :   String,
    var date        :   String,
    var image       :   String

): Serializable
