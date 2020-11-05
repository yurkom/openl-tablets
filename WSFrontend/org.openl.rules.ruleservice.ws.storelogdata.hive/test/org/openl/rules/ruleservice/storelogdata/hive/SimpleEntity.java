package org.openl.rules.ruleservice.storelogdata.hive;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.openl.rules.ruleservice.storelogdata.hive.annotation.Entity;

@Entity("")
public class SimpleEntity {
    private String id;
    private int integerValue;
    private long longValue;
    private boolean booleanValue;
    private short shortValue;
    private byte byteValue;
    private ZonedDateTime zdtValue;
    private LocalDateTime ldtValue;
    private Object refValue;

    public SimpleEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(int integerValue) {
        this.integerValue = integerValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public short getShortValue() {
        return shortValue;
    }

    public void setShortValue(short shortValue) {
        this.shortValue = shortValue;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public void setByteValue(byte byteValue) {
        this.byteValue = byteValue;
    }

    public ZonedDateTime getZdtValue() {
        return zdtValue;
    }

    public void setZdtValue(ZonedDateTime zdtValue) {
        this.zdtValue = zdtValue;
    }

    public LocalDateTime getLdtValue() {
        return ldtValue;
    }

    public void setLdtValue(LocalDateTime ldtValue) {
        this.ldtValue = ldtValue;
    }

    public Object getRefValue() {
        return refValue;
    }

    public void setRefValue(Object refValue) {
        this.refValue = refValue;
    }
}
