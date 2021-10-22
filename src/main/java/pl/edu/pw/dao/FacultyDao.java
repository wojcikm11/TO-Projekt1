package pl.edu.pw.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pw.entity.Faculty;

import javax.transaction.Transactional;

@Repository
public interface FacultyDao extends JpaRepository<Faculty, Long> {

    @Transactional
    @Modifying
    void deleteByName(String name);

    Faculty findByName(String name);
}
