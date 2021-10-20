package pl.edu.pw.rest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.pw.dto.FacultyDto;
import pl.edu.pw.service.FacultyService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class FacultyRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FacultyService service;

    @Test
    public void getFaculties_emptyTable_returnEmptyList() throws Exception {
        List<FacultyDto> emptyList = new ArrayList<>();

        Mockito.when(service.getAll()).thenReturn(emptyList);

        this.mockMvc.perform(get("/faculties"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));
    }

}
