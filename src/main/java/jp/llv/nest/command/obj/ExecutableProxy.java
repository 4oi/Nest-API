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
import jp.llv.nest.command.exceptions.CommandException;
import jp.llv.nest.command.exceptions.TypeMismatchException;
import jp.llv.nest.command.obj.bind.Binding;

/**
 *
 * @author toyblocks
 */
public class ExecutableProxy<T> extends NestObjectAdapter<T> implements NestExecutable<T> {

    private final NestExecutable<T> executable;
    private final CommandExecutor executor;
    private final NestCommandSender<?> sender;

    public ExecutableProxy(CommandExecutor executor, NestCommandSender<?> sender, NestExecutable<T> executable) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(executable);
        Objects.requireNonNull(sender);
        this.executable = executable;
        this.executor = executor;
        this.sender = sender;
    }

    @Override
    public NestObject<?> execute(CommandExecutor executor, NestCommandSender sender, Binding binding, NestObject<?>... args) throws CommandException {
        return this.executable.execute(this.executor, this.sender, binding, args);
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
    public Class<? extends NestCommandSender> getAllowedSender() {
        return executable.getAllowedSender();
    }

    @Override
    public Class<? extends NestObject> getReturnType() {
        return executable.getReturnType();
    }

    @Override
    public <T> T to(Class<T> toClass) throws TypeMismatchException {
        return executable.to(toClass);
    }

    @Override
    public T unwrap() throws UnsupportedOperationException {
        return executable.unwrap();
    }
    
}
