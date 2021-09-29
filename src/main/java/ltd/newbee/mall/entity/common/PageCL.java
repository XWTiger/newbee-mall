package ltd.newbee.mall.entity.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.pagehelper.Page;

import java.util.ArrayList;
import java.util.List;

public class PageCL<T> {
    @JsonProperty("page_num")
    private int pageNum;
    @JsonProperty("page_size")
    private int pageSize;
    private int size;
    private int total;
    @JsonProperty("page_count")
    private int pageCount;
    private List<T> list = new ArrayList<>();

    /**
     * 返回对应分页的list内容
     * @param pageNum
     * @param pageSize
     * @param list
     * @return
     * @throws Exception
     */
    public PageCL<T> getPageInfo(int pageNum, int pageSize, List<T> list)throws Exception {
        List<T> result = new ArrayList<T>();
        PageCL<T> pageCL = new PageCL<>();
        if (list != null && list.size() > 0) {
            int allCount = list.size();

            int pageCount = (allCount + pageSize - 1) / pageSize;
            if (pageNum >= pageCount) {
                pageNum = pageCount;
            }
            int start = (pageNum - 1) * pageSize;
            int end = pageNum * pageSize;
            if (end >= allCount) {
                end = allCount;
            }
            for (int i = start; i < end; i++) {
                result.add(list.get(i));
            }
            if(result != null && result.size() > 0) {
                pageCL.setTotal(allCount);
                pageCL.setSize(pageSize);
                pageCL.setPageNum(pageNum);
                pageCL.setPageSize(pageSize);
                pageCL.setPageCount(pageCount);
                pageCL.setList(result);
                return pageCL;
            }
        } else {
            pageCL.setTotal(0);
            pageCL.setSize(0);
            pageCL.setPageNum(pageNum);
            pageCL.setPageSize(pageSize);
            pageCL.setPageCount(0);
            pageCL.setList(new ArrayList<>());
        }
        return pageCL;
    }

    public PageCL<T> getPageByPageHelper(Page page, List<T> list)throws Exception {
        PageCL<T> pageCL = new PageCL<>();
        pageCL.setList(list);
        pageCL.setPageNum(page.getPageNum());
        pageCL.setPageSize(page.getPageSize());
        pageCL.setTotal(Integer.parseInt(String.valueOf(page.getTotal())));
        pageCL.setPageCount(Integer.valueOf(page.getPages()));
        pageCL.setSize(page.getPageSize());
        return pageCL;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
