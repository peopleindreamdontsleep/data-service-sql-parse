package com.parse.sql.manager;

import com.parse.sql.common.Constants;
import com.parse.sql.repository.SQLCondition;
import com.parse.sql.rules.SQLRules;

import java.util.List;
import java.util.Locale;

/**
 * cast(str as int)有风险
 */
public class CastManager implements Manager {

    @Override
    public String getSuggest(List<SQLCondition> cons) {
        for (SQLCondition eachCon:cons) {
            if (eachCon.getColumn().contains(Constants.PG_CAST)){
                if(eachCon.getDataType().toLowerCase(Locale.ROOT).contains(Constants.PG_INT)){
                    return SQLRules.function_cast.getRuleContent();
                }
            }
        }
        return "";
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
            if (eachCon.getColumn().contains(Constants.PG_CAST)){
                return true;
            }
        }
        return false;
    }
}
