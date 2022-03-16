package br.com.bmont.sorteador.repository;

import br.com.bmont.sorteador.model.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query(value = "select * from tb_group where user_id = ?1", nativeQuery = true)
    Page<Group> findAllGroupsByUser(Long userId, Pageable pageable);

    @Query(value = "select * from tb_group where id = ?1 and user_id = ?2", nativeQuery = true)
    Group findGroupById(Long groupId, Long userId);
}