package programmer.lp.resume.bean;

import java.util.List;

public class ContactResult {

    private List<Contact> contacts;
    private Contact.Search search;
    private Integer totalSize; // 总条数
    private Integer totalPage; // 总页数

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Contact.Search getSearch() {
        return search;
    }

    public void setSearch(Contact.Search search) {
        this.search = search;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
