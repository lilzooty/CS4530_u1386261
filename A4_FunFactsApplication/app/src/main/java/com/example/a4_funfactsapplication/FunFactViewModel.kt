package com.example.a4_funfactsapplication
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FunFactViewModel(private val repository: FunFactRepository) : ViewModel() {

    val allFacts = repository.getAllFacts()

    private val isLoading = MutableStateFlow(false)

    private val errorMessage = MutableStateFlow<String?>(null)

    fun fetchNewFact() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            repository.fetchAndStoreFact()
                .onSuccess {
                }
                .onFailure { exception ->
                    errorMessage.value = "Failed to fetch fact: ${exception.message}"
                }

            isLoading.value = false
        }
    }
}

class FunFactViewModelFactory(
    private val repository: FunFactRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FunFactViewModel::class.java)) {
            return FunFactViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}