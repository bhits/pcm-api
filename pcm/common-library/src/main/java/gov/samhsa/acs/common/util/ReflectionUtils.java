package gov.samhsa.acs.common.util;

import static java.util.stream.Collectors.toSet;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.LongAdder;

public class ReflectionUtils {
	public static final <C, T> Set<T> getFieldValuesOfTypeAsUnmodifiableSet(
			Class<C> clazz, Class<T> fieldType) {
		final LongAdder counter = new LongAdder();
		final Set<T> set = Collections.unmodifiableSet(Arrays
				.stream(clazz.getDeclaredFields())
				.filter(field -> field.getType() == fieldType)
				.map(field -> getFieldValueOfType(field, fieldType))
				.peek(field -> counter.increment()).collect(toSet()));
		if (set.size() != counter.intValue()) {
			throw new IllegalStateException("Duplicate values in " + clazz
					+ " fields of type " + fieldType);
		}
		return set;
	}

	@SuppressWarnings("unchecked")
	private static final <T> T getFieldValueOfType(Field field, Class<T> fieldType) {
		try {
			return (T) field.get(fieldType);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
