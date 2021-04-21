package lk.wisdom_institute.asset.batch.dao;


import lk.wisdom_institute.asset.batch.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BatchDao extends JpaRepository< Batch, Integer> {

}
