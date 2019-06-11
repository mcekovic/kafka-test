package com.igt.ww.risk.prototype.domain.serialization;

import java.io.*;

import com.esotericsoftware.kryo.*;
import com.igt.ww.risk.prototype.domain.*;

public abstract class BetSerde implements Closeable {

	protected final Kryo kryo;

	protected BetSerde() {
		kryo = new Kryo();
		kryo.register(Bet.class);
	}

	@Override public void close() {}
}
