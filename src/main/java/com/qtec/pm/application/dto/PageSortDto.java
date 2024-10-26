package com.qtec.pm.application.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@RequiredArgsConstructor
public class PageSortDto {


    private int page = 0;
    private int size = 10;
    private String sortBy = "price";
    private String sortDirection = "asc";

}
