/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *   
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *   
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.pcm.common;

import java.util.function.Predicate;
import java.util.function.Supplier;

import org.springframework.util.StringUtils;

/**
 * The Class UniqueValueGenerator.
 */
public class UniqueValueGenerator {

	/**
	 * Generate unique value.
	 *
	 * @param <S>
	 *            the generic type
	 * @param uniqueValueSupplier
	 *            the unique value supplier (the supplier function that
	 *            generates a random unique value)
	 * @param valueTest
	 *            the value test function (tests if this value is unique or not;
	 *            true if unique.)
	 * @param limit
	 *            the limit (the maximum number of trials before forced
	 *            termination)
	 * @return the s unique value or null if cannot be generated with given
	 *         limited iterations
	 */
	public static <S> S generateUniqueValue(Supplier<S> uniqueValueSupplier,
			Predicate<S> valueTest, short limit) {
		S uniqueValue = null;
		short iterationCounter = 0;
		do {
			final S tempValue = uniqueValueSupplier.get();
			uniqueValue = valueTest.test(tempValue) ? tempValue : null;
			iterationCounter++;
		} while (uniqueValue == null && iterationCounter < limit);
		if (uniqueValue == null
				|| (uniqueValue instanceof String && !StringUtils
						.hasText((String) uniqueValue))) {
			StringBuilder errorBuilder = new StringBuilder();
			errorBuilder.append("Unique value cannot be generated in ");
			errorBuilder.append(iterationCounter);
			errorBuilder.append(" trials.");
			throw new UniqueValueGeneratorException(errorBuilder.toString());
		}
		return uniqueValue;
	}
}
