package com.example.jpacachespring.aop.repo;

import com.example.jpacachespring.aop.model.PersonDTO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonJPARepository extends JpaRepositoryTest<PersonDTO, Integer> {

    //@CacheRequest
    List<PersonDTO> findByNameAndAge(String name, Integer age);

    List<PersonDTO> findByParentDTOId(Integer parentId);

    List<PersonDTO> findByParentDTONameAndAge(String parentName, Integer age);

    PersonDTO save(PersonDTO entity);
}
