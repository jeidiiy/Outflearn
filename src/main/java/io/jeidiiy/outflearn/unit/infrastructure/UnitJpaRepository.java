package io.jeidiiy.outflearn.unit.infrastructure;

import io.jeidiiy.outflearn.unit.domain.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitJpaRepository extends JpaRepository<Unit, Long> {
}
