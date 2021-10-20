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
    }

    @Override
    public Faculty add(FacultyDto facultyDto) {
        return facultyDao.save(FacultyMapper.map(facultyDto));
    }

    private static class FacultyMapper {
        private static Faculty map(FacultyDto facultyDto) {
            return new Faculty(facultyDto.getName(), facultyDto.getAddress(), facultyDto.getContactEmail());
        }
    }

}
