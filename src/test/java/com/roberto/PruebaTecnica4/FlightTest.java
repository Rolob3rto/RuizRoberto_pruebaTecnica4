package com.roberto.PruebaTecnica4;

import com.roberto.PruebaTecnica4.model.Flight;
import com.roberto.PruebaTecnica4.repository.FlightRepository;
import com.roberto.PruebaTecnica4.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FlightTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    private List<Flight> mockList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockList = new ArrayList<>();
    }

    @Test
    public void findFlights(){
        mockList.add(new Flight("LAMA-2912", "Origin", "Destination", 200, LocalDate.parse("2024-02-20"), "tipo de asiento", 200.1, true, null));

        when(flightRepository.findAll()).thenReturn(mockList);

        List<Flight> flightList = flightService.getActiveFlights();

        assertFalse(flightList.isEmpty());
    }

    @Test
    public void findFlightsEmpty(){
        mockList.add(new Flight("LAMA-2912", "Origin", "Destination", 200, LocalDate.parse("2024-02-20"), "tipo de asiento", 200.1, false, null));

        when(flightRepository.findAll()).thenReturn(mockList);

        List<Flight> flightList = flightService.getActiveFlights();

        assertTrue(flightList.isEmpty());
    }
}
