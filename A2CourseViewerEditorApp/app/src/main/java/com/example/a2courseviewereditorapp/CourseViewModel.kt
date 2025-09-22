package com.example.a2courseviewereditorapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf

class CourseViewModel : ViewModel() {
    val courses = mutableStateListOf<Course>()

    var selectedCourse = mutableStateOf<Course?>(null)
    var showAddDialog = mutableStateOf(false)
    var showEditDialog = mutableStateOf(false)
    var showDetail = mutableStateOf(false)

    fun addCourse(course: Course) {
        courses.add(course)
    }

    fun editCourse(oldCourse: Course, newCourse: Course) {
        val index = courses.indexOf(oldCourse)
        if (index != -1) {
            courses[index] = newCourse
            selectedCourse.value = courses[index]
        }
    }

    fun deleteCourse(course: Course) {
        courses.remove(course)
    }

    fun selectCourse(course: Course) {
        selectedCourse.value = course
        showDetail.value = true
    }
}