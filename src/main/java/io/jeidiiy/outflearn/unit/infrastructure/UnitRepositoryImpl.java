package io.jeidiiy.outflearn.unit.infrastructure;

import io.jeidiiy.outflearn.unit.domain.Unit;
import io.jeidiiy.outflearn.unit.service.port.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UnitRepositoryImpl implements UnitRepository {

	private final UnitJpaRepository unitJpaRepository;
	@Override
	public Optional<Unit> findById(Long id) {
		return unitJpaRepository.findById(id);
	}

	@Override
	public void save(Unit unit) {
		unitJpaRepository.save(unit);
	}
}
