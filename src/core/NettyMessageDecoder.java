package core;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;

import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

	private Unmarshaller unmarshaller;
	
	public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset,
			int lengthFieldLength) throws Exception {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
		unmarshaller=MarshallingCodecFactory.getUnmarshalling();
	}
	
	@Override
	protected Object decode(ChannelHandlerContext arg0, ByteBuf arg1)
			throws Exception {
		// TODO Auto-generated method stub
		ByteBuf buffer=(ByteBuf) super.decode(arg0, arg1);
		if(buffer==null){
			return null;
		}
		
		NettyMessage message=new NettyMessage();
		
		Header header=new Header();
		
		header.setCrcCode(buffer.readInt());
		header.setLength(buffer.readInt());
		header.setSessionID(buffer.readLong());
		header.setType(buffer.readByte());
		header.setPriority(buffer.readByte());
		int size=buffer.readInt();
		if(size>0){
			Map<String,Object> attach=new HashMap<String,Object>();
			for(int i=0;i<size;i++){
				int keySize=buffer.readInt();
				byte[] key=new byte[keySize];
				buffer.readBytes(key);
				String keyStr=new String(key,"utf-8");
					
				Object obj=decodeObject(buffer);
				attach.put(keyStr, obj);
			}
			header.setAttachment(attach);
		}
		message.setHeader(header);
		
		Object obj=decodeObject(buffer);
		message.setBody(obj);
		
		return message;
	}
	
	protected Object decodeObject(ByteBuf buffer) throws Exception, IOException{
		int ObjectSize=buffer.readInt();
		ByteBuf ObjBuf=buffer.slice(buffer.readerIndex(), ObjectSize);
		ByteInput input=new ChannelBufferByteInput(ObjBuf);
		try{
			unmarshaller.start(input);
			Object obj=unmarshaller.readObject();
			unmarshaller.finish();
			buffer.readerIndex(buffer.readerIndex()+ObjectSize);
			return obj;
		}finally{
			unmarshaller.close();
		}
	}
}
