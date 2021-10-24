package pl.edu.pw.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.dao.FacultyDao;
import pl.edu.pw.dto.FacultyDto;
import pl.edu.pw.entity.Faculty;
import pl.edu.pw.mapper.FacultyMapper;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    FacultyDao facultyDao;

    @Override
    public List<FacultyDto> getAll() {
        return facultyDao.findAll().stream()
                .map(FacultyMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public FacultyDto add(FacultyDto facultyDto) throws SQLException {
        Faculty newFaculty = facultyDao.save(FacultyMapper.map(facultyDto));
        return FacultyMapper.map(newFaculty);
    }

    @Override
    public void deleteByName(String name) throws NotFoundException {
        Faculty faculty = facultyDao.findByName(name);
        if (faculty == null) {
            throw new NotFoundException("Faculty " + name + " does not exist");
        }
        facultyDao.deleteByName(name);
    }

    @Override
    public FacultyDto findByName(String name) throws NotFoundException {
        Faculty faculty = facultyDao.findByName(name);
        if (faculty == null) {
            throw new NotFoundException("Faculty " + name + " does not exist");
        }
        return FacultyMapper.map(faculty);
    }

    @Override
    public void updateByName(String name,FacultyDto facultyDto) throws NotFoundException {
        Faculty faculty = facultyDao.findByName(name);
        if (faculty == null) {
            throw new NotFoundException("Faculty " + name + " does not exist");
        }
        faculty.setName(facultyDto.getName());
        faculty.setAddress(facultyDto.getAddress());
        faculty.setContactEmail(facultyDto.getContactEmail());
        facultyDao.save(faculty);
    }

}
