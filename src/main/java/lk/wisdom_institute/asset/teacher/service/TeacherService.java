package lk.wisdom_institute.asset.teacher.service;

import lk.wisdom_institute.asset.teacher.dao.TeacherDao;
import lk.wisdom_institute.asset.teacher.entity.Teacher;
import lk.wisdom_institute.util.interfaces.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService implements AbstractService< Teacher, Integer> {
    private final TeacherDao teacherDao;

    public TeacherService(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    public List<Teacher> findAll() {
        return teacherDao.findAll();
    }

    public Teacher findById(Integer id) {
        return teacherDao.getOne(id);
    }

    public Teacher persist(Teacher teacher) {
        return teacherDao.save(teacher);
    }

    public boolean delete(Integer id) {
        teacherDao.deleteById(id);
        return false;
    }

    public List<Teacher> search(Teacher teacher) {
        return null;
    }


    public Teacher lastTeacher() {
        return teacherDao.findFirstByOrderByIdDesc();
    }

    public Teacher lastTeacherOnDB() {return teacherDao.findFirstByOrderByIdDesc();
    }
}
