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

import jp.llv.nest.command.Type;
import jp.llv.nest.i18n.InjectableMessage;

/**
 *
 * @author toyblocks
 */
@Type("User")
public abstract class NestCommandSender<E> extends NestValueAdapter<E> implements NestPermitter<E> {

    public NestCommandSender(E sender) {
        super(sender);
    }

    public abstract void sendMessage(String name);
    
    public void sendMessage(CharSequence seq) {
        if (seq instanceof String) {
            this.sendMessage((String) seq);
        } else if (seq instanceof InjectableMessage) {
            this.sendMessage(seq.toString());
        } else {
            this.sendMessage(new StringBuilder(seq).toString());
        }
    }

}
