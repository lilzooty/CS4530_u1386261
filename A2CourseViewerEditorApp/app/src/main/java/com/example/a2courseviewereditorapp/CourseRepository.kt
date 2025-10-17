import com.example.a2courseviewereditorapp.Course
import kotlinx.coroutines.flow.Flow

class CourseRepository(private val courseDao: CourseDao) {
    val allCourses: Flow<List<Course>> = courseDao.getAllCourses()

    suspend fun insert(course: Course) = courseDao.insertCourse(course)
    suspend fun update(course: Course) = courseDao.updateCourse(course)
    suspend fun delete(course: Course) = courseDao.deleteCourse(course)
}