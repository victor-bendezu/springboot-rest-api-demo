package com.victor.portfolio.restapi.common;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagedResponse<T> {

    private List<T> items;

    private int page;            // 0-based
    private int size;

    private long totalItems;
    private int totalPages;

    private boolean hasNext;
    private boolean hasPrevious;

    private String sort;         // e.g. "price: DESC, name: ASC"
}