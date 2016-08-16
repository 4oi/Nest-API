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

import jp.llv.nest.command.Context;
import jp.llv.nest.command.exceptions.CommandException;

/**
 *
 * @author toyblocks
 */
public abstract class ContextExecutable<S extends NestCommandSender<?>, E> extends NestObjectAdapter<E> implements NestExecutable<S, E> {

    private final Context<S> context;

    public ContextExecutable(Context<S> context) {
        this.context = context;
    }

    public Context<S> getContext() {
        return context;
    }

    @Override
    public NestObject<?> execute(Context<? extends S> context, NestObject<?>... args) throws CommandException {
        return this.executeIn(context.setBinding(this.context.getBinding()), args);
    }

    public NestObject<?> execute(NestObject<?>... args) throws CommandException {
        return this.executeIn(this.context, args);
    }

    public abstract NestObject<?> executeIn(Context<? extends S> context, NestObject<?>... args) throws CommandException;

}
