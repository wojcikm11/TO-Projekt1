package pl.edu.pw.rest;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.dto.FacultyDto;
import pl.edu.pw.entity.Faculty;
import pl.edu.pw.service.FacultyService;

import javax.validation.Valid;
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
    public Faculty addFaculty(@RequestBody FacultyDto facultyDto) {
        if (facultyDto == null) {
            throw new IllegalArgumentException("Faculty argument is required"); // wydaje mi się, że to jest handlowane gdzie indziej
        }
        return service.add(facultyDto);
    }

    @DeleteMapping("/delete/{name}")
    public String delete(@PathVariable ("name") String name) throws NotFoundException {
        if (name==null){
            throw new IllegalArgumentException("Faculty's name argument is required");
        }
        service.deleteByName(name);
        return "deleted- " + name;
    }

    @GetMapping("/find/{name}")
    public FacultyDto find(@PathVariable("name") String name) throws NotFoundException {
        if (name==null){
            throw new IllegalArgumentException("Faculty's name argument is required");
        }
        String[] data = service.findByName(name).split(",");
        return new FacultyDto(data[0],data[1],data[2]);
    }

    @PutMapping("/update/{name}")
    public String update(@PathVariable(value = "name") String name, @RequestBody String address, @RequestBody String contactEmail) throws NotFoundException{
        service.updateByName(new FacultyDto(name, address, contactEmail));
        return "Updated" + name;
    }


}
