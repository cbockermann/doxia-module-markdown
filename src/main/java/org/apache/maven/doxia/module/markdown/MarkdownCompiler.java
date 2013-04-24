/**
 * 
 */
package org.apache.maven.doxia.module.markdown;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.petebevin.markdown.MarkdownProcessor;

/**
 * @author chris
 * 
 */
public class MarkdownCompiler {

	public final static String PDFLATEX = "/sw/bin/pdflatex";
	public final static String CONVERT = "/usr/local/bin/convert";

	public final static String LATEX_BEGIN = "\\documentclass[a4,12pt]{article}\n"
			+ "\\begin{document}\n"
			+ "\\pagestyle{empty}\n"
			+ "\\begin{displaymath}\n" + "";

	public final static String LATEX_END = "\n" + "\\end{displaymath}\n"
			+ "\\end{document}";

	final List<MarkdownPreprocessor> preProcessors = new ArrayList<MarkdownPreprocessor>();

	public MarkdownCompiler() {
		preProcessors.add(new MarkdownUmlautEscaper());
		preProcessors.add(new MarkdownTablePreprocessor());
	}

	public void addPreProcessor(final MarkdownPreprocessor proc) {
		preProcessors.add(proc);
	}

	public static String read(InputStream in) {
		try {
			StringBuffer s = new StringBuffer();
			BufferedReader r = new BufferedReader(new InputStreamReader(in));
			String line = r.readLine();
			while (line != null) {
				s.append(line + "\n");
				line = r.readLine();
			}
			return s.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String compile(URL url) {
		try {
			return compile(url.openStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String compile(InputStream in) {
		return compile(read(in));
	}

	public String compileHtml(String text) {
		// System.err.println("Compiling:\n" + text);
		String src = text;
		MarkdownProcessor p = new MarkdownProcessor();

		Map<String, String> subs = new LinkedHashMap<String, String>();
		for (MarkdownPreprocessor pre : preProcessors) {
			// System.out.println("Running MarkdownPreprocessor " + pre);
			src = pre.process(src, subs);
		}

		// System.out.println("My compiled substitutions: " + subs);
		String html = p.markdown(src);

		List<String> refs = new ArrayList<String>(subs.keySet());
		Collections.reverse(refs);
		for (String key : refs) {
			html = html.replace(key, subs.get(key));
		}
		return html;
	}

	public static String compile(String text) {
		MarkdownCompiler compiler = new MarkdownCompiler();
		return compiler.compileHtml(text);

	}
}