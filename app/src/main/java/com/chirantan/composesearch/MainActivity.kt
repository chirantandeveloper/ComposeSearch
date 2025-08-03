package com.chirantan.composesearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chirantan.composesearch.ui.theme.ComposeSearchTheme
import com.example.composeselect.SearchSuggestionTextField


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeSearchTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DropdownTestScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

}

@Composable
fun DropdownTestScreen(modifier: Modifier) {
    val fruitList = listOf(
        "Apple", "Apricot", "Banana", "Blackberry", "Blueberry",
        "Cherry", "Date", "Fig", "Grape", "Guava", "Kiwi", "Mango", "Peach", "Pineapple"
    )
    var selectedItem by remember { mutableStateOf<String?>(null) }

    Box(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Column {
            SearchSuggestionTextField(
                items = fruitList,
                onItemSelected = { selectedItem = it },
                placeholder = "Search fruits...",
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Selected: ${selectedItem ?: "None"}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}