package com.example.contacts

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RandomUserViewModel: ViewModel() {
    private var _users by mutableStateOf<List<User>>(emptyList())

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    var loading by mutableStateOf(true)
    var error by mutableStateOf<String?>(null)

    val filteredUsers: State<List<User>> = derivedStateOf {
        if (_searchQuery.value.isBlank()) {
            _users
        } else {
            _users.filter {
                it.name.first.contains(_searchQuery.value, ignoreCase = true) || it.name.last.contains(_searchQuery.value, ignoreCase = true)
            }
        }
    }

    fun onSearchQueryChange (query: String) {
        _searchQuery.value = query
    }

    init {
        viewModelScope.launch {
            try {
                val response = ApiService.api.getUsers()
                _users = response.results
            } catch (e: Exception) {
                error = "error loading: $e"
            }
            loading = false
        }
    }
}