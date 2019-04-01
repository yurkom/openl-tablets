package org.openl.rules.ruleservice.logging;

/**
 * Interface for service that responsible for storing logging info into external resource.
 *
 * @author Marat Kamalov.
 *
 */
public interface StoreLoggingInfoService {
    void store(LoggingInfo loggingInfo);
}
