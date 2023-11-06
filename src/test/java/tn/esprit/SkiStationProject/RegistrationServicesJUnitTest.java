package tn.esprit.SkiStationProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.SkiStationProject.entities.Course;
import tn.esprit.SkiStationProject.entities.Registration;
import tn.esprit.SkiStationProject.entities.Skier;
import tn.esprit.SkiStationProject.repositories.CourseRepository;
import tn.esprit.SkiStationProject.repositories.InstructorRepository;
import tn.esprit.SkiStationProject.repositories.RegistrationRepository;
import tn.esprit.SkiStationProject.repositories.SkierRepository;
import tn.esprit.SkiStationProject.services.RegistrationServicesImpl;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class RegistrationServicesImplTest {

    private RegistrationServicesImpl registrationServices;

    private SkierRepository skierRepository;
    private CourseRepository courseRepository;
    private InstructorRepository instructorRepository;
    private RegistrationRepository registrationRepository;

    @BeforeEach
    void setUp() {
        skierRepository = mock(SkierRepository.class);
        courseRepository = mock(CourseRepository.class);
        instructorRepository = mock(InstructorRepository.class);
        registrationRepository = mock(RegistrationRepository.class);

        registrationServices = new RegistrationServicesImpl(
                registrationRepository, skierRepository, courseRepository, instructorRepository
        );
    }

    @Test
    void addRegistrationAndAssignToSkier() {
        // Arrange
        Registration registration = new Registration();
        Skier skier = new Skier();
        when(skierRepository.findById(anyLong())).thenReturn(Optional.of(skier));
        when(registrationRepository.save(registration)).thenReturn(registration);

        // Act
        Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, 1L);

        // Assert
        verify(skierRepository, times(1)).findById(1L);
        verify(registrationRepository, times(1)).save(registration);
        assertEquals(result, registration);
    }

    @Test
    void assignRegistrationToCourse() {
        // Arrange
        Registration registration = new Registration();
        when(registrationRepository.findById(anyLong())).thenReturn(Optional.of(registration));
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(new Course()));
        when(registrationRepository.save(registration)).thenReturn(registration);

        // Act
        Registration result = registrationServices.assignRegistrationToCourse(1L, 2L);

        // Assert
        verify(registrationRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).findById(2L);
        verify(registrationRepository, times(1)).save(registration);
        assertEquals(result, registration);
    }

    // Add more test cases for other methods as needed
}

