/**
  * ©2011 Looah, LLC
  * looah-api
  * created by isyoon Jan 8, 2011 5:50:18 PM
  */
package com.looah.api.converters;

import java.util.Iterator;
import java.util.Map;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class LooahMapConvert extends MapConverter {
    
    public LooahMapConvert(Mapper mapper) {
        super(mapper);
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        Map map = (Map) source;
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            ExtendedHierarchicalStreamWriterHelper.startNode(writer, entry.getKey().toString(), String.class);
            if(entry.getValue() != null){
                context.convertAnother(entry.getValue());
            }
            writer.endNode();
        }
    }
}