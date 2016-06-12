package core
;

import java.io.IOException;

import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

public class MarshallingCodecFactory {
	public static Marshaller getMarshalling() throws Exception{
		MarshallerFactory factory=Marshalling.getProvidedMarshallerFactory("serial");
		MarshallingConfiguration configuration=new MarshallingConfiguration();
		configuration.setVersion(5);
		Marshaller marshaller=factory.createMarshaller(configuration);
		return marshaller;
	}
	
	public static Unmarshaller getUnmarshalling() throws Exception{
		MarshallerFactory factory=Marshalling.getProvidedMarshallerFactory("serial");
		MarshallingConfiguration configuration=new MarshallingConfiguration();
		configuration.setVersion(5);
		Unmarshaller unmarshaller=factory.createUnmarshaller(configuration);
		return unmarshaller;
	}
}
