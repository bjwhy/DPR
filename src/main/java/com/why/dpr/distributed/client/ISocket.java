package com.why.dpr.distributed.client;

import com.why.dpr.distributed.message.Message;

public interface ISocket {
	public void sendMsg(Message mes);

	public void tcpConnect(String serverAdress, int serverPort);
}
