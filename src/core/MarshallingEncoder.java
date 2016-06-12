package core;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

import org.jboss.marshalling.Marshaller;

public class MarshallingEncoder {
	
	private Marshaller marshaller;
	private static final byte[] PLACE_LENGTH=new byte[4];
	
	public MarshallingEncoder() throws Exception{
		marshaller=MarshallingCodecFactory.getMarshalling();
	}
	
	public void encode(Object obj,ByteBuf buffer) throws Exception{
		try{
			int lengthPos=buffer.writerIndex();
			buffer.writeBytes(PLACE_LENGTH);
			ChannelBufferByteOutput output=new ChannelBufferByteOutput(buffer);
			marshaller.start(output);
			marshaller.writeObject(obj);
			marshaller.finish();
			buffer.setInt(lengthPos, buffer.writerIndex()-lengthPos-4);
		}finally{
			marshaller.close();
		}
		
	}

}
