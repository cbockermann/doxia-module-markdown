/**
 * 
 */
package org.apache.maven.doxia.module.markdown;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chris
 * 
 */
public class MarkdownUmlautEscaper implements MarkdownPreprocessor {

	Map<Character, String> table = new HashMap<Character, String>();

	public MarkdownUmlautEscaper() {
		table.put(Character.valueOf('Ä'), "&Auml;");
		table.put(Character.valueOf('ä'), "&auml;");
		table.put(Character.valueOf('Ö'), "&Ouml;");
		table.put(Character.valueOf('ö'), "&ouml;");
		table.put(Character.valueOf('Ü'), "&Uuml;");
		table.put(Character.valueOf('ü'), "&uuml;");
		table.put(Character.valueOf('ß'), "&szlig;");
		System.out.println("table contains " + table.size()
				+ " umlaut mappings.");
		table.put('\u00DF', "&szlig;");
		System.out.println("table contains " + table.size()
				+ " umlaut mappings.");
	}

	/**
	 * @see org.apache.maven.doxia.module.markdown.MarkdownPreprocessor#process(java.lang.String,
	 *      java.util.Map)
	 */
	public String process(String source, Map<String, String> subs) {
		String str = source;
		for (Character c : table.keySet()) {
			while (str.indexOf(c) >= 0) {
				String ch = c.toString();
				String repl = table.get(c);
				System.out
						.println("Replacing '" + ch + "' with '" + repl + "'");
				str = str.replace(c.toString(), table.get(c));
			}
		}
		return str;
	}
}
