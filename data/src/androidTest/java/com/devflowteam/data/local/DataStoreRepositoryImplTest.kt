package com.devflowteam.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.test.core.app.ApplicationProvider
import com.devflowteam.core.utils.Settings
import com.devflowteam.data.repository.DataStoreRepositoryImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.nio.file.Files

@ExperimentalCoroutinesApi
class DataStoreRepositoryImplTest  {

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var repository: DataStoreRepositoryImpl

    @Before
    fun setup() {
        dataStore = PreferenceDataStoreFactory.create(
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { Files.createTempFile("test_prefs", ".preferences_pb").toFile() }
        )

        val context = ApplicationProvider.getApplicationContext<Context>()
        repository = DataStoreRepositoryImpl(context = context)
    }

    @After
    fun tearDown() = runTest {
        dataStore.edit { it.clear() }
    }

    @Test
    fun writeAndReadBooleanValue() = runTest {
        repository.write(Settings.DarkMode, true)

        val result = repository.read(Settings.DarkMode).first()

        assertEquals(true, result)
    }

    @Test
    fun readDefaultValueIfNoDataExists() = runTest {
        val result = repository.read(Settings.FirstLaunch).first()

        assertEquals(true, result)
    }
}