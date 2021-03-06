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
package jp.llv.nest.command.token;

import java.util.concurrent.CompletableFuture;
import jp.llv.nest.command.CommandExecution;
import jp.llv.nest.command.Context;
import jp.llv.nest.command.obj.NestObject;

/**
 *
 * @author toyblocks
 */
public interface Token {

    CommandExecution execute(Context context);

    public static CompletableFuture<NestObject<?>[]> allOf(CompletableFuture<? extends NestObject<?>>... elements) {
        return CompletableFuture.allOf(elements).thenApply(v -> {
            NestObject<?>[] result = new NestObject[elements.length];
            for (int i = 0; i < elements.length; i++) {
                result[i] = elements[i].join();
            }
            return result;
        });
    }

    public static CompletableFuture<NestObject<?>[]> allOf(Context context, Token... elements) {
        CompletableFuture[] result = new CompletableFuture[elements.length];
        for (int i = 0; i < elements.length; i++) {
            result[i] = elements[i].execute(context.newInner()).getResult();
        }
        return allOf(result);
    }

    default String toString(boolean topLevel) {
        return this.toString();
    }
    
}
