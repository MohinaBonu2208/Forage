package com.bignerdranch.android.forage

import androidx.lifecycle.*
import com.bignerdranch.android.forage.Data.Forage
import com.bignerdranch.android.forage.Data.ForageableDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ForageViewModel(private val dao: ForageableDao) : ViewModel() {
    val allForages: LiveData<List<Forage>> = dao.getForages().asLiveData()

    fun getForage(id: Int): LiveData<Forage> {
        return dao.getForage(id).asLiveData()
    }

    fun addForage(
        forageName: String,
        forageLocation: String,
        inSeason: Boolean,
        forageNote: String
    ) {
        val forage = Forage(
            name = forageName,
            location = forageLocation,
            inSeason = inSeason,
            notes = forageNote
        )

        viewModelScope.launch { dao.insert(forage) }

    }

    fun updateForage(
        id: Int,
        forageName: String,
        forageLocation: String,
        inSeason: Boolean,
        forageNote: String
    ) {
        val forage = Forage(
            id = id,
            name = forageName,
            location = forageLocation,
            inSeason = inSeason,
            notes = forageNote
        )
        viewModelScope.launch { dao.update(forage) }
    }


    fun delete(forage: Forage) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(forage)
        }
    }

    fun isValidEntry(name: String, location: String): Boolean {
        return name.isNotBlank() && location.isNotBlank()
    }
}

class ViewModelFactory(private var dao: ForageableDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForageViewModel::class.java)) {
            return ForageViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown viewModel")
    }

}