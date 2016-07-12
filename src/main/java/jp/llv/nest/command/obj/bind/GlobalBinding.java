/*
 * Copyright (C) 2016 toyblocks
 */
package jp.llv.nest.command.obj.bind;

import jp.llv.nest.command.obj.NestCommandSender;

/**
 *
 * @author toyblocks
 */
public interface GlobalBinding<E> extends Binding<E> {

    Binding getPrivateBinding(NestCommandSender<?> owner);
    
}
