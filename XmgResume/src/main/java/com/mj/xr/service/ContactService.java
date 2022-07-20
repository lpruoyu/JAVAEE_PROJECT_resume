package com.mj.xr.service;

import com.mj.xr.bean.Contact;
import com.mj.xr.bean.ContactListParam;
import com.mj.xr.bean.ContactListResult;

public interface ContactService extends BaseService<Contact> {
    ContactListResult list(ContactListParam param);
    boolean read(Integer id);
}
