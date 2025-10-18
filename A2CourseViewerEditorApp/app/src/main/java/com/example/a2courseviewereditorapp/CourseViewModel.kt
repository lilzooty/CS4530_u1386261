package com.example.a2courseviewereditorapp

import CourseRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CourseViewModel(private val repository: CourseRepository) : ViewModel() {

    // Collect all courses from the repository as a StateFlow (live-updating stream)
    val courses: StateFlow<List<Course>> = repository.allCourses
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // UI states
    var selectedCourse = androidx.compose.runtime.mutableStateOf<Course?>(null)
    var showAddDialog = androidx.compose.runtime.mutableStateOf(false)
    var showEditDialog = androidx.compose.runtime.mutableStateOf(false)
    var showDetail = androidx.compose.runtime.mutableStateOf(false)

    // Add new course
    fun addCourse(course: Course) {
        viewModelScope.launch {
            repository.insertCourse(course)
        }
    }

    // Update course
    fun editCourse(course: Course) {
        viewModelScope.launch {
            repository.updateCourse(course)
        }
    }

    // Delete course
    fun deleteCourse(course: Course) {
        viewModelScope.launch {
            repository.deleteCourse(course)
        }
    }

    // Select a course for detail view
    fun selectCourse(course: Course) {
        selectedCourse.value = course
        showDetail.value = true
    }
}
