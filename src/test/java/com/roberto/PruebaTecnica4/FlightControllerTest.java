package com.roberto.PruebaTecnica4;

import com.roberto.PruebaTecnica4.dto.FlightDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findFlights() throws Exception {
        mockMvc.perform(get("/flights")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createFlight() throws Exception {
        FlightDTO flightDTO = new FlightDTO();
        mockMvc.perform(MockMvcRequestBuilders.post("/flights/new" , flightDTO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
