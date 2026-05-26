package com.my.pharmacy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DocumentDto {
    //=========================================================
    //주소로 좌표값 찾았을 때 얻어올 내용
    @JsonProperty("address_name")
    private String addressName;

    @JsonProperty("x")
    private double longitude; //경도

    @JsonProperty("y")
    private double latitude; //위도

    //=========================================================
    // 카테고리로 검색할 때 나오는 내용
    @JsonProperty("place_name")
    private String placeName;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("distance")
    private String distance;
}
