package programmer.lp.resume.service;

import programmer.lp.resume.base.BaseService;
import programmer.lp.resume.bean.Contact;
import programmer.lp.resume.bean.ContactResult;

public interface ContactService extends BaseService<Contact> {

    ContactResult list(Contact.Search search);

    boolean read(Integer id);

}
