package com.zhw.ms.commons.db;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2016/5/10 0010.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Transactional
public @interface MasterDataSource {
}
