package com.example.sondre.oblig_1_name_quizz

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query


@Dao
interface PersonDao {
        @Query("SELECT * FROM person")
        fun getAll(): List<Person>

        @Query("SELECT * FROM person WHERE uid IN (:userIds)")
        fun loadAllByIds(userIds: IntArray): List<Person>

        @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
                "last_name LIKE :last LIMIT 1")
        fun findByName(first: String, last: String): Person

        @Insert
        fun insertAll(vararg users: Person)

        @Delete
        fun delete(user: Person)
    }


