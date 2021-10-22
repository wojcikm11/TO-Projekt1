package pl.edu.pw.rest;

import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.pw.dto.FacultyDto;
import pl.edu.pw.mapper.FacultyMapper;
import pl.edu.pw.service.FacultyService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class FacultyRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FacultyService service;

    @Test
    void getFaculties_emptyTable_returnEmptyList() throws Exception {
        List<FacultyDto> emptyList = new ArrayList<>();

        Mockito.when(service.getAll()).thenReturn(emptyList);

        this.mockMvc.perform(get("/faculties"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));
        Mockito.verify(service, times(1)).getAll();
    }

    // TODO: more tests for getFaculties()

    @Test
    void addFaculty_noBody_throwBadRequest() throws Exception {
        this.mockMvc.perform(post("/faculties/add"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addFaculty_missingContactEmailInBody_throwBadRequest() throws Exception {
        FacultyDto dto = new FacultyDto("Totally Real Faculty", "Totally real street 1337", null);
        String requestBody = "{" +
                "\"name\":\"" + dto.getName() + "\"," +
                "\"address\":\"" + dto.getAddress() + "\"" +
                "}";

        Mockito.when(service.add(dto)).thenThrow(new PropertyValueException(null, "Faculty", "contactEmail"));

        this.mockMvc.perform(post("/faculties/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
        Mockito.verify(service, times(1)).add(dto);
    }

    @Test
    void addFaculty_nonUniqueName_throwBadRequest() throws Exception {
        FacultyDto dto = new FacultyDto("Totally Real Faculty", "Totally real street 1337", "trf@realemails.com");
        String requestBody = "{" +
                "\"name\":\"" + dto.getName() + "\"," +
                "\"address\":\"" + dto.getAddress() + "\"," +
                "\"contactEmail\":\"" + dto.getContactEmail() + "\"" +
                "}";

        Mockito.when(service.add(dto)).thenThrow(new SQLException());

        this.mockMvc.perform(post("/faculties/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
        Mockito.verify(service, times(1)).add(dto);
    }

    @Test
    void addFaculty_success() throws Exception {
        FacultyDto requestDto = new FacultyDto("Totally Real Faculty", "Totally real street 1337", "trf@realemails.com");
        FacultyDto responseDto = FacultyMapper.map(FacultyMapper.map(requestDto));
        String requestBody = "{" +
                "\"name\":\"" + requestDto.getName() + "\"," +
                "\"address\":\"" + requestDto.getAddress() + "\"," +
                "\"contactEmail\":\"" + requestDto.getContactEmail() + "\"" +
                "}";

        Mockito.when(service.add(requestDto)).thenReturn(responseDto);

        this.mockMvc.perform(post("/faculties/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated());
        Mockito.verify(service, times(1)).add(requestDto);
    }

}
