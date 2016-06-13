package gov.samhsa.mhc.pcm.service.dto;

import java.util.List;
import java.util.Map;

/**
 * Created by Jiahao.Li on 6/12/2016.
 */
public class ActivityHistoryListDto {
    private List<HistoryDto> historyDtoList;
    private long totalItems;
    private int totalPages;
    private int itemsPerPage;
    private int currentPage;

    public ActivityHistoryListDto() {
    }

    public ActivityHistoryListDto(Map<String, Object> pageResults) {
        this.historyDtoList = (List<HistoryDto>) pageResults.get("results");
        this.currentPage = (int) pageResults.get("currentPage");
        this.itemsPerPage = (int) pageResults.get("itemsPerPage");
        this.totalPages = (int) pageResults.get("totalPages");
        this.totalItems = (long) pageResults.get("totalItems");
    }

    public List<HistoryDto> getHistoryDtoList() {
        return historyDtoList;
    }

    public void setHistoryDtoList(List<HistoryDto> historyDtoList) {
        this.historyDtoList = historyDtoList;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }
}
