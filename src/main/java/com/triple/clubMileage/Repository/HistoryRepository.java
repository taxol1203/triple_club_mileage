package com.triple.clubMileage.Repository;

import com.triple.clubMileage.domain.History;
import com.triple.clubMileage.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HistoryRepository {

    private final EntityManager em;

    public void save(History history) {
        em.persist(history);
    }

    public List<History> findByUser(String user){
        return em.createQuery("select h from History h where h.id = :user", History.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<History> findAll() {
        return em.createQuery("select h from History h", History.class)
                .getResultList();
    }
}
