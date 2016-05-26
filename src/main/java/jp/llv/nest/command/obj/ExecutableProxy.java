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
package jp.llv.nest.command.obj;

import java.util.Objects;
import jp.llv.nest.command.CommandExecutor;
import jp.llv.nest.command.Context;
import jp.llv.nest.command.exceptions.CommandException;
import jp.llv.nest.command.obj.bind.Binding;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author toyblocks
 */
public class ExecutableProxy<S extends NestCommandSender<?>, T> extends NestObjectAdapter<T> implements NestExecutable<S, T> {

    private final NestExecutable<S, T> executable;
    private final NestPermitter<?> authority;
    private final S sender;
    private final CommandExecutor executor;
    private final Binding<?> binding;

    public ExecutableProxy(@Nullable NestPermitter<?> authority, @Nullable S sender, @Nullable CommandExecutor executor, @Nullable Binding<?> binding, @NotNull NestExecutable<S, T> executable) {
        Objects.requireNonNull(executable);
        this.executable = executable;
        this.authority = authority;
        this.sender = sender;
        this.executor = executor;
        this.binding = binding;
    }

    public ExecutableProxy(@Nullable NestPermitter<?> authority, @NotNull NestExecutable<S, T> executable) {
        this(authority, null, null, null, executable);
    }

    public ExecutableProxy(@Nullable S sender, @NotNull NestExecutable<S, T> executable) {
        this(null, sender, null, null, executable);
    }

    public ExecutableProxy(@Nullable CommandExecutor executor, @NotNull NestExecutable<S, T> executable) {
        this(null, null, executor, null, executable);
    }

    public ExecutableProxy(@Nullable Binding<?> binding, @NotNull NestExecutable<S, T> executable) {
        this(null, null, null, binding, executable);
    }

    @Override
    public @Nullable NestObject<?> execute(@NotNull Context<S> context, NestObject<?>... args) throws CommandException {
        Context<S> ctx = context;
        if (this.authority != null) {
            ctx = ctx.setAuthority(authority);
        }
        if (this.sender != null) {
            ctx = ctx.setSender(sender);
        }
        if (this.executor != null) {
            ctx = ctx.setExecutor(executor);
        }
        if (this.binding != null) {
            ctx = ctx.setBinding(binding);
        }
        return this.executable.execute(ctx, args);
    }

    @Override
    public String[] getDescription() {
        return this.executable.getDescription();
    }

    @Override
    public ArgDescription[] getArgDescriptions() {
        return this.executable.getArgDescriptions();
    }

    @Override
    public String getPermission() {
        return executable.getPermission();
    }

    @Override
    public Class<? super S> getAllowedSender() {
        return executable.getAllowedSender();
    }

    @Override
    public Class<? extends NestObject> getReturnType() {
        return executable.getReturnType();
    }

    @Override
    public String toString() {
        return "ExecutableProxy{" + "executable=" + executable + ", authority=" + authority + ", sender=" + sender + ", executor=" + executor + ", binding=" + binding + '}';
    }

    @Override
    public T unwrap() throws UnsupportedOperationException {
        return executable.unwrap();
    }

}
