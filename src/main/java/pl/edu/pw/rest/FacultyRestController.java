package pl.edu.pw.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.dto.FacultyDto;
import pl.edu.pw.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping(value = "/faculties")
public class FacultyRestController {

    @Autowired
    FacultyService service;

    @GetMapping(value = {"", "/"})
    public List<FacultyDto> getFaculties() {
        return service.getAll();
    }

}
