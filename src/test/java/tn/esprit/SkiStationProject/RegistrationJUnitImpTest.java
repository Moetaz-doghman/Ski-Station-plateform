package tn.esprit.SkiStationProject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.SkiStationProject.entities.Registration;
import tn.esprit.SkiStationProject.entities.Skier;
import tn.esprit.SkiStationProject.entities.Course;
import tn.esprit.SkiStationProject.repositories.RegistrationRepository;
import tn.esprit.SkiStationProject.repositories.SkierRepository;
import tn.esprit.SkiStationProject.repositories.CourseRepository;
import tn.esprit.SkiStationProject.services.RegistrationServicesImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Transactional
public class RegistrationJUnitImpTest {

    @Autowired
    private RegistrationServicesImpl registrationServices;

    @Autowired
    private SkierRepository skierRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Test
    public void testAddAndAssignToSkier() {
        // Create a sample Registration, Skier, and Course objects
        Registration registration = new Registration();
        Skier skier = new Skier();
        Course course = new Course();

        // Save Skier and Course to the database
        skierRepository.save(skier);
        courseRepository.save(course);

        // Call the service method
        Registration addedRegistration = registrationServices.addRegistrationAndAssignToSkier(registration, skier.getId());

        // Retrieve the added Registration from the database
        Registration retrievedRegistration = registrationRepository.findById(addedRegistration.getId()).orElse(null);

        // Assert that the retrieved Registration matches the added Registration
        assertNotNull(addedRegistration);
        assertEquals(addedRegistration.getId(), retrievedRegistration.getId());
        // Add more assertions to check other fields of Registration.

        System.out.println("Test 'testAddAndAssignToSkier' completed successfully.");
    }

    @Test
    public void testAddAndAssignToSkierAndCourse() {
        // Create a sample Registration, Skier, and Course objects
        Registration registration = new Registration();
        Skier skier = new Skier();
        Course course = new Course();

        // Save Skier and Course to the database
        skierRepository.save(skier);
        courseRepository.save(course);

        // Call the service method
        Registration addedRegistration = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, skier.getId(), course.getId());

        // Retrieve the added Registration from the database
        Registration retrievedRegistration = registrationRepository.findById(addedRegistration.getId()).orElse(null);

        // Assert that the retrieved Registration matches the added Registration
        assertNotNull(addedRegistration);
        assertEquals(addedRegistration.getId(), retrievedRegistration.getId());
        // Add more assertions to check other fields of Registration.

        System.out.println("Test 'testAddAndAssignToSkierAndCourse' completed successfully.");
    }

    @AfterEach
    public void cleanup() {
        // Delete the registrations that were created during the tests
        List<Registration> registrations = registrationRepository.findAll();
        for (Registration registration : registrations) {
            registrationServices.deleteRegistration(registration.getId());
        }
    }
}
