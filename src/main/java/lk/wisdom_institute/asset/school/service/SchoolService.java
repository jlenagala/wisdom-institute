package lk.wisdom_institute.asset.school.service;


import lk.wisdom_institute.asset.school.dao.SchoolDao;
import lk.wisdom_institute.asset.school.entity.School;
import lk.wisdom_institute.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService implements AbstractService< School, Integer> {

    private final SchoolDao schoolDao;
    @Autowired
    public SchoolService(SchoolDao schoolDao){
        this.schoolDao = schoolDao;
    }



    public List< School > findAll() {
        return schoolDao.findAll();
    }


    public School findById(Integer id) {
        return schoolDao.getOne(id);
    }


    public School persist(School school) {
        return schoolDao.save(school);
    }


    public boolean delete(Integer id) {
        schoolDao.deleteById(id);
        return false;
    }


    public List< School > search(School school) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< School > sampleCollectingTubeExample = Example.of(school, matcher);
        return schoolDao.findAll(sampleCollectingTubeExample);
    }
}
