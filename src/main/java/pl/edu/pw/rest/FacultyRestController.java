package pl.edu.pw.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.dto.FacultyDto;
import pl.edu.pw.entity.Faculty;
import pl.edu.pw.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping(value = "/faculties")
public class FacultyRestController {

    @Autowired
    private FacultyService service;

    @GetMapping(value = {"", "/"})
    public List<FacultyDto> getFaculties() {
        return service.getAll();
    }

    @PostMapping("/add")
    public Faculty addFaculty(@RequestBody FacultyDto facultyDto) {
        if (facultyDto == null) {
            throw new IllegalArgumentException("Faculty argument is required");
        }
        return service.add(facultyDto);
    }
}
