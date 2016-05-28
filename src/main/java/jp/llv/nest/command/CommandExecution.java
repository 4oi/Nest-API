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
import java.util.concurrent.CompletableFuture;
import jp.llv.nest.NestAPI;
import jp.llv.nest.command.exceptions.CommandException;
import jp.llv.nest.command.obj.NestObject;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author toyblocks
 */
public final class CommandExecution<R extends NestObject<?>> {

    private final CompletableFuture<R> result;
    private final Context context;

    public CommandExecution(CompletableFuture<R> result, @NotNull Context context) {
        this.result = result == null? CompletableFuture.completedFuture(null) : result;
        Objects.requireNonNull(context);
        this.context = context;
    }

    public @NotNull CompletableFuture<R> getResult() {
        return result;
    }

    public @NotNull Context getContext() {
        return context;
    }

    public NestObject<?> getResultNow() throws CommandException {
        return NestAPI.getResultNow(this.getResult());
    }

}
