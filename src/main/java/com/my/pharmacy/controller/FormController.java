package com.my.pharmacy.controller;

import com.my.pharmacy.dto.DocumentDto;
import com.my.pharmacy.dto.KakaoApiResponseDto;
import com.my.pharmacy.dto.OutputDto;
import com.my.pharmacy.service.KakaoAddressSearchService;
import com.my.pharmacy.service.KakaoCategorySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FormController {
    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final KakaoCategorySearchService kakaoCategorySearchService;

    @GetMapping
    public String mainForm(){
        return "main";
    }

    @GetMapping("/output")
    public String outputForm() {
        return "output";
    }

    @PostMapping("/search")
    public String searchAddress(@RequestParam("address")String address,
                                Model model) {
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);
        log.info("결과 : " + kakaoApiResponseDto);
        // 결과 중 Documents만 빼서 dto 저장
        DocumentDto documentDto = kakaoApiResponseDto
                .getDocumentList().get(0);
        KakaoApiResponseDto kakaoApiCategoryDto = kakaoCategorySearchService.resultCategorySearch(documentDto);
        log.info("카테고리 검색 결과 : " + kakaoApiCategoryDto);
        List<OutputDto> resultList = kakaoCategorySearchService.toOutputDtoList(kakaoApiCategoryDto.getDocumentList());
        model.addAttribute("dtoList", resultList);
        return "output";
    }
}
