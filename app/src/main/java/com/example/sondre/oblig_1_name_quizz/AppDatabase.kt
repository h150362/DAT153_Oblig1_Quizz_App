package com.example.sondre.oblig_1_name_quizz

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import java.security.AccessControlContext


@Database(entities = arrayOf(Person::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao




        companion object {

            private var INSTANCE: AppDatabase? = null

            fun getInstance(context: Context): AppDatabase? {

                //INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                //    AppDatabase::class.java, "person.db").allowMainThreadQueries().build()

                if (INSTANCE == null) {
                    synchronized(AppDatabase::class) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase::class.java, "person.db").allowMainThreadQueries().build()//Må fjerne allowMainThreadQueires
                            //.build()
                    }
                }

                return INSTANCE

                    }

        }



/*
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "person.db").build()

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }



*/
}
