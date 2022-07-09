package com.triple.clubMileage.Repository;

import com.triple.clubMileage.domain.History;
import com.triple.clubMileage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {

    Optional<List<History>> findByUser(User user);
}
