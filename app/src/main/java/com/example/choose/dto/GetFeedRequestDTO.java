package com.example.choose.dto;

public class GetFeedRequestDTO {
    private Integer offset;
    private Integer page;
    private Integer size;

    public GetFeedRequestDTO(Integer offset, Integer page, Integer size) {
        this.offset = offset;
        this.page = page;
        this.size = size;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
