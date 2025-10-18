package com.example.a2courseviewereditorapp

import CourseRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CourseViewModel(private val repository: CourseRepository) : ViewModel() {

    //get all courses from the repository as a StateFlow
    val courses: StateFlow<List<Course>> = repository.allCourses
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    var selectedCourse = androidx.compose.runtime.mutableStateOf<Course?>(null)
    var showAddDialog = androidx.compose.runtime.mutableStateOf(false)
    var showEditDialog = androidx.compose.runtime.mutableStateOf(false)
    var showDetail = androidx.compose.runtime.mutableStateOf(false)

    fun addCourse(course: Course) {
        viewModelScope.launch {
            repository.insertCourse(course)
        }
    }

    fun editCourse(course: Course) {
        viewModelScope.launch {
            repository.updateCourse(course)
        }
    }

    fun deleteCourse(course: Course) {
        viewModelScope.launch {
            repository.deleteCourse(course)
        }
    }

    fun selectCourse(course: Course) {
        selectedCourse.value = course
        showDetail.value = true
    }
}
