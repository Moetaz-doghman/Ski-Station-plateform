package tn.esprit.SkiStationProject;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.SkiStationProject.entities.Course;
import tn.esprit.SkiStationProject.entities.enums.Support;
import tn.esprit.SkiStationProject.entities.enums.TypeCourse;
import tn.esprit.SkiStationProject.repositories.CourseRepository;
import tn.esprit.SkiStationProject.services.CourseServicesImpl;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class CourseMockTest {

    @InjectMocks
    private CourseServicesImpl courseServices;

    @Mock
    private CourseRepository courseRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void shouldRetrieveAllCourses() {
        // Create sample courses
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, TypeCourse.COLLECTIVE_CHILDREN, Support.SNOWBOARD, 100.0f, 60));
        courses.add(new Course(2, TypeCourse.INDIVIDUAL, Support.SNOWBOARD, 80.0f, 90));

        // Mock the repository to return the sample courses when findAll is called
        when(courseRepository.findAll()).thenReturn(courses);

        // Call the service method to retrieve all courses
        List<Course> result = courseServices.retrieveAllCourses();

        // Verify that the service method called the repository's findAll method
        verify(courseRepository).findAll();

        // Assert that the result matches the sample courses
        assertEquals(courses, result);

        // Print a success message
        System.out.println("shouldRetrieveAllCourses succeeded!");
    }

    // Similarly, you can add success messages for other test methods
}

