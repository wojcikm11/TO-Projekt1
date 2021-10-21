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



    @Override
    public void deleteByName(String name) throws NotFoundException {
        if (facultyDao.findByName(name)==null) {
            throw new NotFoundException("Faculty " +name+" does not exist");
        }
      facultyDao.deleteByName(name);

    }

    @Override
    public String findByName(String name) throws NotFoundException {
        String f = facultyDao.findByName(name);
        if (f==null) {
            throw new NotFoundException("Faculty " +name+" does not exist");
        }
        return f;
    }

    @Override
    public void updateByName(FacultyDto facultyDto) throws NotFoundException {
        String name = facultyDto.getName();
        String f = facultyDao.findByName(name);
        if (f==null) {
            throw new NotFoundException("Faculty " +name+" does not exist");
        }
        facultyDao.updateByName(name, facultyDto.getAddress(), facultyDto.getContactEmail());
    }


    private static class FacultyMapper {
        private static Faculty map(FacultyDto facultyDto) {
            return new Faculty(facultyDto.getName(), facultyDto.getAddress(), facultyDto.getContactEmail());
        }
    }

}
