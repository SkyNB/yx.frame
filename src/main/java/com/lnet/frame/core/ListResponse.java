package com.lnet.frame.core;

import java.util.*;

public class ListResponse<T> extends Response<List<T>> {

    //region constructor
    public ListResponse() {
        super(new ArrayList<>());
    }

    public ListResponse(List<T> body) {
        this();
        this.setBody(body);
    }

    public ListResponse(List<T> body, boolean success, String message) {
        this(body);
        this.setSuccess(success);
        this.setMessage(message);
    }
    //endregion

    //region override

    public ListResponse<T> setBody(List<T> body) {
        this.clear();
        this.addAll(body);
        return this;
    }


    public ListResponse<T> setSuccess(boolean success) {
        super.setSuccess(success);
        return this;
    }


    public ListResponse<T> setMessage(String message) {
        super.setMessage(message);
        return this;
    }
    //endregion

    //region implements list
    
    public int size() {
        return this.getBody().size();
    }

    
    public boolean isEmpty() {
        return this.getBody().isEmpty();
    }

    
    public boolean contains(Object o) {
        return this.getBody().contains(o);
    }

    
    public Iterator<T> iterator() {
        return this.getBody().iterator();
    }

    
    public Object[] toArray() {
        return this.getBody().toArray();
    }


    public <T1> T1[] toArray(T1[] a) {
        return this.getBody().toArray(a);
    }


    public boolean add(T t) {
        return this.getBody().add(t);
    }


    public boolean remove(Object o) {
        return this.getBody().remove(o);
    }


    public boolean containsAll(Collection<?> c) {
        return this.getBody().containsAll(c);
    }


    public boolean addAll(Collection<? extends T> c) {
        return this.getBody().addAll(c);
    }


    public boolean addAll(int index, Collection<? extends T> c) {
        return this.getBody().addAll(index, c);
    }


    public boolean removeAll(Collection<?> c) {
        return this.getBody().removeAll(c);
    }


    public boolean retainAll(Collection<?> c) {
        return this.getBody().retainAll(c);
    }


    public void clear() {
        this.getBody().clear();
    }


    public T get(int index) {
        return this.getBody().get(index);
    }


    public T set(int index, T element) {
        return this.getBody().set(index, element);
    }


    public void add(int index, T element) {
        this.getBody().add(index, element);
    }


    public T remove(int index) {
        return this.getBody().remove(index);
    }


    public int indexOf(Object o) {
        return this.getBody().indexOf(o);
    }


    public int lastIndexOf(Object o) {
        return this.getBody().lastIndexOf(o);
    }


    public ListIterator<T> listIterator() {
        return this.getBody().listIterator();
    }


    public ListIterator<T> listIterator(int index) {
        return this.getBody().listIterator(index);
    }


    public List<T> subList(int fromIndex, int toIndex) {
        return this.getBody().subList(fromIndex, toIndex);
    }
    //endregion
}
