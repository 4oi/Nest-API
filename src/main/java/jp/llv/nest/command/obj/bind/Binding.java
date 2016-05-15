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
package jp.llv.nest.command.obj.bind;

import jp.llv.nest.command.exceptions.UndefinedVariableException;
import jp.llv.nest.command.exceptions.UnmodifiableVariableException;
import jp.llv.nest.command.obj.NestObject;
import jp.llv.nest.command.obj.NestString;

/**
 *
 * @author toyblocks
 */
public interface Binding<E> extends KeyedValueSet<E> {
    
    NestObject<?> get(String key) throws UndefinedVariableException;
    
    @Override
    default NestObject<?> get(NestString key) throws UndefinedVariableException {
        return this.get(key.unwrap());
    }
    
    default boolean isDefined(String key) {
        return this.keySet().contains(key);
    }
    
    void set(String key, NestObject<?> value) throws UnmodifiableVariableException;
    
    default void setUniqueValue(String key, NestObject<?> value) throws UnmodifiableVariableException {
        this.set(key+"@"+System.identityHashCode(this), value);
    }
    
    default void setUniqueValue(NestString key, NestObject<?> value) throws UnmodifiableVariableException {
        this.setUniqueValue(key.unwrap(), value);
    }
    
    default NestObject<?> getUniqueValue(String key) throws UndefinedVariableException {
        return this.get(key+"@"+System.identityHashCode(this));
    }
    
    default NestObject<?> getUniqueValue(NestString key) throws UndefinedVariableException {
        return this.getUniqueValue(key.unwrap());
    }
    
    Binding newChild();
    
    Binding getParent();
    
    void del(String key) throws UnmodifiableVariableException;
    
}
