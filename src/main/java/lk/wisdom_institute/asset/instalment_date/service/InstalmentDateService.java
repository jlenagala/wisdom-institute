package lk.wisdom_institute.asset.instalment_date.service;



import lk.wisdom_institute.asset.common_asset.model.enums.LiveDead;
import lk.wisdom_institute.asset.instalment_date.dao.InstalmentDateDao;
import lk.wisdom_institute.asset.instalment_date.entity.InstalmentDate;
import lk.wisdom_institute.util.interfaces.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstalmentDateService implements AbstractService< InstalmentDate, Integer > {
  private final InstalmentDateDao intalmentDateDao;

  public InstalmentDateService(InstalmentDateDao instalmentDateDao) {
    this.intalmentDateDao = instalmentDateDao;
  }

  public List< InstalmentDate > findAll() {
    return intalmentDateDao.findAll()
        .stream()
        .filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE))
        .collect(Collectors.toList());
  }

    public List< InstalmentDate > findAllDeleted() {
        return intalmentDateDao.findAll()
            .stream()
            .filter(x -> x.getLiveDead().equals(LiveDead.STOP))
            .collect(Collectors.toList());
    }


    public InstalmentDate findById(Integer id) {
    return intalmentDateDao.getOne(id);
  }

  public InstalmentDate persist(InstalmentDate instalmentDate) {
    if ( instalmentDate.getId() == null ) {
      instalmentDate.setLiveDead(LiveDead.ACTIVE);
    }
    return intalmentDateDao.save(instalmentDate);
  }

  public boolean delete(Integer id) {
    InstalmentDate intalmentDate = intalmentDateDao.getOne(id);
    intalmentDate.setLiveDead(LiveDead.STOP);
    intalmentDateDao.save(intalmentDate);
    return false;
  }

  public List< InstalmentDate > search(InstalmentDate instalmentDate) {
    return null;
  }


  public InstalmentDate lastInstalmentDate() {
    return intalmentDateDao.findFirstByOrderByIdDesc();
  }
}
