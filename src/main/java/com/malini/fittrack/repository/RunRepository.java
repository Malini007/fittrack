package com.malini.fittrack.repository;

import com.malini.fittrack.model.Run;
import com.malini.fittrack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface RunRepository extends JpaRepository<Run, Long> {
    List<Run> findByUserOrderByRunDateDesc(User user);

    @Query("SELECT SUM(r.distanceKm) FROM Run r WHERE r.user = :user")
    Double getTotalDistanceByUser(User user);

    @Query("SELECT AVG(r.durationMinutes / r.distanceKm) FROM Run r WHERE r.user = :user")
    Double getAvgPaceByUser(User user);
}