/*
 * The MIT License
 *
 * Copyright 2016 toyblocks.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jp.llv.nest.command;

import java.util.Objects;
import jp.llv.nest.command.exceptions.CommandException;
import jp.llv.nest.command.obj.NestCommandSender;
import jp.llv.nest.command.obj.NestList;
import jp.llv.nest.command.obj.NestObject;
import jp.llv.nest.command.obj.NestPermitter;
import jp.llv.nest.command.obj.bind.Binding;

/**
 *
 * @author toyblocks
 */
public final class Context<S extends NestCommandSender<?>> {

    private final NestPermitter<?> authority;
    private final S sender;
    private final CommandExecutor executor;
    private final Binding<?> binding;

    public Context(NestPermitter<?> authority, S sender, CommandExecutor executor, Binding<?> binding) {
        Objects.requireNonNull(authority);
        Objects.requireNonNull(sender);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(binding);
        this.authority = authority;
        this.sender = sender;
        this.executor = executor;
        this.binding = binding;
    }

    public NestPermitter<?> getAuthority() {
        return authority;
    }

    public S getSender() {
        return sender;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    public Binding<?> getBinding() {
        return binding;
    }

    public Context<S> setAuthority(NestPermitter<?> authority) {
        return new Context<>(authority, this.sender, this.executor, this.binding);
    }

    public <NS extends NestCommandSender<?>> Context<NS> setSender(NS sender) {
        return new Context<>(this.authority, sender, this.executor, this.binding);
    }

    public Context<S> setExecutor(CommandExecutor executor) {
        return new Context<>(this.authority, this.sender, executor, this.binding);
    }

    public Context<S> setBinding(Binding<?> binding) {
        return new Context<>(this.authority, this.sender, this.executor, binding);
    }
    
    public CommandExecution execute(NestObject<?> ... args) throws CommandException {
        return this.executor.execute(authority, sender, binding, args);
    }
    
    public CommandExecution execute(NestList args) throws CommandException {
        return this.executor.execute(authority, sender, binding, args);
    }
    
}
