package ru.practicum.ewm.storage.users;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.users.User;

import java.util.List;

/**
 * Репозиторий для работы с пользователями
 */
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByOrderByIdAsc(Pageable pageable);

    @Query(value = "select * from users where id in (:ids)", nativeQuery = true)
    List<User> findAllByIds(List<Integer> ids);
}
