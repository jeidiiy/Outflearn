package io.jeidiiy.outflearn.common.infrastructure;

import java.util.UUID;

import org.springframework.stereotype.Component;

import io.jeidiiy.outflearn.common.service.port.UuidHolder;

@Component
public class SystemUuidHolder implements UuidHolder {
	@Override
	public String random() {
		return UUID.randomUUID().toString();
	}
}
