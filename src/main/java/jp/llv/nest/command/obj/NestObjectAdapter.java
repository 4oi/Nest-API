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

import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import jp.llv.nest.command.exceptions.TypeMismatchException;

/**
 *
 * @author toyblocks
 */
public abstract class NestObjectAdapter<E> implements NestObject<E> {

    protected <T> T to(Class<T> toClass, Map.Entry<? extends Class<?>, ? extends Supplier<?>>... caster) throws TypeMismatchException {
        Objects.requireNonNull(toClass);
        if (toClass.isAssignableFrom(this.getClass())) {
            return (T) this;
        } else if (caster != null) {
            for (Map.Entry<? extends Class<?>, ? extends Supplier<?>> c : caster) {
                if (c.getKey() == toClass) {
                    return (T) c.getValue().get();
                }
            }
        }
        if (toClass == NestBool.class) {
            return (T) NestBool.TRUE;
        } else if (toClass == NestString.class) {
            return (T) new NestString(this.toString());
        } else if (toClass == NestList.class) {
            return (T) new NestList(this);
        }
        throw new TypeMismatchException(this, toClass);
    }

    @Override
    public <T> T to(Class<T> toClass) throws TypeMismatchException {
        return this.to(toClass, (Map.Entry<? extends Class<?>, ? extends Supplier<?>>[]) null);
    }

    protected static <E extends NestObject> Map.Entry<Class<E>, Supplier<E>> ifClass(Class<E> clazz, Supplier<E> supply) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(supply);
        return new Map.Entry<Class<E>, Supplier<E>>() {
            @Override
            public Class<E> getKey() {
                return clazz;
            }

            @Override
            public Supplier<E> getValue() {
                return supply;
            }

            @Override
            public Supplier<E> setValue(Supplier<E> value) {
                throw new UnsupportedOperationException();
            }
        };
    }

}
