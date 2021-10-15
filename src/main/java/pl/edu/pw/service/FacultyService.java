package pl.edu.pw.service;

import pl.edu.pw.dto.FacultyDto;
import pl.edu.pw.entity.Faculty;

import java.util.List;

public interface FacultyService {

    List<FacultyDto> getAll();

    Faculty add(FacultyDto facultyDto);
}
