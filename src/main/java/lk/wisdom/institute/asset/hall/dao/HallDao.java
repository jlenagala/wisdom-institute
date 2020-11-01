package lk.wisdom.institute.asset.hall.dao;


import lk.wisdom.institute.asset.hall.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HallDao extends JpaRepository<Hall, Integer> {

}
