package io.github.hapjava.server.impl.http.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpResponseEncoder;
import java.util.Iterator;
import java.util.List;

public class HttpResponseEncoderAggregate extends HttpResponseEncoder {

  @Override
  protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
    super.encode(ctx, msg, out);
    if (out.size() > 0) {
      Iterator<Object> i = out.iterator();
      ByteBuf b = (ByteBuf) i.next();
      while (i.hasNext()) {
        b.writeBytes((ByteBuf) i.next());
        i.remove();
      }
    }
  }
}
