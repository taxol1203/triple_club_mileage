package com.triple.clubMileage.RepositoryTest;

import com.triple.clubMileage.Repository.HistoryRepository;
import com.triple.clubMileage.Repository.UserRepository;
import com.triple.clubMileage.domain.History;
import com.triple.clubMileage.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class HistoryRepositoryTest {

    @Autowired
    HistoryRepository historyRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void save() throws Exception {
        //given
        /*User user1 = new User();
        user1.setName("taxol");
        user1.setId(UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745"));
        user1.setPoint(10);
        userRepository.save(user1);*/

        User user = userRepository.findById(UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f746")).get();

        History history = new History();
        history.setPoint(10);
        history.setUser(user);

        //when
        historyRepository.save(history);        // https://reeme.tistory.com/39 -> 참조 무결성 위배
        //List<History> findHistory = historyRepository.findByUser(history.getUser().getName());
        List<History> findHistory = historyRepository.findAll();

        //then
        Assertions.assertThat(findHistory.get(0).getUser().getName()).isEqualTo(history.getUser().getName());
    }

    @Test
    void findByUser() {
    }

    @Test
    void findAll() {
    }
}