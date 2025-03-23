package com.bikkadIt.ElectronicStore.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableResponse<T> {

    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalEelements;
    private int totalPages;
    private boolean lastpage;

    public void setTotalElements(long totalElements) {
    }


    public void setLastpage(boolean last) {
    }

    public void setLastPage(boolean last) {
    }
}
