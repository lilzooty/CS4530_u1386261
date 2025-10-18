import com.example.a2courseviewereditorapp.Course
import com.example.a2courseviewereditorapp.CourseDao
import kotlinx.coroutines.flow.Flow

class CourseRepository(private val courseDao: CourseDao) {
    val allCourses: Flow<List<Course>> = courseDao.getAllCourses()

    suspend fun insertCourse(course: Course) = courseDao.insertCourse(course)
    suspend fun updateCourse(course: Course) = courseDao.updateCourse(course)
    suspend fun deleteCourse(course: Course) = courseDao.deleteCourse(course)
}