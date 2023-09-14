package io.jeidiiy.outflearn.unit.controller.port;

import io.jeidiiy.outflearn.unit.controller.request.UnitRequest;
import io.jeidiiy.outflearn.unit.domain.Unit;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UnitService {
	String getUrlById(Long id);
	void create(UnitRequest unitRequest, MultipartFile file) throws IOException;
}
