package gp.arttx.service;

import gp.arttx.dto.HouseImageResponseDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ImageService {

    @Autowired
    private WebClient webClient;

    public HouseImageResponseDto uploadHouseImage() {

        return new HouseImageResponseDto();  //todo
    }
}
