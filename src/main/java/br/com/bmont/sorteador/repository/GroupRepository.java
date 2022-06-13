package br.com.bmont.sorteador.repository;

import br.com.bmont.sorteador.model.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("SELECT g FROM Group g WHERE g.user.id = ?1")
    Page<Group> findAllGroupsByUser(Long userId, Pageable pageable);

    @Query("SELECT g FROM Group g WHERE g.id = ?1 AND g.user.id = ?2")
    Group findGroupById(Long groupId, Long userId);
}