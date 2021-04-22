package lk.wisdom_institute.asset.batch.service;


import lk.wisdom_institute.asset.batch.dao.BatchDao;
import lk.wisdom_institute.asset.batch.entity.Batch;
import lk.wisdom_institute.asset.common_asset.model.enums.LiveDead;
import lk.wisdom_institute.util.interfaces.AbstractService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchService implements AbstractService< Batch, Integer > {
  private final BatchDao batchDao;

  public BatchService(BatchDao batchDao) {
    this.batchDao = batchDao;
  }

  public List< Batch > findAll() {
    return batchDao.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList());
  }

  public Batch findById(Integer id) {
    return batchDao.getOne(id);
  }

  public Batch persist(Batch batch) {
    if ( batch.getId() == null ) {
      batch.setLiveDead(LiveDead.ACTIVE);
    }
    return batchDao.save(batch);
  }

  public boolean delete(Integer id) {
    Batch batch = batchDao.getOne(id);
    batch.setLiveDead(LiveDead.STOP);
    batchDao.save(batch);
    return false;
  }

  public List< Batch > search(Batch batch) {
    return null;
  }

  public Batch lastBatchOnDB() {
    return batchDao.findFirstByOrderByIdDesc();
  }


  public Batch findByName(String name) {
    return batchDao.findByName(name);
  }


  public Batch findByNameAndStartAtIsBetweenAndEndAtIsBetween(String name, LocalDate startAt, LocalDate endAt, LocalDate startAt1, LocalDate endAt1) {
  return batchDao.findByNameAndStartAtIsBetweenAndEndAtIsBetween(name,startAt,endAt,startAt1,endAt1);
  }
}
