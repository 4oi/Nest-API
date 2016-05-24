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

import jp.llv.nest.command.exceptions.TypeMismatchException;

/**
 *
 * @author toyblocks
 */
public abstract class NestCommandSender<E> extends NestValueAdapter<E> {

    public NestCommandSender(E sender) {
        super(sender);
    }
    
    public abstract void sendMessage(String name);
    
    public abstract boolean hasPermission(String permission);
    
    public static class PermBreakThroughCommandSenderWrap<E> extends NestCommandSender<E> {

        private final NestCommandSender<E> wrap;
        
        public PermBreakThroughCommandSenderWrap(NestCommandSender<E> sender) {
            super(sender.value);
            this.wrap = sender;
        }

        @Override
        public void sendMessage(String name) {
            wrap.sendMessage(name);
        }

        @Override
        public boolean hasPermission(String permission) {
            return true;
        }

        @Override
        public E unwrap() {
            return wrap.unwrap();
        }

        @Override
        public String toString() {
            return wrap.toString();
        }

        @Override
        public int hashCode() {
            return wrap.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return wrap.equals(obj);
        }

        @Override
        public <T> T to(Class<T> toClass) throws TypeMismatchException {
            return wrap.to(toClass);
        }
        
    }
    
}
