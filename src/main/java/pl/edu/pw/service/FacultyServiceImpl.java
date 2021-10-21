package pl.edu.pw.service;

import javassist.NotFoundException;
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
                .map(FacultyMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Faculty add(FacultyDto facultyDto) {
        return facultyDao.save(FacultyMapper.map(facultyDto));
    }

    @Override
    public void deleteByName(String name) throws NotFoundException {
        if (facultyDao.findByName(name) == null) {
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
    public void updateByName(String name, FacultyDto facultyDto) throws NotFoundException {
        Faculty faculty = facultyDao.findByName(name);
        if (faculty == null) {
            throw new NotFoundException("Faculty " + name + " does not exist");
        }
        faculty.setName(facultyDto.getName());
        faculty.setAddress(facultyDto.getAddress());
        faculty.setContactEmail(facultyDto.getContactEmail());
        facultyDao.save(faculty);
    }


    private static class FacultyMapper {
        private static Faculty map(FacultyDto facultyDto) {
            return new Faculty(facultyDto.getName(), facultyDto.getAddress(), facultyDto.getContactEmail());
        }

        private static FacultyDto map(Faculty faculty) {
            return new FacultyDto(faculty.getName(), faculty.getAddress(), faculty.getContactEmail());
        }
    }

}
