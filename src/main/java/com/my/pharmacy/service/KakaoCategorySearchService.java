package com.my.pharmacy.service;

import com.my.pharmacy.dto.DocumentDto;
import com.my.pharmacy.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoCategorySearchService {
    private final RestTemplate restTemplate;
    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;
    private static final String KAKAO_CATEGORY_URL = "https://dapi.kakao.com/v2/local/search/category";
    //카테고리 상수(약국)
    //private static final String CATEGORY = "PM9";
    private static final String CATEGORY = "CE7";
    public KakaoApiResponseDto resultCategorySearch(DocumentDto dto) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(KAKAO_CATEGORY_URL);
        // 1. 카테고리
        uriBuilder.queryParam("category_group_code", CATEGORY);
        // 2. x값, y값
        uriBuilder.queryParam("x", dto.getLongitude());
        uriBuilder.queryParam("y", dto.getLatitude());
        // 3. radius
        uriBuilder.queryParam("radius", 1000);
        // 4. 검색 사이즈 - 나중에 처리
        // 5. 정렬 처리
        uriBuilder.queryParam("sort", "distance");
        //url에 포함된 한글을 UTF-8 인코딩 처리
        URI uri = uriBuilder.build().encode().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, kakaoRestApiKey);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        return restTemplate
                .exchange(
                        uri,
                        HttpMethod.GET,
                        httpEntity,
                        KakaoApiResponseDto.class
                ).getBody();
    }
}
