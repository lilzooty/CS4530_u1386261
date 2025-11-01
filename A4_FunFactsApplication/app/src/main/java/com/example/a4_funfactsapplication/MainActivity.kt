package com.example.a4_funfactsapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: FunFactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as FunFactsApplication

        viewModel = ViewModelProvider(
            this,
            FunFactViewModelFactory(app.repository)
        )[FunFactViewModel::class.java]

        setContent {
            MaterialTheme {
                FunFactsScreen(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FunFactsScreen(viewModel: FunFactViewModel) {
    val facts by viewModel.allFacts.collectAsStateWithLifecycle(initialValue = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fun Facts") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.fetchNewFact() }
            ) {
                Text("New Fact")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(facts, key = { it.id }) { fact ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = fact.text,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}