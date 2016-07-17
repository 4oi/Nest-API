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
package jp.llv.nest.event;

import java.util.function.Consumer;
import jp.llv.nest.command.exceptions.EventHandlingException;
import jp.llv.nest.command.obj.NestObject;

/**
 *
 * @author toyblocks
 */
public interface EventManager {

    void fireThrowing(NestObject<?> event) throws EventHandlingException;
    
    void fire(NestObject<?> event);

    default boolean fireThrowing(Cancelable event) throws EventHandlingException {
        this.fireThrowing((NestObject<?>) event);
        return event.isCanceled();
    }

    default boolean fire(Cancelable event) {
        this.fire((NestObject<?>) event);
        return event.isCanceled();
    }

    <E extends NestObject<?>> EventManager on(Class<E> clazz, int priority, Consumer<? super E> listener);

    default <E extends NestObject<?>> EventManager on(Class<E> clazz, Consumer<? super E> listener) {
        return this.on(clazz, Priority.NORMAL, listener);
    }

    void unregister(Consumer<?> listener);

}
