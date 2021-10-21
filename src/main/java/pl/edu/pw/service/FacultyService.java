package pl.edu.pw.service;

import javassist.NotFoundException;
import pl.edu.pw.dto.FacultyDto;
import pl.edu.pw.entity.Faculty;

import java.util.List;

public interface FacultyService {

    List<FacultyDto> getAll();

    Faculty add(FacultyDto facultyDto);
    void deleteByName(String  name) throws NotFoundException;
    String findByName(String name) throws NotFoundException;
    void updateByName(FacultyDto facultyDto) throws NotFoundException;

}
