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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import jp.llv.nest.command.Type;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author toyblocks
 */
@Type("List")
public final class NestList extends  NestValueAdapter<List<NestObject<?>>> {
    
    public NestList() {
        super(new ArrayList<>());
    }
    
    public NestList(int capacity) {
        super(new ArrayList<>(capacity));
        for (int i = 0 ; i < capacity ; i ++) {
            this.value.set(i, null);
        }
    }
    
    public NestList(@NotNull Collection<? extends NestObject<?>> values) {
        super(new ArrayList<>(values));
    }
    
    public NestList(@NotNull NestObject<?> ... values) {
        super(new ArrayList<>(Arrays.asList(values)));
    }

    @Override
    public List<NestObject<?>> unwrap() {
        return super.value;
    }
    
    public int size() {
        return this.value.size();
    }
    
    public NestObject<NestObject<?>>[] toArray() {
        return this.value.toArray(new NestObject[this.value.size()]);
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }

    public boolean contains(NestObject<?> o) {
        return value.contains(o);
    }

    public Iterator<NestObject<?>> iterator() {
        return value.iterator();
    }

    public boolean add(NestObject<?> e) {
        return value.add(e);
    }

    public boolean addAll(Collection<? extends NestObject<?>> c) {
        return value.addAll(c);
    }

    public NestObject<?> get(int index) {
        return value.get(index);
    }

    public NestObject<?> set(int index, NestObject<?> element) {
        return value.set(index, element);
    }

    public Stream<NestObject<?>> stream() {
        return value.stream();
    }

    public Stream<NestObject<?>> parallelStream() {
        return value.parallelStream();
    }

    public void forEach(Consumer<? super NestObject<?>> action) {
        value.forEach(action);
    }
    
    /* NOT @Override NestJson.Jsonable */
    public JSONArray toJson() {
        JSONArray array = new JSONArray();
        this.forEach(obj -> {
            if (obj == null) {
                array.put(JSONObject.NULL);
            } else {
                array.put(NestJson.toJson(obj));
            }
        });
        return array;
    }
    
}
