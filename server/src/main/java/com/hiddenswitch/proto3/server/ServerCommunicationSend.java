package com.hiddenswitch.proto3.server;

import com.hiddenswitch.proto3.net.common.RemoteUpdateListener;

public interface ServerCommunicationSend {
	public RemoteUpdateListener getPlayerListener(int player);
}
