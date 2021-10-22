package pl.edu.pw.rest;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.dto.FacultyDto;
import pl.edu.pw.service.FacultyService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(value = "/faculties")
public class FacultyRestController {

    @Autowired
    private FacultyService service;

    @GetMapping("")
    public List<FacultyDto> getFaculties() {
        return service.getAll();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public FacultyDto addFaculty(@RequestBody FacultyDto facultyDto) throws SQLException {
        return service.add(facultyDto);
    }

    @DeleteMapping("/delete/{name}")
    public void delete(@PathVariable String name) throws NotFoundException {
        service.deleteByName(name);
    }

    @GetMapping("/find/{name}")
    public FacultyDto find(@PathVariable String name) throws NotFoundException {
        return service.findByName(name);
    }

    @PutMapping("/update/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String name, @RequestBody FacultyDto facultyDto) throws NotFoundException {
        service.updateByName(name, facultyDto);
    }

}
