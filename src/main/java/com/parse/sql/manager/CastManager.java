package com.parse.sql.manager;

import com.parse.sql.common.Constants;
import com.parse.sql.repository.SQLCondition;
import com.parse.sql.rules.SQLRules;

import java.util.List;
import java.util.Locale;

/**
 * function_cast
 * cast(str as int)有风险
 */
public class CastManager implements Manager {

    @Override
    public String getSuggest(Object cons) {
        List<SQLCondition> conditions = (List<SQLCondition>)cons;
        for (SQLCondition eachCon:conditions) {
            if (eachCon.getColumn().contains(Constants.PG_CAST)){
                if(eachCon.getDataType().toLowerCase(Locale.ROOT).contains(Constants.PG_INT)){
                    return SQLRules.function_cast.getRuleContent();
                }
            }
        }
        return "";
    }


    @Override
    public String getOperate(Object cons) {
        return null;
    }

    @Override
    public String getLeft(Object cons) {
        return null;
    }

    @Override
    public String getRight(Object cons) {
        return null;
    }

    @Override
    public boolean hasType(Object cons) {
        List<SQLCondition> conditions = (List<SQLCondition>)cons;
        for (SQLCondition eachCon:conditions) {
            if (eachCon.getColumn().contains(Constants.PG_CAST)){
                return true;
            }
        }
        return false;
    }
}
