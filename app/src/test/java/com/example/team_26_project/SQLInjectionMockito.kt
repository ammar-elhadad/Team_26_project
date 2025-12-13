package com.example.team_26_project

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteQuery
import android.database.Cursor
import com.example.team_26_project.database.UserDao
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class SQLInjectionMockito {

    private lateinit var userDao: UserDao
    private lateinit var supportDb: SupportSQLiteDatabase
    private lateinit var cursor: Cursor

    @Before
    fun setup() {
        userDao = mock()
        supportDb = mock()
        cursor = mock()
    }

    // 1: wrong password
    @Test
    fun loginWrongPassword() { //empty list and closed
        val query = mock<SupportSQLiteQuery>()

        whenever(userDao.buildVulnerableLoginQuery(any(), any()))
            .thenReturn(query)

        whenever(supportDb.query(query))
            .thenReturn(cursor)

        whenever(cursor.moveToNext())
            .thenReturn(false)

        val result = executeVulnerableQuery("admin@gmail.com", "wrong")

        assertTrue(result.isEmpty())
        verify(cursor).close()
    }

    //2: SQL Injection bypasses
    @Test
    fun sqlInjection() {
        val query = mock<SupportSQLiteQuery>()

        whenever(userDao.buildVulnerableLoginQuery(any(), any()))
            .thenReturn(query)

        whenever(supportDb.query(query))
            .thenReturn(cursor)

        whenever(cursor.moveToNext())
            .thenReturn(true, false)

        whenever(cursor.getColumnIndexOrThrow("username"))
            .thenReturn(0)

        whenever(cursor.getColumnIndexOrThrow("password"))
            .thenReturn(1)

        whenever(cursor.getString(0))
            .thenReturn("admin@gmail.com")

        whenever(cursor.getString(1))
            .thenReturn("1234")

        val result = executeVulnerableQuery("admin@gmail.com", "' OR '1'='1")

        assertEquals(1, result.size)
        assertEquals("admin@gmail.com", result[0].first)
        assertEquals("1234", result[0].second)
    }

    //helper
    private fun executeVulnerableQuery(
        username: String,
        password: String
    ): List<Pair<String, String>> {

        val query = userDao.buildVulnerableLoginQuery(username, password)
        val cursor = supportDb.query(query)

        val list = mutableListOf<Pair<String, String>>()
        while (cursor.moveToNext()) {
            val u = cursor.getString(cursor.getColumnIndexOrThrow("username"))
            val p = cursor.getString(cursor.getColumnIndexOrThrow("password"))
            list.add(u to p)
        }
        cursor.close()
        return list
    }
}
