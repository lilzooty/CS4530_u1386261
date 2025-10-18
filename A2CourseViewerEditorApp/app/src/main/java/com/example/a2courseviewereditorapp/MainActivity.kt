package com.example.a2courseviewereditorapp

import CourseRepository
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private val viewModel: CourseViewModel by viewModels {
        val database = CourseDatabase.getDatabase(applicationContext)
        val repository = CourseRepository(database.courseDao())
        CourseViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CourseApp(viewModel)
            }
        }
    }
}

@Composable
fun CourseApp(viewModel: CourseViewModel) {
    val showAdd by viewModel.showAddDialog
    val showEdit by viewModel.showEditDialog
    val showDetail by viewModel.showDetail
    val selectedCourse by viewModel.selectedCourse
    val courses by viewModel.courses.collectAsState()

    if (showDetail && selectedCourse != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { viewModel.showDetail.value = false }) {
                Text("Back")
            }
            Text("Department: ${selectedCourse!!.department}")
            Text("Course Number: ${selectedCourse!!.courseNumber}")
            Text("Location: ${selectedCourse!!.location}")
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Courses", style = MaterialTheme.typography.headlineMedium)
                FloatingActionButton(
                    onClick = { viewModel.showAddDialog.value = true },
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(courses) { course ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.selectCourse(course) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("${course.department} ${course.courseNumber}")
                            Row {
                                IconButton(onClick = {
                                    viewModel.selectedCourse.value = course
                                    viewModel.showEditDialog.value = true
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                                }
                                IconButton(onClick = { viewModel.deleteCourse(course) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAdd) {
        var department by remember { mutableStateOf("") }
        var courseNumber by remember { mutableStateOf("") }
        var location by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { viewModel.showAddDialog.value = false },
            title = { Text("Add Course") },
            text = {
                Column {
                    OutlinedTextField(
                        value = department,
                        onValueChange = { department = it },
                        label = { Text("Department") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = courseNumber,
                        onValueChange = { courseNumber = it },
                        label = { Text("Course Number") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("Location") }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (department.isNotBlank() && courseNumber.isNotBlank() && location.isNotBlank()) {
                            viewModel.addCourse(Course(
                                department = department,
                                courseNumber = courseNumber,
                                location = location))
                            viewModel.showAddDialog.value = false
                        }
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.showAddDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showEdit && selectedCourse != null) {
        var department by remember { mutableStateOf(selectedCourse!!.department) }
        var courseNumber by remember { mutableStateOf(selectedCourse!!.courseNumber) }
        var location by remember { mutableStateOf(selectedCourse!!.location) }

        AlertDialog(
            onDismissRequest = { viewModel.showEditDialog.value = false },
            title = { Text("Edit Course") },
            text = {
                Column {
                    OutlinedTextField(
                        value = department,
                        onValueChange = { department = it },
                        label = { Text("Department") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = courseNumber,
                        onValueChange = { courseNumber = it },
                        label = { Text("Course Number") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("Location") }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (department.isNotBlank() && courseNumber.isNotBlank() && location.isNotBlank()) {
                            viewModel.editCourse(
                                selectedCourse!!.copy(
                                    department = department,
                                    courseNumber = courseNumber,
                                    location = location
                                )
                            )
                            viewModel.showEditDialog.value = false
                        }
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.showEditDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
