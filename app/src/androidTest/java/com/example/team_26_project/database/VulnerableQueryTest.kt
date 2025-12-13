package com.example.team_26_project.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class VulnerableQueryTest {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var supportDb: SupportSQLiteDatabase

    @Before
    fun setup() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        userDao = db.userDao()
        supportDb = db.openHelper.writableDatabase

        userDao.insertUser(User(username = "admin@gmail.com", password = "1234"))
        userDao.insertUser(User(username = "user@gmail.com", password = "pass"))
    }

    @After
    fun tearDown() {
        db.close()
    }

    //1: login with wrong password
    @Test
    fun loginWrongPassword() {
        val query = userDao.buildVulnerableLoginQuery(
            "admin@gmail.com",
            "wrong_password"
        )

        val cursor = supportDb.query(query)
        val resultCount = cursor.count
        cursor.close()

        Assert.assertEquals(0, resultCount)
    }

    //2: SQL Injection bypasses
    @Test
    fun sqlInjection() {
        val injectedPassword = "' OR '1'='1"

        val query = userDao.buildVulnerableLoginQuery(
            "admin@gmail.com",
            injectedPassword
        )

        val cursor = supportDb.query(query)
        val resultCount = cursor.count
        cursor.close()

        Assert.assertTrue(resultCount > 0)
    }
}