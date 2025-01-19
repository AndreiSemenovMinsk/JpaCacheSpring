package com.example.jpacachespring.aop.repo;

import com.example.jpacachespring.aop.model.ParentDTO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentJPARepository extends JpaRepositoryTest<ParentDTO, Integer> {

    List<ParentDTO> findByNameAndAge(String name, Integer age);

    ParentDTO save(ParentDTO entity);
}
