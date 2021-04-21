package lk.wisdom_institute.asset.batch.service;


import lk.wisdom_institute.util.interfaces.AbstractService;
import lk.wisdom_institute.asset.batch.dao.BatchDao;
import lk.wisdom_institute.asset.batch.entity.Batch;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchService implements AbstractService<Batch, Integer> {
    private final BatchDao batchDao;

    public BatchService(BatchDao batchDao) {
        this.batchDao = batchDao;
    }

    public List<Batch> findAll() {
        return batchDao.findAll();
    }

    public Batch findById(Integer id) {
        return batchDao.getOne(id);
    }

    public Batch persist(Batch batch) {
        return batchDao.save(batch);
    }

    public boolean delete(Integer id) {
        batchDao.deleteById(id);
        return false;
    }

    public List<Batch> search(Batch batch) {
        return null;
    }



}
