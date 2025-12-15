package com.example.todo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todo.data.local.dao.TodoDao
import com.example.todo.data.local.entity.TodoEntity

@Database(entities = [TodoEntity::class], version = 2)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                val currentTimestamp = System.currentTimeMillis()
                db.execSQL("ALTER TABLE todos ADD COLUMN description TEXT")
                db.execSQL("ALTER TABLE todos ADD COLUMN priority INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE todos ADD COLUMN createdAt INTEGER NOT NULL DEFAULT $currentTimestamp")
                db.execSQL("ALTER TABLE todos ADD COLUMN completedAt INTEGER")
            }
        }
    }
}