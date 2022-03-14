package br.com.bmont.sorteador.repository;

import br.com.bmont.sorteador.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    @Query(value = "select * from tb_participant where group_id = ?1", nativeQuery = true)
    List<Participant> findParticipantsByGroupId(Long groupId);
}