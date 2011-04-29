package com.looah.api.support;

import java.io.IOException;
import java.io.Writer;

import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.looah.api.converters.LooahAPIConverter;
import com.looah.api.converters.LooahMapConvert;
import com.thoughtworks.xstream.XStream;

/**
 * Created by IntelliJ IDEA.
 * User: ImYoon
 * Date: 2010. 10. 3
 * Time: 오전 11:21:12
 * com.looah-api - com.looah.support
 */
public class ExtendXStreamMarshaller extends XStreamMarshaller {

    ExtendXStreamMarshaller() {
        this.getXStream().registerConverter(new LooahAPIConverter(this.getXStream().getMapper(),
                this.getXStream().getReflectionProvider()), XStream.PRIORITY_VERY_LOW);
        this.getXStream().registerConverter(new LooahMapConvert(this.getXStream().getMapper()));
    }

    @Override
    protected void marshalWriter(Object graph, Writer writer) throws XmlMappingException, IOException {
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        super.marshalWriter(graph, writer);
    }
}
