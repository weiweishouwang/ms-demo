package com.zhw.ms.demo.service;

import com.zhw.ms.commons.db.SlaverDataSource;
import com.zhw.ms.demo.entity.Admin;
import com.zhw.ms.demo.persistence.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ZHW on 2015/5/8.
 */
@Service
public class DemoService {
    @Autowired
    private AdminMapper adminMapper;

    @SlaverDataSource
    public Admin getAdmin(Long id) {
        return adminMapper.selectByPrimaryKey(id);
    }

}
