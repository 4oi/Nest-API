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
package jp.llv.nest.command.exceptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author toyblocks
 */
public class EventHandlingException extends CommandException implements Iterable<Throwable> {
    
    private final List<Throwable> causes = new ArrayList<>();

    public EventHandlingException() {
        super("Failed to handle event");
    }

    public int size() {
        return causes.size();
    }

    @Override
    public Iterator<Throwable> iterator() {
        return causes.iterator();
    }

    public Throwable[] toArray() {
        return (Throwable[]) causes.toArray();
    }

    public boolean add(Throwable e) {
        return causes.add(e);
    }
    
    public boolean addAll(EventHandlingException ex) {
        return this.causes.addAll(ex.causes);
    }

    public Throwable get(int index) {
        return causes.get(index);
    }

    public List<Throwable> getCauses() {
        return Collections.unmodifiableList(causes);
    }
    
}
