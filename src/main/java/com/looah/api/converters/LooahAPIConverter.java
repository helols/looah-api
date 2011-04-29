package com.looah.api.converters;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * Created by IntelliJ IDEA.
 * User: ImYoon
 * Date: 2010. 10. 3
 * Time: 4:21:40 AM
 * com.looah-api - com.looah.converters
 */
public class LooahAPIConverter extends AbstractReflectionConverter {


    public LooahAPIConverter(Mapper mapper, ReflectionProvider reflectionProvider) {
        super(mapper, reflectionProvider);
    }

    @Override
    public void marshal(Object original, HierarchicalStreamWriter writer, MarshallingContext context) {
        final Object source = serializationMethodInvoker.callWriteReplace(original);
        if (source.getClass() != original.getClass()) {
            context.convertAnother(source);
        } else {
            doMarshal(source, writer, context);
        }

    }

    protected void doMarshal(final Object source, final HierarchicalStreamWriter writer, final MarshallingContext context) {
        final Set<String> seenFields = new HashSet<String>();

        // Attributes might be preferred to child elements ...
        reflectionProvider.visitSerializableFields(source, new ReflectionProvider.Visitor() {
            public void visit(String fieldName, Class type, Class definedIn, Object value) {
                if (!mapper.shouldSerializeMember(definedIn, fieldName)) {
                    return;
                }
                SingleValueConverter converter = mapper.getConverterFromItemType(fieldName, type, definedIn);
                if (converter != null) {
                    if (value != null) {
                        if (seenFields.contains(fieldName)) {
                            throw new ConversionException("Cannot write field with name '" + fieldName
                                    + "' twice as attribute for object of type " + source.getClass().getName());
                        }
                        final String str = converter.toString(value);
                        if (str != null) {
                            writer.addAttribute(mapper.aliasForAttribute(mapper.serializedMember(definedIn, fieldName)), str);
                        }
                    }
                    seenFields.add(fieldName);
                }
            }
        });

        // Child elements not covered already processed as attributes ...
        reflectionProvider.visitSerializableFields(source, new ReflectionProvider.Visitor() {
            public void visit(String fieldName, Class fieldType, Class definedIn, Object newObj) {
                if (!mapper.shouldSerializeMember(definedIn, fieldName)) {
                    return;
                }
                if (!seenFields.contains(fieldName) && newObj != null) {
                    Mapper.ImplicitCollectionMapping mapping = mapper.getImplicitCollectionDefForFieldName(source.getClass(), fieldName);
                    if (mapping != null) {
                        if (mapping.getItemFieldName() != null) {
                            Collection list = (Collection) newObj;
                            for (Iterator iter = list.iterator(); iter.hasNext();) {
                                Object obj = iter.next();
                                writeField(fieldName, obj == null ? mapper.serializedClass(null) : mapping.getItemFieldName(), mapping.getItemType(), definedIn, obj);
                            }
                        } else {
                            context.convertAnother(newObj);
                        }
                    } else {
                        writeField(fieldName, null, fieldType, definedIn, newObj);
                    }
                } else if (newObj == null) {
                    writeField(fieldName, null, fieldType, definedIn, "");
                }
            }

            private void writeField(String fieldName, String aliasName, Class fieldType, Class definedIn, Object newObj) {
                ExtendedHierarchicalStreamWriterHelper.startNode(writer, aliasName != null ? aliasName : mapper.serializedMember(source.getClass(), fieldName), fieldType);

                if (newObj != null) {
                    Field field = reflectionProvider.getField(definedIn, fieldName);
                    marshallField(context, newObj, field);
                }
                writer.endNode();
            }
        });
    }

    public boolean canConvert(Class type) {
        return true;
    }
}
