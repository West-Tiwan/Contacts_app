package com.example.contacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.contacts.ui.theme.ContactsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactsTheme {
                Scaffold(modifier = Modifier.displayCutoutPadding()) { innerPadding ->
                    Contacts(innerPadding)
                }
            }
        }
    }
}

@Composable
fun Contacts(paddingValues: PaddingValues ,viewModel: RandomUserViewModel = viewModel()) {
    val searchQuery by viewModel.searchQuery
    val filteredUsers by viewModel.filteredUsers

    if (viewModel.loading) {
        Column(modifier = Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "loading...")
        }
    } else if (viewModel.error != null) {
        Text(text = "$viewModel.error")
    } else {
        Column {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {viewModel.onSearchQueryChange(it)},
                label = { Text(text = "Search contacts") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn (modifier = Modifier.fillMaxWidth().padding(paddingValues)) {
                items(filteredUsers) { user ->
                    Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                        AsyncImage(
                            model = user.picture.large,
                            contentDescription = null,
                            modifier = Modifier.size(50.dp).clip(CircleShape).border(BorderStroke(1.dp,
                                Color.DarkGray), CircleShape)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = "${user.name.first} ${user.name.last}")
                            Text(text = user.email)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ContactsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Contacts(innerPadding)
        }
    }
}