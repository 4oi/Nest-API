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

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * An exception caused by multiple exceptions. 
 * @author toyblocks
 */
public class MultipleException extends CommandException {
 
    private final Throwable[] causes;

    /**
     * Constructs new exception caused by 
     * @param causes causes of this exception
     * @deprecated You should submit a message because auto generated one with multiple caueses may be too long .
     */
    @Deprecated
    public MultipleException(Throwable... causes) {
        this(Arrays.stream(causes).map(Throwable::getMessage).collect(Collectors.joining(" OR ")), causes);
    }

    public MultipleException(String string, Throwable ... causes) {
        super(string);
        this.causes = causes;
    }

    public Throwable[] getCauses() {
        return this.causes.clone();
    }
    
}
