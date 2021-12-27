package com.parse.sql.repository;

import java.util.ArrayList;
import java.util.List;

public class SQLCondition {

    private  String column;
    private  String operator;
    private  String dataType;
    private final List<Object> values = new ArrayList<Object>();

    public SQLCondition(){}

//    public SQLCondition(String column, String operator) {
//        this.column = column;
//        this.operator = operator;
//    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void addValue(Object value) {
        this.values.add(value);
    }

    public List<Object> getValues() {
        return values;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void clear() {
        this.column=null;
        this.operator=null;
        this.values.clear();
    }

    @Override
    public String toString() {
        return column  + "{"+operator +"}"+ values;
    }
}
