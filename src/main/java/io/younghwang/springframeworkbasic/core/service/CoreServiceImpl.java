package io.younghwang.springframeworkbasic.core.service;

import io.younghwang.springframeworkbasic.core.dao.CoreDao;

public class CoreServiceImpl implements CoreService {
    private CoreDao dao;

    public CoreDao getDao() {
        return dao;
    }

    public void setDao(CoreDao dao) {
        this.dao = dao;
    }
}
