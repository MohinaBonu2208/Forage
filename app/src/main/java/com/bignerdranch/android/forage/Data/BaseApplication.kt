package com.bignerdranch.android.forage.Data

import android.app.Application

class BaseApplication : Application() {
    val dataBase: ForageDatabase by lazy { ForageDatabase.getDatabase(this) }
}