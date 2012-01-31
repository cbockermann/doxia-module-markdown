/**
 * 
 */
package org.apache.maven.doxia.module.markdown;

import java.util.Map;

/**
 * @author chris
 *
 */
public interface MarkdownPreprocessor {

	public abstract String process(String source, final Map<String, String> subs);

}