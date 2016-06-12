package core;

import java.util.List;
import java.util.Map.Entry;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class NettyMessageEncoder extends MarshallingEncoder{
	private MarshallingEncoder encoder;
	
	public NettyMessageEncoder() throws Exception{
		this.encoder=new MarshallingEncoder();
	}
	protected void encode(ChannelHandlerContext ctx, NettyMessage msg,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		if(msg==null||msg.getHeader()==null){
			throw new Exception("the msg is null");
		}
		
		ByteBuf buffer=Unpooled.buffer();
		
		buffer.writeInt(msg.getHeader().getCrcCode());
		buffer.writeInt(msg.getHeader().getLength());
		buffer.writeLong(msg.getHeader().getSessionID());
		buffer.writeByte(msg.getHeader().getType());
		buffer.writeByte(msg.getHeader().getPriority());
		buffer.writeInt(msg.getHeader().getAttachment().size());
		for(Entry<String,Object> entry:msg.getHeader().getAttachment().entrySet()){
			String key=entry.getKey();
			Object obj=entry.getValue();
			buffer.writeInt(key.length());
			buffer.writeBytes(key.getBytes());
			encoder.encode(obj, buffer);
		}
		
		if(msg.getBody()!=null){
			encoder.encode(msg.getBody(), buffer);
		}else{
			buffer.writeInt(0);
		}
		
		buffer.setInt(4, buffer.readableBytes());
	}
}
