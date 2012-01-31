/**
 * 
 */
package org.apache.maven.doxia.module.markdown;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author chris
 *
 */
public class MarkdownTablePreprocessor implements MarkdownPreprocessor {

	/**
	 * @see net.scinotes.web.layout.MarkdownPreprocessor#process(java.lang.String, java.util.Map)
	 */
	public String process(String source, Map<String, String> subs) {
		String src = source;
		StringWriter output = new StringWriter(); 
		try {
			BufferedReader reader = new BufferedReader( new StringReader( source ) );
			String line = reader.readLine();
			String last = null;
			while( line != null ){

				if( ( last == null || "".equals( last.trim() ) ) && isTableLine( line ) ){
					StringBuffer tableSource = new StringBuffer();
					while( line != null && isTableLine( line ) ){
						tableSource.append( line + "\n" );
						line = reader.readLine();
					}
					
					String html = compileTable( tableSource.toString() );
					String ref = MD5.md5( tableSource.toString() );
					subs.put( ref, html );
					
					src = src.replace( tableSource.toString(), ref );
					output.append( ref + "\n" );
				} else {
					output.append( line + "\n" );
				}
				
				last = line;
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println( "Returning source as:\n" + output.toString() );
		return output.toString();
	}
	
	
	public String compileTable( String src ){
		StringWriter w = new StringWriter();
		w.append( "<div class=\"markdownTable\">\n" );
		w.append( "<table class=\"markdownTable\">\n" );
		
		String[] lines = src.split( "\n" );
		for( int i = 0; i < lines.length; i++ ){
			w.append( "<tr>\n" );
			
			String[] cols = lines[i].split( "\\|" );
			for( int c = 1; c < cols.length; c++ ){
				String col = cols[c];
				if( i == 0 ){
					w.append( "<th>" + col.trim() + "</th>\n" );
				} else {
					w.append( "<td>" + col.trim() + "</td>\n" );
				}
			}
			
			w.append( "</tr>\n" );
		}
		
		w.append( "</table>\n" );
		w.append( "</div>\n" );
		
		return w.toString();
	}

	public boolean isTableLine( String line ){
		return line != null && line.startsWith( "|" );
	}
}