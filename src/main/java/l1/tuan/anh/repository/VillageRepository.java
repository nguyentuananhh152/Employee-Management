package l1.tuan.anh.repository;

import l1.tuan.anh.entity.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VillageRepository extends JpaRepository<Village, Integer> {
}
