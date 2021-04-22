package lk.wisdom_institute.asset.instalment_date.service;


import lk.real_way_institute.asset.instalment_date.dao.InstalmentDateDao;
import lk.real_way_institute.asset.instalment_date.entity.InstalmentDate;
import lk.real_way_institute.util.interfaces.AbstractService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InstalmentDateService implements AbstractService< InstalmentDate, Integer > {
  private final InstalmentDateDao intalmentDateDao;

  public InstalmentDateService(InstalmentDateDao instalmentDateDao) {
    this.intalmentDateDao = instalmentDateDao;
  }

  public List< InstalmentDate > findAll() {
    return intalmentDateDao.findAll();
  }

    public List< InstalmentDate > findAllDeleted() {
        return intalmentDateDao.findAll();
    }


    public InstalmentDate findById(Integer id) {
    return intalmentDateDao.getOne(id);
  }

  public InstalmentDate persist(InstalmentDate instalmentDate) {

    return intalmentDateDao.save(instalmentDate);
  }

  public boolean delete(Integer id) {
//    InstalmentDate intalmentDate = intalmentDateDao.getOne(id);
//    intalmentDate.setLiveDead(LiveDead.STOP);
//    intalmentDateDao.save(intalmentDate);
    return false;
  }

  public List< InstalmentDate > search(InstalmentDate instalmentDate) {
    return null;
  }


  public InstalmentDate lastInstalmentDate() {
    return intalmentDateDao.findFirstByOrderByIdDesc();
  }

  public List< InstalmentDate > findByDateBefore(LocalDate localDate) {
    return intalmentDateDao.findByDateBefore(localDate);
  }
}
