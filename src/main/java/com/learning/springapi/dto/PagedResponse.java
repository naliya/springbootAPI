package com.learning.springapi.dto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class PagedResponse<T> {

    private List<T> content;

    @Schema(example = "1")
    private int page;

    @Schema(example = "1")
    private int size;
    @Schema(example = "1")
    private long totalElements;
    @Schema(example = "1")
    private int totalPages;
    @Schema(example = "true")
    private boolean first;
    @Schema(example = "false")
    private boolean last;

    public PagedResponse(
            List<T> content,
            int page,
            int size,
            long totalElements,
            int totalPages,
            boolean first,
            boolean last
    ) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.first = first;
        this.last = last;
    }

    public List<T> getContent() { return content; }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public long getTotalElements() { return totalElements; }
    public int getTotalPages() { return totalPages; }
    public boolean isFirst() { return first; }
    public boolean isLast() { return last; }
}


// Original response for pagination
//{
//        "status": 200,
//        "message": "Users retrieved successfully",
//        "data": {
//        "content": [
//        {
//        "id": 1,
//        "age": 29,
//        "name": "Liam",
//        "email": "liam@sample.com"
//        },
//        {
//        "id": 2,
//        "age": 32,
//        "name": "Jonathan",
//        "email": "jonathan@sample.com"
//        },
//        {
//        "id": 3,
//        "age": 36,
//        "name": "Raymond",
//        "email": "raymond@sample.com"
//        }
//        ],
//        "empty": false,
//        "first": true,
//        "last": false,
//        "number": 0,
//        "numberOfElements": 3,
//        "pageable": {
//        "offset": 0,
//        "pageNumber": 0,
//        "pageSize": 3,
//        "paged": true,
//        "sort": {
//        "empty": true,
//        "sorted": false,
//        "unsorted": true
//        },
//        "unpaged": false
//        },
//        "size": 3,
//        "sort": {
//        "empty": true,
//        "sorted": false,
//        "unsorted": true
//        },
//        "totalElements": 9,
//        "totalPages": 3
//        },
//        "timestamp": "2026-01-12T08:33:11.989170Z"
// }
