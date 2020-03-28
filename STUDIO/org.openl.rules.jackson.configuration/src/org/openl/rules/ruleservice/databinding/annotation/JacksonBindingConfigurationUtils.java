package org.openl.rules.ruleservice.databinding.annotation;

/*-
 * #%L
 * OpenL - STUDIO - Jackson - Configuration
 * %%
 * Copyright (C) 2015 - 2020 OpenL Tablets
 * %%
 * See the file LICENSE.txt for copying permission.
 * #L%
 */

import java.lang.annotation.Annotation;

public final class JacksonBindingConfigurationUtils {

    private static final Class<?>[] CONFIGURATION_ANNOTATIONS = { MixInClassFor.class, MixInClass.class };

    private JacksonBindingConfigurationUtils() {
    }

    @SuppressWarnings("unchecked")
    public static boolean isConfiguration(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        for (Class<?> annotation : CONFIGURATION_ANNOTATIONS) {
            if (clazz.isAnnotationPresent((Class<? extends Annotation>) annotation)) {
                return true;
            }
        }
        return false;
    }
}
