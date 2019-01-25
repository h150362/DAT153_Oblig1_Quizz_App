package com.example.sondre.oblig_1_name_quizz

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity
data class Person(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "first_name") var first_name: String?,
    @ColumnInfo(name = "path") var picturePath: String?
){
    constructor():this(0,"","")
}
