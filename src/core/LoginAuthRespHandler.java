package core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginAuthRespHandler extends SimpleChannelInboundHandler<NettyMessage>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NettyMessage arg1)
			throws Exception {
		// TODO Auto-generated method stub
		if(arg1.getHeader().getType()==3){
			ctx.writeAndFlush(new NettyMessage());
		}
	}

}
