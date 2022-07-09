package com.triple.clubMileage.service;

import com.triple.clubMileage.Repository.UserRepository;
import com.triple.clubMileage.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final HistoryService historyService;

    @Transactional(readOnly = true)
    public User getUser(String userId) {
        Optional<User> userOptional = userRepository.findById(UUID.fromString(userId));
        User user;
        if(userOptional.isPresent()){
            user = userOptional.get();
        } else {
            throw new EntityNotFoundException(
                    "Cant find any user under given userId");
        }

        return user;
    }

    @Transactional
    public void updatePoint(User user, int calPoint) {
        int point = user.getPoint();
        user.setPoint(point + calPoint);
        userRepository.save(user);

        // 포인트 변경 내역 저장
        historyService.saveHistory(user, calPoint);
    }
}
