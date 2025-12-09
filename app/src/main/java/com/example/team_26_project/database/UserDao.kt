package com.example.team_26_project.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUsersCount(): Int


    // Safe
    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun getUser(username: String, password: String): User?

    //vulnerable
    @RawQuery
    fun loginUnsafe(query: SupportSQLiteQuery): List<User>

    //query string
    fun buildVulnerableLoginQuery(username: String, password: String): SupportSQLiteQuery {
        val unsafeSql = "SELECT * FROM users WHERE username = '$username' AND password = '$password'"
        return androidx.sqlite.db.SimpleSQLiteQuery(unsafeSql)
    }
}