package io.jeidiiy.outflearn.unit.service.port;

import io.jeidiiy.outflearn.unit.domain.Unit;

import java.util.Optional;

public interface UnitRepository {
	Optional<Unit> findById(Long id);

	void save(Unit unit);
}
