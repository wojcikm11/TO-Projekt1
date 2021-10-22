package pl.edu.pw.service;

import javassist.NotFoundException;
import pl.edu.pw.dto.FacultyDto;

import java.sql.SQLException;
import java.util.List;

public interface FacultyService {

    List<FacultyDto> getAll();

    FacultyDto add(FacultyDto facultyDto) throws SQLException;

    void deleteByName(String name) throws NotFoundException;

    FacultyDto findByName(String name) throws NotFoundException;

    void updateByName(String name, FacultyDto facultyDto) throws NotFoundException;

}
