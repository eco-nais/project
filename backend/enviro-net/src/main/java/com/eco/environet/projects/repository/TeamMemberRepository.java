package com.eco.environet.projects.repository;

import com.eco.environet.projects.model.TeamMember;
import com.eco.environet.projects.model.id.TeamMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, TeamMemberId> {

    List<TeamMember> findByProjectId(Long projectId);

    List<TeamMember> findAllByProjectIdAndUserIdIn(Long projectId, List<Long> userIds);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END " +
            "FROM TeamMember t " +
            "WHERE t.projectId = :projectId AND t.userId = :userId")
    Boolean isOnTeam(Long projectId, Long userId);
}
