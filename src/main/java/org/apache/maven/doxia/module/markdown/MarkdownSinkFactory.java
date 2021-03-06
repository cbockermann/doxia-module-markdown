package org.apache.maven.doxia.module.markdown;

import java.io.Writer;

import org.apache.maven.doxia.sink.AbstractTextSinkFactory;
import org.apache.maven.doxia.sink.Sink;

/**
 * @plexus.component role="org.apache.maven.doxia.sink.SinkFactory"
 *                   role-hint="markdown"
 */
public class MarkdownSinkFactory extends AbstractTextSinkFactory {

	protected Sink createSink( Writer writer, String encoding ) {
		return new MarkdownSink();
	}
}