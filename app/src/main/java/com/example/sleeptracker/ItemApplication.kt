package com.example.sleeptracker

import android.app.Application

class ItemApplication : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}