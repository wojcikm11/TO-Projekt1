package pl.edu.pw.rest;

import javassist.NotFoundException;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    void getFaculties_oneElementTable_returnOneElementList() throws Exception {
        List<FacultyDto> oneElementList = new ArrayList<>();
        FacultyDto dto = new FacultyDto("Transport", "Konopnickiej 7", "transport@pw.edu.pl");
        oneElementList.add(dto);

        Mockito.when(service.getAll()).thenReturn(oneElementList);

        this.mockMvc.perform(get("/faculties"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[" +
                                "{" +
                                    "\"name\":\"" + dto.getName() + "\"," +
                                    "\"address\":\"" + dto.getAddress() + "\"," +
                                    "\"contactEmail\":\"" + dto.getContactEmail() + "\"" +
                                "}" +
                            "]"
                        )
                ));
        Mockito.verify(service, times(1)).getAll();
    }

    @Test
    void findFaculty_success() throws Exception {
        String facultyName = "Electrical";
        FacultyDto dto = new FacultyDto(facultyName, "Brzozowa 13", "electrical@pw.edu.pl");
        Mockito.when(service.findByName(facultyName)).thenReturn(dto);

        this.mockMvc.perform(get("/faculties/find/" + facultyName))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{" +
                                "\"name\":\"" + dto.getName() + "\"," +
                                "\"address\":\"" + dto.getAddress() + "\"," +
                                "\"contactEmail\":\"" + dto.getContactEmail() + "\"" +
                                "}"
                            )
                ));
        Mockito.verify(service, times(1)).findByName(facultyName);
    }

    @Test
    void findFaculty_invalidName_throwNotFound() throws Exception {
        String invalidFacultyName = "Mechanicel";
        Mockito.when(service.findByName(invalidFacultyName)).thenThrow(new NotFoundException(null));

        this.mockMvc.perform(get("/faculties/find/" + invalidFacultyName))
                .andExpect(status().isNotFound());
        Mockito.verify(service, times(1)).findByName(invalidFacultyName);
    }

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

    @Test
    void updateFaculty_success() throws Exception {
        String facultyName = "Faculty";
        FacultyDto facultyDto = new FacultyDto(facultyName, "Faculty Addres", "contact@gmail.com");
        String requestBody = "{" +
                "\"name\":\"" + facultyDto.getName() + "\"," +
                "\"address\":\"" + facultyDto.getAddress() + "\"," +
                "\"contactEmail\":\"" + facultyDto.getContactEmail() + "\"" +
                "}";

        this.mockMvc.perform(put("/faculties/update/" + facultyName)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)).andExpect(status().isNoContent());
        Mockito.verify(service, times(1)).updateByName(facultyName, facultyDto);
    }

    @Test
    void updateFaculty_invalidName_throwsBadRequest() throws Exception {
        this.mockMvc.perform(put("/faculties/update/InvalidName")).andExpect(status().isBadRequest());
    }

    @Test
    void updateFaculty_noBody_throwBadRequest() throws Exception {
        this.mockMvc.perform(put("/faculties/update/wydzial1")).andExpect(status().isBadRequest());
    }


    @Test
    void delete_invalidName_throwsNotFound() throws Exception {
        String name= "wrongName";
        doThrow(NotFoundException.class).when(service).deleteByName(name);
        this.mockMvc.perform(delete("/faculties/delete/"+name)).andExpect(status().isNotFound());

    }

    @Test
    void delete_noNameProvided_throwsNotFound() throws Exception {
        this.mockMvc.perform(delete("/faculties/delete/")).andExpect(status().isNotFound());
    }

    @Test
    void delete_success() throws Exception {
        String name = "Totally Real Faculty";
        this.mockMvc.perform(delete("/faculties/delete/" + name)).andExpect(status().isOk());
    }
}
