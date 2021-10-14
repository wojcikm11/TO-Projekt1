package pl.edu.pw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.dao.FacultyDao;
import pl.edu.pw.dto.FacultyDto;
import pl.edu.pw.entity.Faculty;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    FacultyDao facultyDao;

    @Override
    public List<FacultyDto> getAll() {
        return facultyDao.findAll().stream()
                .map(f -> new FacultyDto(
                        f.getName(),
                        f.getAddress(),
                        f.getContactEmail()
                )).collect(Collectors.toList());
// Above code does the same thing as this one:
//
//        List<Faculty> facultyEntities = facultyDao.findAll();
//        List<FacultyDto> facultyDtos = new ArrayList<>();
//        for (Faculty faculty : facultyEntities) {
//            FacultyDto dto = new FacultyDto(
//                    faculty.getName(),
//                    faculty.getAddress(),
//                    faculty.getContactEmail()
//            );
//        }
//        return facultyDtos;
    }

}
