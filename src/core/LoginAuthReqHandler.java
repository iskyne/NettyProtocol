package core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginAuthReqHandler extends SimpleChannelInboundHandler<NettyMessage>{

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		
		ctx.writeAndFlush(buildReq(3));
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelReadComplete(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.flush();
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg)
			throws Exception {
		if(msg.getHeader().getType()==3){
			ctx.writeAndFlush(buildReq(4));
		}else{
			System.out.println(msg);
		}
		
	}
	
	private NettyMessage buildReq(int type){
		Header header=new Header();
		header.setType((byte)type);
		header.setPriority((byte)1);
		
		NettyMessage message=new NettyMessage();
		message.setHeader(header);
		return message;
	}

}
