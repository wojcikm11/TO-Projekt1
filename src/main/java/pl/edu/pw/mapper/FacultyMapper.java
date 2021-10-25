package pl.edu.pw.mapper;

import pl.edu.pw.dto.FacultyDto;
import pl.edu.pw.entity.Faculty;

import javax.validation.Valid;

public class FacultyMapper {

    public static Faculty map(FacultyDto facultyDto) {
        return new Faculty(facultyDto.getName(), facultyDto.getAddress(), facultyDto.getContactEmail());
    }

    public static FacultyDto map(Faculty faculty) {
        return new FacultyDto(faculty.getName(), faculty.getAddress(), faculty.getContactEmail());
    }

}
