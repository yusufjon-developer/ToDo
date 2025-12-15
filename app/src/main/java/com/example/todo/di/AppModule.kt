package com.example.todo.di

import android.content.Context
import androidx.room.Room
import com.example.todo.data.local.TodoDatabase
import com.example.todo.data.local.dao.TodoDao
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
    fun provideDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context, TodoDatabase::class.java, "todo_db"
        )
            .addMigrations(TodoDatabase.MIGRATION_1_2)
            .build()
    }

    @Provides
    fun provideTodoDao(database: TodoDatabase): TodoDao {
        return database.todoDao()
    }
}