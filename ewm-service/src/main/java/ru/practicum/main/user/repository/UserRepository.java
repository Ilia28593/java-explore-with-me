package ru.practicum.main.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.user.model.User;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);

    Collection<User> getUsersByIdIn(Collection<Long> ids, Pageable pageable);

    void removeUserById(Long userId);

    User getUserById(Long userId);
}