package core;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import org.jboss.marshalling.ByteInput;

public class ChannelBufferByteInput implements ByteInput{

	private ByteBuf buffer;
	
	public ChannelBufferByteInput(ByteBuf buffer){
		this.buffer=buffer;
	}
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int available() throws IOException {
		// TODO Auto-generated method stub
		return buffer.readableBytes();
	}

	@Override
	public int read() throws IOException {
		// TODO Auto-generated method stub
		byte b=buffer.readByte();
		return b&0xff;
	}

	@Override
	public int read(byte[] arg0) throws IOException {
		// TODO Auto-generated method stub
		return read(arg0,0,arg0.length);
	}

	@Override
	public int read(byte[] arg0, int arg1, int arg2) throws IOException {
		// TODO Auto-generated method stub
		int available=available();
		if(available==0)
			return -1;
		int length=Math.min(available, arg2);
		buffer.readBytes(arg0, arg1, length);
		return length;
	}

	@Override
	public long skip(long arg0) throws IOException {
		// TODO Auto-generated method stub
		int readable=buffer.readableBytes();
		if(arg0>readable){
			arg0=readable;
		}
		buffer.readerIndex(buffer.readerIndex()+readable);
		return arg0;
	}
}
