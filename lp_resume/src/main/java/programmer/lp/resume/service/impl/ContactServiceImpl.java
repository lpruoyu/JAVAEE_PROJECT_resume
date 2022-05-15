package programmer.lp.resume.service.impl;

import programmer.lp.resume.base.BaseServiceImpl;
import programmer.lp.resume.bean.Contact;
import programmer.lp.resume.bean.ContactResult;
import programmer.lp.resume.dao.ContactDao;
import programmer.lp.resume.service.ContactService;

public class ContactServiceImpl extends BaseServiceImpl<Contact> implements ContactService {
    @Override
    public ContactResult list(Contact.Search search) {
        return ((ContactDao) dao()).list(search);
    }

    @Override
    public boolean read(Integer id) {
        return ((ContactDao) dao()).read(id);
    }
}