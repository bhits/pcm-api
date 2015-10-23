package gov.samhsa.pcm.commonunit.matcher;

import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.mockito.ArgumentMatcher;

public class ArgumentMatchers {
	public static <T> ArgumentMatcher<T> matching(Predicate<T> matches) {
		return new ArgumentMatcher<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public boolean matches(Object argument) {
				final boolean match = matches.test((T) argument);
				final StringBuilder builder = new StringBuilder();
				builder.append("Match:");
				builder.append(match);
				builder.append("; Argument:");
				builder.append(ReflectionToStringBuilder.toString(argument));
				if (match) {
					System.out.println(builder.toString());
				} else {
					System.err.println(builder.toString());
				}
				return match;
			}
		};
	}
}
