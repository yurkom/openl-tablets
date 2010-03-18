package org.openl.rules.table.xls.formatters;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

public abstract class AXlsFormatter implements IFormatter {
    
    private static HashMap<Class<?>, AXlsFormatter> formatters;
    
    static {        
        formatters = new HashMap<Class<?>, AXlsFormatter>();

        formatters.put(Integer.class, XlsNumberFormatter.General);
        formatters.put(Double.class, XlsNumberFormatter.General);
        formatters.put(Boolean.class, new XlsBooleanFormatter());
        formatters.put(Date.class, new XlsDateFormatter(XlsDateFormatter.DEFAULT_XLS_DATE_FORMAT));
        formatters.put(String.class, new XlsStringFormatter());
    }
    
    /**
     * The method used for getting the appropriate formatter for the income class. If no formatter found
     * it will be returned {@link XlsStringFormatter} as default.<br>
     * Existing formatters:<ul>
     *      <li>{@link XlsNumberFormatter} for {@link Integer} and {@link Double} types.</li>
     *      <li>{@link XlsBooleanFormatter} for {@link Boolean} type.</li>
     *      <li>{@link XlsDateFormatter} for {@link Date} type.</li>
     *      <li>{@link XlsStringFormatter} for {@link String} type.</li>
     *      <li>{@link XlsEnumFormatter} for Enum types.</li>
     *      <li>{@link XlsArrayFormatter} for array types.</li>
     * </ul>
     * 
     * @param clazz formatter will be returned for this {@link Class}.
     * @param format custom format for date formatter. If <code>null</code> default format will be used 
     * {@link XlsDateFormatter#DEFAULT_XLS_DATE_FORMAT}.
     * @return formatter for a type.
     */
    public static AXlsFormatter getFormatter(Class<?> clazz, String format) {
        AXlsFormatter formatter = formatters.get(clazz);
        
        if (formatter != null) {
            // apply users format for date formatter instead of default one from initialization.
            if (formatter instanceof XlsDateFormatter && StringUtils.isNotEmpty(format)) {
                ((XlsDateFormatter)formatter).setFormat(format);
            }
        } else {            
            if (clazz.isEnum()) {
                formatter = new XlsEnumFormatter(clazz);
            } else  if (clazz.isArray()) {
                Class<?> componentType = clazz.getComponentType();
                AXlsFormatter componentFormatter = getFormatter(componentType, null);
                formatter = new XlsArrayFormatter((AXlsFormatter) componentFormatter);
            }
        }
        
        // if formatter wasn`t found use XlsStringFormat as default.
        if (formatter == null) {            
            formatter = new XlsStringFormatter();
        }
        return formatter;
    }
}
