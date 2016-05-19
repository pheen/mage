/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.web;

import mage.interfaces.MageClient;
import mage.interfaces.callback.ClientCallback;
import mage.remote.Connection;
import mage.remote.Session;
import mage.remote.SessionImpl;
import mage.utils.MageVersion;
import org.jruby.embed.ScriptingContainer;
import org.jruby.javasupport.JavaUtil;
import org.jruby.Ruby;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;

/**
 * @author pheen
 */

public class Client implements MageClient {

    private static final MageVersion version = new MageVersion(MageVersion.MAGE_VERSION_MAJOR, MageVersion.MAGE_VERSION_MINOR, MageVersion.MAGE_VERSION_PATCH,  MageVersion.MAGE_VERSION_MINOR_PATCH, MageVersion.MAGE_VERSION_INFO);

    private final ScriptingContainer container;
    private final IRubyObject receiver;
    private final String username;
    private Session session;

    public Client(String username, IRubyObject rubyClient) {
        this.receiver = rubyClient;
        this.username = username;
        this.container = new ScriptingContainer();
    }

    public Session getSession() {
        return session;
    }

    public void connect() {
        session = new SessionImpl(this);
        Connection connection = new Connection();

        connection.setUsername(username);
        connection.setHost("localhost");
        connection.setPort(17171);
        connection.setProxyType(Connection.ProxyType.NONE);

        session.connect(connection);
    }

    @Override
    public MageVersion getVersion() {
        return version;
    }

    @Override
    public void connected(String message) {
        IRubyObject arg = JavaUtil.convertJavaToRuby(runtime(), message);
        receiver.callMethod(context(), "connected", arg);
    }

    @Override
    public void disconnected(boolean errorCall) {
        IRubyObject arg = JavaUtil.convertJavaToRuby(runtime(), errorCall);
        receiver.callMethod(context(), "disconnected", arg);
    }

    @Override
    public void showMessage(String message) {
        IRubyObject arg = JavaUtil.convertJavaToRuby(runtime(), message);
        receiver.callMethod(context(), "showMessage", arg);
    }

    @Override
    public void showError(String message) {
        IRubyObject arg = JavaUtil.convertJavaToRuby(runtime(), message);
        receiver.callMethod(context(), "showError", arg);
    }

    @Override
    public void processCallback(ClientCallback callback) {
        IRubyObject arg = JavaUtil.convertJavaToRuby(runtime(), callback);
        receiver.callMethod(context(), "processCallback", arg);
    }

    public ThreadContext context() {
        return runtime().getThreadService().getCurrentContext();
    }

    public Ruby runtime() {
        return container.getProvider().getRuntime();
    }
}
