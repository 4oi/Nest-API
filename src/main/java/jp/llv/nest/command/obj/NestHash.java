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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import jp.llv.nest.command.exceptions.UndefinedVariableException;
import jp.llv.nest.command.obj.bind.KeyedValueSet;

/**
 *
 * @author toyblocks
 */
public final class NestHash<K extends NestObject<?>, V extends NestObject<?>> extends NestObjectAdapter<Map<K, V>> implements KeyedValueSet<Map<K, V>> {

    private final Map<K, V> values;
    
    public NestHash() {
        this.values = new HashMap<>();
    }
    
    public NestHash(Map<K, V> source) {
        this.values = new HashMap<>(source);
    }
    
    public V get(K key) {
        return this.values.get(key);
    }

    @Override
    public V get(NestString key) throws UndefinedVariableException {
        if (key == null) {
            return values.get(null);
        }
        Iterator<Map.Entry<K, V>> it = this.values.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> e = it.next();
            if (Objects.hashCode(e) == key.hashCode() && key.equals(e)) {
                return e.getValue();
            }
        }
        throw new UndefinedVariableException(this, key.toString());
    }
    
    public void set(K key, V value) {
        this.values.put(key, value);
    }
    
    @Override
    public Map<K, V> unwrap() throws UnsupportedOperationException {
        return Collections.unmodifiableMap(values);
    }

    @Override
    public String toString() {
        return this.values.toString();
    }
    
    public int size() {
        return this.values.size();
    }

    @Override
    public Collection<String> keySet() {
        return this.values.keySet().stream()
                .filter(k -> k instanceof NestString)
                .map(NestObject::toString)
                .collect(Collectors.toList());
    }
    
}
