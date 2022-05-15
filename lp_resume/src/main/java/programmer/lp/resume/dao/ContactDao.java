package programmer.lp.resume.dao;

import programmer.lp.resume.base.BaseDao;
import programmer.lp.resume.bean.Contact;
import programmer.lp.resume.bean.ContactResult;

public interface ContactDao extends BaseDao<Contact> {

    ContactResult list(Contact.Search search);

    boolean read(Integer id);
}