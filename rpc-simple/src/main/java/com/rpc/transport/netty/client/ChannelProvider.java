package com.rpc.transport.netty.client;

import com.rpc.enumeration.RpcErrorMessageEnum;
import com.rpc.exception.RpcException;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ChannelProvider {
    private static final Logger logger = LoggerFactory.getLogger(ChannelProvider.class);

    private static Bootstrap bootstrap = NettyClient.initializeBootstrap();
    private static Channel channel = null;
    private static final int MAX_RETRY_COUNT = 5;

    public static Channel get(InetSocketAddress inetSocketAddress) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            connect(bootstrap, inetSocketAddress, countDownLatch);
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error("occur exception when get channel: ", e);
        }
        return channel;
    }

    private static void connect(Bootstrap bootstrap, InetSocketAddress inetSocketAddress, CountDownLatch countDownLatch) {
        connect(bootstrap, inetSocketAddress, MAX_RETRY_COUNT, countDownLatch);
    }

    private static void connect(Bootstrap bootstrap, InetSocketAddress inetSocketAddress, int maxRetryCount, CountDownLatch countDownLatch) {
        bootstrap.connect(inetSocketAddress).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                logger.info("client connect success");
                channel = future.channel();
                countDownLatch.countDown();
                return;
            }
            if (maxRetryCount == 0) {
                logger.error("client connect failed: retry count run out, give up connection");
                countDownLatch.countDown();
                throw new RpcException(RpcErrorMessageEnum.CLIENT_CONNECT_SERVER_FAILURE);
            }
            int order = (MAX_RETRY_COUNT - maxRetryCount) + 1;
            int delay = 1 << order;
            logger.error("{}: connect failed, {} reconnecting...", new Date(), order);
            bootstrap.config().group().schedule(() -> {
                connect(bootstrap, inetSocketAddress, maxRetryCount - 1, countDownLatch);
            }, delay, TimeUnit.SECONDS);
        });
    }
}
