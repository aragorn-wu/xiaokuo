package com.git.wuqf.xiaokuo.remoting;

/**
 * Created by wuqf on 17-2-24.
 */
public interface ChannelHandler {

    /**
     * on channel connected
     *
     * @param channel
     */
    void connected(Channel channel) throws RemotingException;

    /**
     * one channel disconnected
     *
     * @param channel
     */
    void disconnected(Channel channel) throws RemotingException;

    /**
     * on message sent
     *
     * @param channel
     * @param message
     */
    void sent(Channel channel, Object message) throws RemotingException;

    /**
     * on message received
     *
     * @param channel
     * @param message
     */
    void received(Channel channel, Object message) throws RemotingException;

    /**
     * on exception caught
     *
     * @param channel
     * @param exception
     */
    void caught(Channel channel, Throwable exception) throws RemotingException;
}
