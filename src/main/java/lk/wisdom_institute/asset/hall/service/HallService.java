package lk.wisdom_institute.asset.hall.service;


import lk.wisdom_institute.util.interfaces.AbstractService;
import lk.wisdom_institute.asset.hall.dao.HallDao;
import lk.wisdom_institute.asset.hall.entity.Hall;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallService implements AbstractService<Hall, Integer> {
    private final HallDao hallDao;

    public HallService(HallDao hallDao) {
        this.hallDao = hallDao;
    }

    public List<Hall> findAll() {
        return hallDao.findAll();
    }

    public Hall findById(Integer id) {
        return hallDao.getOne(id);
    }

    public Hall persist(Hall hall) {
        return hallDao.save(hall);
    }

    public boolean delete(Integer id) {
        hallDao.deleteById(id);
        return false;
    }

    public List<Hall> search(Hall hall) {
        return null;
    }



}
