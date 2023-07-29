package io.jeidiiy.outflearn.mock;

import io.jeidiiy.outflearn.common.service.port.UuidHolder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestUuidHolder implements UuidHolder {

	private final String uuid;

	@Override
	public String random() {
		return uuid;
	}
}
