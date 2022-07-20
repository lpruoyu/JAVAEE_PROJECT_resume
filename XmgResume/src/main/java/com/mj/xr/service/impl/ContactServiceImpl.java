package com.mj.xr.service.impl;

import com.mj.xr.bean.Contact;
import com.mj.xr.bean.ContactListParam;
import com.mj.xr.bean.ContactListResult;
import com.mj.xr.dao.ContactDao;
import com.mj.xr.service.ContactService;


public class ContactServiceImpl extends BaseServiceImpl<Contact> implements ContactService {

    @Override
    public ContactListResult list(ContactListParam param) {
        return ((ContactDao) dao).list(param);
    }

    @Override
    public boolean read(Integer id) {
        return ((ContactDao) dao).read(id);
    }
}
