package com.norman.guava;

import com.google.common.collect.ForwardingList;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/30 9:39 AM.
 */
public class AddLoggingList<E> extends ForwardingList<E> {

    final List<E> delegate; // backing list

    public AddLoggingList(){
        delegate = Lists.newArrayList();
    }

    public AddLoggingList(List<E> list){
        this.delegate = list;
    }

    @Override
    protected List<E> delegate() {
        return delegate;
    }

    @Override
    public void add(int index, E elem) {
//        log(index, elem);
        super.add(index, elem);
    }

    @Override
    public boolean add(E elem) {
        return standardAdd(elem); // implements in terms of add(int, E)
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return standardAddAll(c); // implements in terms of add
    }
}
