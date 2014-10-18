/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.smarthome;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.xml.sax.SAXException;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class JaxbFassade {

    public static class LocalFileSchemaResolver extends SchemaOutputResolver {

	private File baseDir;

	public LocalFileSchemaResolver(File baseDir) {
	    this.baseDir = baseDir;
	}

	@Override
	public Result createOutput(String namespaceUri, String suggestedFileName)
		throws IOException {
	    return new StreamResult(new File(baseDir, suggestedFileName));
	}
    }

    public static void saveSchema(File baseDir, Class... classes) throws JAXBException, IOException {
	JAXBContext context = JAXBContext.newInstance(classes);
	context.generateSchema(new LocalFileSchemaResolver(baseDir));
    }

    public static Schema loadSchema(URL schemaURL) throws SAXException {
	SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	return sf.newSchema(schemaURL);
    }

    public static void saveInstance(OutputStream os, URL schemaURL, String schemaName, Object instance) throws JAXBException, IOException {
	Marshaller marshaller = JAXBContext.newInstance(instance.getClass()).createMarshaller();
	marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaURL + " ");
	marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	marshaller.marshal(instance, os);
	os.flush();
    }

    public static void saveInstanceXML(OutputStream os, Object instance) throws JAXBException, IOException {
	Marshaller marshaller = JAXBContext.newInstance(instance.getClass()).createMarshaller();
	marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	marshaller.marshal(instance, os);
	os.flush();
    }

    public static void saveInstanceJSON(OutputStream os, Object instance) throws JAXBException, IOException {
	Marshaller marshaller = JAXBContext.newInstance(instance.getClass()).createMarshaller();
	marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	marshaller.marshal(instance, os);
	marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
	marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
	marshaller.marshal(instance, os);
	os.flush();

    }

    public static Object loadInstance(InputStream inputStream, Schema schema, Class instanceClass) throws JAXBException {
	Unmarshaller unmarshaller = JAXBContext.newInstance(instanceClass).createUnmarshaller();
	unmarshaller.setSchema(schema);
	Object instance = unmarshaller.unmarshal(inputStream);
	return instance;
    }

    public static Object loadInstanceXML(InputStream inputStream, Class instanceClass) throws JAXBException {
	Unmarshaller unmarshaller = JAXBContext.newInstance(instanceClass).createUnmarshaller();
	Object instance = unmarshaller.unmarshal(inputStream);
	return instance;
    }

    public static Object loadInstanceJSON(InputStream inputStream, Class instanceClass) throws JAXBException {
	Unmarshaller unmarshaller = JAXBContext.newInstance(instanceClass).createUnmarshaller();
	unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");
	unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
	Object instance = unmarshaller.unmarshal(inputStream);
	return instance;
    }
}
