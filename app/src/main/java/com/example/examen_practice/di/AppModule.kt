package com.example.examen_practice.di // Use your actual package name

import android.content.Context
import androidx.room.Room
import com.example.examen_practice.Presentation.Room.FormularyDb
import com.example.examen_practice.Presentation.Room.FormularyDbDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFormularyDatabase(@ApplicationContext context: Context): FormularyDb {
        return Room.databaseBuilder(
            context,
            FormularyDb::class.java,
            "formulary_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFormularyDao(database: FormularyDb): FormularyDbDao {
        return database.formularyDbDao()
    }
}
    