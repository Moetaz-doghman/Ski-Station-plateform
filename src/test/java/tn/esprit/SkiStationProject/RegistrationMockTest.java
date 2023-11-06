package tn.esprit.SkiStationProject;


import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.SkiStationProject.entities.Registration;
import tn.esprit.SkiStationProject.entities.Skier;
import tn.esprit.SkiStationProject.entities.Course;
import tn.esprit.SkiStationProject.repositories.RegistrationRepository;
import tn.esprit.SkiStationProject.repositories.SkierRepository;
import tn.esprit.SkiStationProject.repositories.CourseRepository;
import tn.esprit.SkiStationProject.services.RegistrationServicesImpl;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RegistrationMockTest {

    @InjectMocks
    private RegistrationServicesImpl registrationServices;

    @Mock
    private SkierRepository skierRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private RegistrationRepository registrationRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddAndAssignToSkier() {
        // Create a sample Registration, Skier, and Course objects
        Registration registration = new Registration();
        Skier skier = new Skier();
        Course course = new Course();

        // Mock the repository methods to return the same objects
        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(registrationRepository.save(registration)).thenReturn(registration);

        // Call the service method
        Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, 1L);

        // Verify that the repository methods were called with the correct arguments
        verify(skierRepository).findById(1L);
        verify(registrationRepository).save(registration);

        // Assert that the result matches the mocked Registration object
        assertEquals(registration, result);
    }

    @Test
    public void testAddAndAssignToSkierAndCourse() {
        // Create a sample Registration, Skier, and Course objects
        Registration registration = new Registration();
        Skier skier = new Skier();
        Course course = new Course();

        // Mock the repository methods to return the same objects
        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(registrationRepository.save(registration)).thenReturn(registration);

        // Call the service method
        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 1L);

        // Verify that the repository methods were called with the correct arguments
        verify(skierRepository).findById(1L);
        verify(courseRepository).findById(1L);
        verify(registrationRepository).save(registration);

        // Assert that the result matches the mocked Registration object
        assertEquals(registration, result);
    }
}
