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

import java.lang.reflect.Array;
import jp.llv.nest.command.Type;
import jp.llv.nest.command.exceptions.CommandException;
import jp.llv.nest.command.exceptions.TypeMismatchException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author toyblocks
 */
@Type("Object")
public interface NestObject<E> {
    
    <T> T to(@NotNull Class<T> toClass) throws TypeMismatchException;
    
    default @Nullable E unwrap() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    
    public static <T extends NestObject<?>> T to(@Nullable NestObject<?> from,@NotNull  Class<T> toClass) throws CommandException {
        if (from == null) {
            if (toClass == NestString.class) {
                return (T) new NestString("nil");
            } else {
                return null;
            }
        } else {
            return from.to(toClass);
        }
    }
    
    public static <T> T[] unwrap(NestObject<T>[] objects, Class<T> clazz) {
        T[] a = (T[]) Array.newInstance(clazz, objects.length);
        for (int i = 0; i < objects.length; i++) {
            a[i] = objects[i].unwrap();
        }
        return a;
    }
    
    public static Object[] unwrap(NestObject[] objects) {
        Object[] a = new Object[objects.length];
        for (int i = 0; i < objects.length; i++) {
            a[i] = objects[i].unwrap();
        }
        return a;
    }
    
}
