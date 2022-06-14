package br.com.bmont.sorteador.repository;

import br.com.bmont.sorteador.model.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.servlet.http.Part;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Query("SELECT p FROM Participant p WHERE p.group.id = ?1 ORDER BY rand()")
    Page<Participant> findParticipantsByGroupIdOrderByRandom(Long groupId, Pageable pageable);

    @Query("SELECT p FROM Participant p WHERE p.group.id = ?1")
    Page<Participant> findParticipantsByGroupId(Long groupId, Pageable pageable);

    @Query("SELECT p FROM Participant p WHERE p.group.id = ?1 AND p.isActive = true ORDER BY rand()")
    Page<Participant> findActiveParticipantsByGroupIdOrderByRandom(Long groupId, Pageable pageable);
}