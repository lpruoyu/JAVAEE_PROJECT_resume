package programmer.lp.resume.util;

import programmer.lp.resume.bean.Contact;
import programmer.lp.resume.dao.impl.ContactDaoImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        AutoGenerateCode.generateTPL();
    }

    public static void testContactSearch() {
        ContactDaoImpl daoImpl = new ContactDaoImpl();
        Contact.Search search = new Contact.Search();
//        search.pageIndex = 3;
        search.keyWord = "720";
//        search.beginDay = "2099-01-01";
//        search.endDay = "2091-10-20";
        final List<Contact> list = daoImpl.list(search).getContacts();
        for (Contact contact : list) {
            System.out.println(contact.getJson());
        }
    }

}
