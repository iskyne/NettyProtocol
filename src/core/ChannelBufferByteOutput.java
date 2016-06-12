package core;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import org.jboss.marshalling.ByteOutput;

public class ChannelBufferByteOutput implements ByteOutput{
	
	private ByteBuf buffer;
	
	public ChannelBufferByteOutput(ByteBuf buffer){
		this.buffer=buffer;
	}
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void flush() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(int arg0) throws IOException {
		// TODO Auto-generated method stub
		buffer.writeInt(arg0);
	}

	@Override
	public void write(byte[] arg0) throws IOException {
		// TODO Auto-generated method stub
		buffer.writeBytes(arg0);
	}

	@Override
	public void write(byte[] arg0, int arg1, int arg2) throws IOException {
		// TODO Auto-generated method stub
		buffer.writeBytes(arg0, arg1, arg2);
	}
	
	public ByteBuf getBuffer(){
		return this.buffer;
	}

}
