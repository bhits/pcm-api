/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.consent;

import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceListener;
import org.w3c.dom.Node;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a custom listener class used to ignore differences between expected
 * and generated xmls based on regular expression.
 */
public class RegexBasedDifferenceListener implements DifferenceListener {
	/**
	 * list of regex expressions used to ignore differences found between
	 * expected and generated xmls.
	 */
	private final List<Pattern> ignorableRegexPatters;

	public RegexBasedDifferenceListener(final List<String> ignorableXPathsRegex) {
		this.ignorableRegexPatters = compileXpathExpressions(ignorableXPathsRegex);
	}

	/**
	 * compile all regular expressions once.
	 * 
	 * @param ignorableXPathsRegex
	 *            list of regular expressions for ignorable xpath locations.
	 * @return list of compiled regular expressions.
	 */
	private List<Pattern> compileXpathExpressions(
			final List<String> ignorableXPathsRegex) {
		final List<Pattern> compiledRegexList = new ArrayList<Pattern>();
		Iterator<String> it = ignorableXPathsRegex.iterator();
		while (it.hasNext()) {
			final Pattern pattern = Pattern.compile(it.next().toString());
			compiledRegexList.add(pattern);
		}
		return compiledRegexList;
	}

	/**
	 * On each difference this method is called by XMLUnit framework to
	 * determine whether we accept the difference or ignore it. If any of the
	 * provided regular expression match with xml xpath location at which
	 * difference found then ignore the difference.
	 * 
	 * @param difference
	 *            contains information about differences.
	 */
	public int differenceFound(Difference difference) {
		Iterator<Pattern> it = this.ignorableRegexPatters.iterator();
		final String xpathLocation = difference.getTestNodeDetail()
				.getXpathLocation();
		while (it.hasNext()) {
			final Pattern pattern = it.next();
			final Matcher m = pattern.matcher(xpathLocation);
			if (m.find()) {
				return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
				/** ignore it, not a valid difference */
			}
		}
		return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
		/** no objection, mark it as a valid difference */
	}

	/**
	 * This method is here just b/c it exist in DifferenceListener interface.
	 * So, needs dummy implementation. We actually do not need to implement it
	 * for current scenario.
	 */
	public void skippedComparison(Node node, Node node1) {
	}
}