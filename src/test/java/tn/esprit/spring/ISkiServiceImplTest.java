package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.*;
import tn.esprit.spring.services.SkierServicesImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ISkiServiceImplTest {

    @Mock
    private ISkierRepository skierRepository;

    @Mock
    private IPisteRepository pisteRepository;

    @Mock
    private ICourseRepository courseRepository;

    @Mock
    private IRegistrationRepository registrationRepository;

    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SkierServicesImpl skierServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllSkiers() {
        // Mock data
        when(skierRepository.findAll()).thenReturn(Arrays.asList(new Skier(), new Skier()));

        // Verify
        assertEquals(2, skierServices.retrieveAllSkiers().size());
        verify(skierRepository, times(1)).findAll();
    }


    @Test
    void testAssignSkierToSubscription() {
        Skier skier = new Skier();
        Subscription subscription = new Subscription();
        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(subscriptionRepository.findById(2L)).thenReturn(Optional.of(subscription));
        when(skierRepository.save(skier)).thenReturn(skier);

        Skier result = skierServices.assignSkierToSubscription(1L, 2L);

        assertEquals(subscription, result.getSubscription());
        verify(skierRepository).save(skier);
    }



    @Test
    void testRemoveSkier() {
        skierServices.removeSkier(1L);
        verify(skierRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRetrieveSkier() {
        when(skierRepository.findById(1L)).thenReturn(Optional.of(new Skier()));

        assertNotNull(skierServices.retrieveSkier(1L));
        verify(skierRepository, times(1)).findById(1L);
    }

    @Test
    void testAssignSkierToPiste() {
        Skier skier = new Skier();
        Piste piste = new Piste();
        skier.setPistes(new HashSet<>());

        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(pisteRepository.findById(1L)).thenReturn(Optional.of(piste));
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);

        Skier result = skierServices.assignSkierToPiste(1L, 1L);

        assertTrue(result.getPistes().contains(piste));
        verify(skierRepository).save(skier);
    }

    @Test
    void testRetrieveSkiersBySubscriptionType() {
        when(skierRepository.findBySubscription_TypeSub(TypeSubscription.ANNUAL))
                .thenReturn(Arrays.asList(new Skier(), new Skier()));

        assertEquals(2, skierServices.retrieveSkiersBySubscriptionType(TypeSubscription.ANNUAL).size());
        verify(skierRepository, times(1)).findBySubscription_TypeSub(TypeSubscription.ANNUAL);
    }
}
