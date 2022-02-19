package com.parse.sql.manager;

import com.parse.sql.repository.SQLCondition;

import java.util.List;

/**
 * 弃用，直接基于druid进行改造，不需要单独实现
 */
public interface Manager {

    String getSuggest(Object cons);

    String getOperate(Object cons);

    String getLeft(Object cons);

    String getRight(Object cons);

    boolean hasType(Object cons);

}
