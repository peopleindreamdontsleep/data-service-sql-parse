package com.parse.sql.manager;

import com.parse.sql.repository.SQLCondition;

import java.util.List;

public interface Manager {

    String getSuggest(Object cons);

    String getOperate(Object cons);

    String getLeft(Object cons);

    String getRight(Object cons);

    boolean hasType(Object cons);

}
