package org.openl.meta;

import java.math.BigInteger;

import org.openl.exception.OpenlNotCheckedException;
import org.openl.meta.explanation.ExplanationNumberValue;
import org.openl.meta.number.NumberOperations;

public class BigIntegerValue extends ExplanationNumberValue<BigIntegerValue> {
    
    private static final long serialVersionUID = -3936317402079096501L;
    private BigInteger value;    
    
    public static BigIntegerValue add(BigIntegerValue bigIntValue1, BigIntegerValue bigIntValue2) {

        if (bigIntValue1 == null) {
            return bigIntValue2;
        }

        if (bigIntValue2 == null) {
            return bigIntValue1;
        }

        return new BigIntegerValue(bigIntValue1, bigIntValue2, 
            bigIntValue1.getValue().add(bigIntValue2.getValue()), NumberOperations.ADD.toString(), false);
    }    
    
    // ******* Autocasts 8*************
    
    public static BigIntegerValue autocast(byte x, BigIntegerValue y) {
        return new BigIntegerValue(String.valueOf(x));
    }

    public static BigIntegerValue autocast(short x, BigIntegerValue y) {
        return new BigIntegerValue(String.valueOf(x));
    }
    
    public static BigIntegerValue autocast(char x, BigIntegerValue y) {
        return new BigIntegerValue(String.valueOf((int)x));    
    }

    public static BigIntegerValue autocast(int x, BigIntegerValue y) {
        return new BigIntegerValue(String.valueOf(x));
    }

    public static BigIntegerValue autocast(long x, BigIntegerValue y) {
        return new BigIntegerValue(String.valueOf(x));
    } 

    public static BigIntegerValue autocast(BigInteger x, BigIntegerValue y) {
        if (x == null) {
            return null;
        }

        return new BigIntegerValue(x);
    }
    
    public static BigIntegerValue autocast(BigIntegerValue x, BigDecimalValue y) {
        if (x == null) {
            return null;
        }
        return new BigIntegerValue(String.valueOf(x.getValue()));
    }
    
    // ******* Casts 8*************

    public static byte cast(BigIntegerValue x, byte y) {
        return x.byteValue();
    }

    public static short cast(BigIntegerValue x, short y) {
        return x.shortValue();
    }
    
    public static char cast(BigIntegerValue x, char y) {
        return (char) x.intValue();
    }

    public static int cast(BigIntegerValue x, int y) {
        return x.intValue();
    }

    public static long cast(BigIntegerValue x, long y) {
        return x.longValue();
    }

    public static float cast(BigIntegerValue x, float y) {
        return x.floatValue();
    }
    
    public static double cast(BigIntegerValue x, double y) {
        return x.doubleValue();
    }

    public static BigInteger cast(BigIntegerValue x, BigInteger y) {
        if (x == null) {
            return null;
        }

        return x.getValue();
    }
    
    public static ByteValue cast(BigIntegerValue x, ByteValue y) {
        if (x == null) {
            return null;
        }
        return new ByteValue(x.byteValue());
    }

    public static ShortValue cast(BigIntegerValue x, ShortValue y) {
        if (x == null) {
            return null;
        }
        return new ShortValue(x.shortValue());
    }
        
    public static IntValue cast(BigIntegerValue x, IntValue y) {
        if (x == null) {
            return null;
        }
        return new IntValue(x.intValue());
    }

    public static LongValue cast(BigIntegerValue x, LongValue y) {
        if (x == null) {
            return null;
        }
        return new LongValue(x.longValue());
    }
    
    public static FloatValue cast(BigIntegerValue x, FloatValue y) {
        if (x == null) {
            return null;
        }
        return new FloatValue(x.floatValue());
    }
    
    public static DoubleValue cast(BigIntegerValue x, DoubleValue y) {
        if (x == null) {
            return null;
        }
        return new DoubleValue(x.doubleValue());
    }

    public static BigIntegerValue copy(BigIntegerValue value, String name) {
        if (value.getName() == null) {
            value.setName(name);

            return value;
        } else if (!value.getName().equals(name)) {
            BigIntegerValue lv = new BigIntegerValue(value, NumberOperations.COPY.toString(), 
                new BigIntegerValue[] { value });
            lv.setName(name);

            return lv;
        }

        return value;
    }

    public static BigIntegerValue divide(BigIntegerValue bigIntValue1, BigIntegerValue bigIntValue2) {
        if (bigIntValue1 == null && bigIntValue2 == null) {
            return null;
        } 
        return new BigIntegerValue(bigIntValue1, bigIntValue2, bigIntValue1.getValue().divide(bigIntValue2.getValue()),
            NumberOperations.DIVIDE.toString(), true);
    }

    public static boolean eq(BigIntegerValue bigIntValue1, BigIntegerValue bigIntValue2) {
        if (bigIntValue1 == null || bigIntValue2 == null) {
            return false;
        }
        return bigIntValue1.equals(bigIntValue2);
    }

    public static boolean ge(BigIntegerValue bigIntValue1, BigIntegerValue bigIntValue2) {
        if (bigIntValue1 == null || bigIntValue2 == null) {
            return false;
        }
        return bigIntValue1.compareTo(bigIntValue2) >= 0;
    }

    public static boolean gt(BigIntegerValue bigIntValue1, BigIntegerValue bigIntValue2) {
        if (bigIntValue1 == null || bigIntValue2 == null) {
            return false;
        }
        return bigIntValue1.compareTo(bigIntValue2) > 0;
    }

    public static boolean le(BigIntegerValue bigIntValue1, BigIntegerValue bigIntValue2) {
        if (bigIntValue1 == null || bigIntValue2 == null) {
            return false;
        }
        return bigIntValue1.compareTo(bigIntValue2) <= 0;
    }

    public static boolean lt(BigIntegerValue bigIntValue1, BigIntegerValue bigIntValue2) {
        if (bigIntValue1 == null || bigIntValue2 == null) {
            return false;
        }
        return bigIntValue1.compareTo(bigIntValue2) < 0;
    }

    public static BigIntegerValue max(BigIntegerValue bigIntValue1, BigIntegerValue bigIntValue2) {        
        BigIntegerValue maxValue = null;
        if (bigIntValue1 == null && bigIntValue2 == null) {
            return null;
        }        
        if (bigIntValue1 != null) {
            maxValue = bigIntValue1.compareTo(bigIntValue2) > 0 ? bigIntValue1 : bigIntValue2;            
        } else if (bigIntValue2 != null) {
            maxValue = bigIntValue2.compareTo(bigIntValue1) > 0 ? bigIntValue2 : bigIntValue1;
        } 
        return new BigIntegerValue(maxValue, NumberOperations.MAX.toString(), 
            new BigIntegerValue[] { bigIntValue1, bigIntValue2 });
    }
    

    public static BigIntegerValue min(BigIntegerValue bigIntValue1, BigIntegerValue bigIntValue2) {
        BigIntegerValue minValue = null;
        if (bigIntValue1 == null && bigIntValue2 == null) {
            return null;
        } 
        if (bigIntValue1 != null) {
            minValue = bigIntValue1.compareTo(bigIntValue2) < 0 ? bigIntValue1 : bigIntValue2;            
        } else if (bigIntValue2 != null) {
            minValue = bigIntValue2.compareTo(bigIntValue1) < 0 ? bigIntValue2 : bigIntValue1;
        }
        if (minValue != null) {
            return new BigIntegerValue(minValue, NumberOperations.MIN.toString(), 
                new BigIntegerValue[] { bigIntValue1, bigIntValue2 });
        } else {
            return null;
        }        
    }

    public static BigIntegerValue multiply(BigIntegerValue bigIntValue1, BigIntegerValue bigIntValue2) {
        if (bigIntValue1 == null && bigIntValue2 == null) {
            return null;
        }
        return new BigIntegerValue(bigIntValue1, bigIntValue2, 
            bigIntValue1.getValue().multiply(bigIntValue2.getValue()), NumberOperations.MULTIPLY.toString(), 
            true);
    }    

    public static boolean ne(BigIntegerValue bigIntValue1, BigIntegerValue bigIntValue2) {        
        return bigIntValue1.getValue() != bigIntValue2.getValue();
    }

    public static BigIntegerValue negative(BigIntegerValue bigIntValue) {
        if (bigIntValue == null) {
            return null;
        }
        BigIntegerValue neg = new BigIntegerValue(bigIntValue.getValue().negate());
        neg.setMetaInfo(bigIntValue.getMetaInfo());

        return neg;
    }

    public static BigIntegerValue pow(BigIntegerValue bigIntValue1, BigIntegerValue bigIntValue2) {   
        if (bigIntValue1 == null && bigIntValue2 == null) {
            return null;
        }
        return new BigIntegerValue(new BigIntegerValue(bigIntValue1.getValue().pow(bigIntValue2.getValue().intValue())),
            NumberOperations.POW.toString(), new BigIntegerValue[] { bigIntValue1, bigIntValue2 });
    }

    public static BigIntegerValue subtract(BigIntegerValue bigIntValue1, BigIntegerValue bigIntValue2) {

        if (bigIntValue2 == null) {
            return bigIntValue1;
        }

        return new BigIntegerValue(bigIntValue1, bigIntValue2, 
            bigIntValue1.getValue().subtract(bigIntValue2.getValue()), NumberOperations.SUBTRACT.toString(), 
            false);
    }
    
    public BigIntegerValue(BigInteger value) {
        this.value = value;
    }
    
    public BigIntegerValue(String valueString) {        
        value = new BigInteger(valueString);
    }
    
    public BigIntegerValue(BigInteger value, String name) {
        super(name);
        this.value = value;
    }

    public BigIntegerValue(BigInteger value, IMetaInfo metaInfo) {
        super(metaInfo);
        this.value = value;        
    } 
    
    public BigIntegerValue(String value, String name) {
        super(name);
        this.value = new BigInteger(value);
    }

    public BigIntegerValue(String value, IMetaInfo metaInfo) {
        super(metaInfo);
        this.value = new BigInteger(value);      
    }
    
    /**Formula constructor**/
    public BigIntegerValue(BigIntegerValue bigIntValue1, BigIntegerValue bigIntValue2, BigInteger value, 
            String operand, boolean isMultiplicative) {
        super(bigIntValue1, bigIntValue2, operand, isMultiplicative);
        this.value = value;
    }
    
    /**Function constructor**/
    public BigIntegerValue(BigIntegerValue result, String functionName, BigIntegerValue[] params) {
        super(result, functionName, params);
        this.value = result.getValue();
    }

    @Override
    public BigIntegerValue copy(String name) {        
        return copy(this, name);
    }

    @Override
    public double doubleValue() {        
        return value.doubleValue();
    }

    @Override
    public float floatValue() {        
        return value.floatValue();
    }

    @Override
    public int intValue() {        
        return value.intValue();
    }

    @Override
    public long longValue() {
        return value.longValue();
    }

    public String printValue() {        
        return value.toString();
    }

    public int compareTo(Number o) {        
        if (o == null) {
            return 1;
        } else if (o instanceof BigIntegerValue) {            
            return value.compareTo(((BigIntegerValue)o).getValue());
        } else {
            throw new OpenlNotCheckedException("Can`t compare BigIntegerValue with unknown type.");
        }            
    }
    
    public BigInteger getValue() {        
        return value;
    }
    
    public void setValue(BigInteger value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BigIntegerValue) {
            BigIntegerValue secondObj = (BigIntegerValue) obj;
            return value.equals(secondObj.getValue());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return  value.hashCode();
    }

}
