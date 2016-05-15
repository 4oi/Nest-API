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
import jp.llv.nest.command.exceptions.TypeMismatchException;

/**
 *
 * @author toyblocks
 */
public class PairedValue extends NestValueAdapter<Map.Entry<NestObject<?>, NestObject<?>>> {

    public PairedValue(Map.Entry<NestObject<?>, NestObject<?>> value) {
        super(value);
    }

    public PairedValue(NestObject<?> key, NestObject<?> value) {
        this(new Entry(key, value));
    }
    
    public PairedValue(String str) throws TypeMismatchException {
        this(toEntry(str));
    }
    
    private static Entry<NestObject<?>, NestObject<?>> toEntry(String str) throws TypeMismatchException {
        String[] pair = str.replace("::","ː").split(":");
        if (pair.length != 2) {
            throw new TypeMismatchException("Present value is not paired through ':'");
        }
        return new Entry<>(new NestString(pair[0].replace('ː', ':')), new NestString(pair[1].replace('ː', ':')));
    }
    
    public NestObject<?> getKey() {
        return super.value.getKey();
    }
    
    public NestObject<?> getValue() {
        return super.value.getValue();
    }
    
    private static class Entry<K, V> implements Map.Entry<K, V> {

        private final K key;
        private final V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }
        
    }

}
