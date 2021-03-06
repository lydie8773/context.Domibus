package eu.domibus.ext.delegate.converter;

import java.util.List;

/**
 * Class responsible of conversion from the internal domain to external domain and the other way around
 *
 * @author migueti, Cosmin Baciu
 * @since 3.3
 */
public interface DomainExtConverter {

    <T, U> T convert(U source, Class<T> typeOfT);

    <T, U> List<T> convert(List<U> sourceList, Class<T> typeOfT);

}
