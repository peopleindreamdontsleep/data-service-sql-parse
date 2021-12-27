package com.parse.sql.manager;

import com.parse.sql.common.Constants;
import com.parse.sql.repository.SQLCondition;
import com.parse.sql.rules.SQLRules;

import java.util.List;

public class NotManager implements Manager{

    @Override
    public String getSuggest(List<SQLCondition> cons) {
        for (SQLCondition eachCon:cons) {
            if (eachCon.getOperator().contains(Constants.PG_NOT)){
               return SQLRules.condition_not.getRuleContent();
            }
        }
        return null;
    }

    @Override
    public String getOperate(List<SQLCondition> cons) {
        return null;
    }

    @Override
    public String getLeft(List<SQLCondition> cons) {
        return null;
    }

    @Override
    public String getRight(List<SQLCondition> cons) {
        return null;
    }

    @Override
    public boolean getType(List<SQLCondition> cons) {
        for (SQLCondition eachCon:cons) {
            if (eachCon.getOperator().contains(Constants.PG_NOT)){
                return true;
            }
        }
        return false;
    }
}
