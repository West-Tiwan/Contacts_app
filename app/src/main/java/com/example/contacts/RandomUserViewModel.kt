package com.example.contacts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RandomUserViewModel: ViewModel() {
    var users by mutableStateOf<List<User>>(emptyList())
    var loading by mutableStateOf(true)
    var error by mutableStateOf<String?>(null)

    init {
        viewModelScope.launch {
            try {
                val response = ApiService.api.getUsers()
                users = response.results
            } catch (e: Exception) {
                error = "error loading: $e"
            }
            loading = false
        }
    }
}