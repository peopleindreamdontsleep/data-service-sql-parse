package com.parse.sql.manager;

import com.parse.sql.repository.SQLCondition;

import java.util.List;

public interface Manager {
    String getSuggest(List<SQLCondition> cons);

    String getOperate(List<SQLCondition> cons);

    String getLeft(List<SQLCondition> cons);

    String getRight(List<SQLCondition> cons);

    boolean getType(List<SQLCondition> cons);
}
