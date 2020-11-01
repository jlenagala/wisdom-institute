package lk.wisdom.institute.asset.subject.service;


import lk.wisdom.institute.util.interfaces.AbstractService;
import lk.wisdom.institute.asset.subject.dao.SubjectDao;
import lk.wisdom.institute.asset.subject.entity.Subject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService implements AbstractService<Subject, Integer> {
    private final SubjectDao subjectDao;

    public SubjectService(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }

    public List<Subject> findAll() {
        return subjectDao.findAll();
    }

    public Subject findById(Integer id) {
        return subjectDao.getOne(id);
    }

    public Subject persist(Subject subject) {
        return subjectDao.save(subject);
    }

    public boolean delete(Integer id) {
        subjectDao.deleteById(id);
        return false;
    }

    public List<Subject> search(Subject subject) {
        return null;
    }



}
