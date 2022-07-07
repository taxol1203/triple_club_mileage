package com.triple.clubMileage.Repository;

import com.triple.clubMileage.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
