/**
 * 
 */
package org.apache.maven.doxia.module.markdown;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author chris
 * 
 */
public class MarkdownUmlautTest extends TestCase {

	public void testUmlautEscaper() {
		MarkdownCompiler c = new MarkdownCompiler();
		String input = "Äußerlich";
		String str = c.compileHtml(input);
		Assert.assertTrue("<p>&Auml;u&szlig;erlich</p>".equals(str.trim()));
	}
}
