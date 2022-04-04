package com.example.roomhomeworkweek5.data

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*



@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM donuts_table ORDER BY day ASC")
    fun readAllData(): LiveData<List<User>>

    //@Query("SELECT * FROM donuts_table WHERE day = :day")
    //fun findByDay(day: Long): User?

}