package com.example.oto.viewmodel

import androidx.lifecycle.*
import com.example.oto.database.MainRepository
import com.example.oto.model.Aktivitas
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AktivitasViewModel(private val repository: MainRepository) : ViewModel() {

    val allAktivitas : LiveData<List<Aktivitas>> = repository.allAktivitas.asLiveData()

    fun insert(aktivitas: Aktivitas) = viewModelScope.launch{
        repository.insert(aktivitas)
    }
}

class AktivitasViewModelFactory(private  val repository: MainRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AktivitasViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return AktivitasViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}