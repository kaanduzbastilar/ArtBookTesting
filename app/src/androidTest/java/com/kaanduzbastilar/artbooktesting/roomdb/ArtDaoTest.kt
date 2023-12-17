package com.kaanduzbastilar.artbooktesting.roomdb

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.kaanduzbastilar.artbooktesting.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database: ArtDatabase

    //private lateinit var database: ArtDatabase
    private lateinit var dao: ArtDao

    @Before
    fun setup(){
        /*
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), ArtDatabase::class.java
        ).allowMainThreadQueries().build()

         */
        hiltRule.inject()

        dao = database.artDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertArtTesting() = runBlocking{

        val exampleArt = Art("Mona Lisa", "Da Vinci", 1700, "test.com", 1)
        dao.insertArt(exampleArt)

        val list = dao.observeArts().getOrAwaitValue()

        assertThat(list).contains(exampleArt)

    }

    @Test
    fun deleteArtTesting() = runBlocking{

        val exampleArt = Art("Mona Lisa", "Da Vinci", 1700, "test.com", 1)
        dao.insertArt(exampleArt)
        dao.deleteArt(exampleArt)

        val list = dao.observeArts().getOrAwaitValue()

        assertThat(list).doesNotContain(exampleArt)

    }

}