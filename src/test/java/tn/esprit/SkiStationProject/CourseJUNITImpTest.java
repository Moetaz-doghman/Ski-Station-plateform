package tn.esprit.SkiStationProject;

import org.junit.jupiter.api.AfterEach;
import tn.esprit.SkiStationProject.entities.*;
import tn.esprit.SkiStationProject.entities.enums.TypeCourse;
import tn.esprit.SkiStationProject.entities.enums.Support;

import tn.esprit.SkiStationProject.services.CourseServicesImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("test") // Use the "test" profile to configure the H2 in-memory database
@Transactional // Rollback transactions after each test
public class CourseJUNITImpTest {

    @Autowired
    private CourseServicesImpl courseServices;

    @Test
    public void testRetrieveAllCourses() {
        // Create and save sample courses to the H2 database
        Course course1 = new Course(1, TypeCourse.COLLECTIVE_CHILDREN, Support.SNOWBOARD, 100.0f, 60);
        Course course2 = new Course(2, TypeCourse.INDIVIDUAL, Support.SNOWBOARD, 80.0f, 90);
        courseServices.addCourse(course1);
        courseServices.addCourse(course2);

        // Call the service method to retrieve all courses
        List<Course> courses = courseServices.retrieveAllCourses();

        // Assert that the list of courses is not empty and contains the expected courses
        assertNotNull(courses);
        assertEquals(2, courses.size());
        assertEquals(1, courses.get(0).getLevel());
        assertEquals(TypeCourse.COLLECTIVE_CHILDREN, courses.get(0).getTypeCourse());
        assertEquals(Support.SNOWBOARD, courses.get(0).getSupport());
        assertEquals(100.0f, courses.get(0).getPrice());
        assertEquals(60, courses.get(0).getTimeSlot());

        assertEquals(2, courses.get(1).getLevel());
        assertEquals(TypeCourse.INDIVIDUAL, courses.get(1).getTypeCourse());
        assertEquals(Support.SNOWBOARD, courses.get(1).getSupport());
        assertEquals(80.0f, courses.get(1).getPrice());
        assertEquals(90, courses.get(1).getTimeSlot());

        System.out.println("Test 'testRetrieveAllCourses' completed successfully.");
    }

    @Test
    public void testAddCourse() {
        // Create a new course
        Course newCourse = new Course(3, TypeCourse.COLLECTIVE_ADULT, Support.SKI, 120.0f, 75);

        // Add the new course using the service
        Course addedCourse = courseServices.addCourse(newCourse);

        // Retrieve the added course from the database
        Course retrievedCourse = courseServices.retrieveCourse(addedCourse.getId());

        // Assert that the retrieved course matches the added course
        assertNotNull(addedCourse);
        assertEquals(newCourse.getLevel(), addedCourse.getLevel());
        assertEquals(newCourse.getTypeCourse(), addedCourse.getTypeCourse());
        assertEquals(newCourse.getSupport(), addedCourse.getSupport());
        assertEquals(newCourse.getPrice(), addedCourse.getPrice());
        assertEquals(newCourse.getTimeSlot(), addedCourse.getTimeSlot());
        assertNotNull(retrievedCourse);
        assertEquals(addedCourse.getId(), retrievedCourse.getId());
        assertEquals(newCourse.getLevel(), retrievedCourse.getLevel());
        assertEquals(newCourse.getTypeCourse(), retrievedCourse.getTypeCourse());
        assertEquals(newCourse.getSupport(), retrievedCourse.getSupport());
        assertEquals(newCourse.getPrice(), retrievedCourse.getPrice());
        assertEquals(newCourse.getTimeSlot(), retrievedCourse.getTimeSlot());

        System.out.println("Test 'testAddCourse' completed successfully.");
    }

    @Test
    public void testUpdateCourse() {
        // Create a new course
        Course newCourse = new Course(4, TypeCourse.COLLECTIVE_CHILDREN, Support.SNOWBOARD, 100.0f, 60);
        Course addedCourse = courseServices.addCourse(newCourse);

        // Update the course's price
        addedCourse.setPrice(150.0f);
        Course updatedCourse = courseServices.updateCourse(addedCourse);

        // Retrieve the updated course from the database
        Course retrievedCourse = courseServices.retrieveCourse(updatedCourse.getId());

        // Assert that the retrieved course has the updated price
        assertNotNull(updatedCourse);
        assertEquals(150.0f, updatedCourse.getPrice());
        assertNotNull(retrievedCourse);
        assertEquals(updatedCourse.getId(), retrievedCourse.getId());
        assertEquals(150.0f, retrievedCourse.getPrice());

        System.out.println("Test 'testUpdateCourse' completed successfully.");
    }

    @Test
    public void testRetrieveCourse() {
        // Create and save a sample course to the H2 database
        Course course = new Course(5, TypeCourse.COLLECTIVE_ADULT, Support.SKI, 80.0f, 90);
        Course addedCourse = courseServices.addCourse(course);

        // Retrieve the course by its ID
        Course retrievedCourse = courseServices.retrieveCourse(addedCourse.getId());

        // Assert that the retrieved course matches the added course
        assertNotNull(retrievedCourse);
        assertEquals(addedCourse.getId(), retrievedCourse.getId());
        assertEquals(addedCourse.getLevel(), retrievedCourse.getLevel());
        assertEquals(addedCourse.getTypeCourse(), retrievedCourse.getTypeCourse());
        assertEquals(addedCourse.getSupport(), retrievedCourse.getSupport());
        assertEquals(addedCourse.getPrice(), retrievedCourse.getPrice());
        assertEquals(addedCourse.getTimeSlot(), retrievedCourse.getTimeSlot());

        // Try to retrieve a course with an invalid ID
        Course invalidCourse = courseServices.retrieveCourse(-1L);

        // Assert that the result is null for an invalid ID
        assertNull(invalidCourse);

        System.out.println("Test 'testRetrieveCourse' completed successfully.");
    }

    @AfterEach
    public void cleanup() {
        // Delete the courses that were created during the tests
        List<Course> courses = courseServices.retrieveAllCourses();
        for (Course course : courses) {
            courseServices.deleteCourse(course.getId());
        }
    }

}
