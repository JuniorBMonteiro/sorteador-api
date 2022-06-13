package br.com.bmont.sorteador.repository;

import br.com.bmont.sorteador.model.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Query("SELECT p FROM Participant p WHERE p.group.id = ?1 order by rand()")
    Page<Participant> findParticipantsByGroupId(Long groupId, Pageable pageable);

    @Query("SELECT p FROM Participant p WHERE p.group.id = ?1 and p.isActive = true order by rand()")
    Page<Participant> findActiveParticipantsByGroupId(Long groupId, Pageable pageable);
}