package io.jeidiiy.outflearn.user.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.jeidiiy.outflearn.user.domain.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
