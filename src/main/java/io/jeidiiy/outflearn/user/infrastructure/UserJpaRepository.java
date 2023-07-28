package io.jeidiiy.outflearn.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import io.jeidiiy.outflearn.user.domain.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}
