package com.mj.xr.dao;

import com.mj.xr.bean.Contact;
import com.mj.xr.bean.ContactListParam;
import com.mj.xr.bean.ContactListResult;

public interface ContactDao extends BaseDao<Contact> {
    ContactListResult list(ContactListParam param);
    boolean read(Integer id);
}
