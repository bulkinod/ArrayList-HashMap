package org.example;

import java.util.Arrays;
import java.util.Objects;

public class ArrayList<E> {
    private static final int capacity = 10; // количество элементов в списке по умолчанию

    private int size; // количество добавленных элементов

    private static final Object[] zeroElementList = {}; // при инициализации списка нулем

    private static final Object[] defaultElementList = {}; // список без параметров

    Object[] elements; // массив, где хранятся элементы списка

    public ArrayList(){
        this.elements = defaultElementList;
    }

    public ArrayList(int capacity){
        if(capacity > 0){
            this.elements = new Object[capacity];
        }

        else if(capacity == 0){
            this.elements = zeroElementList;
        }

        else {
            throw new IllegalArgumentException("Количество элементов не может быть меньше нуля");
        }
    }

    public E get(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        return (E)elements[index];
    }

    private Object[] grow(int minCapacity){
        int oldCapacity = elements.length;
        if(oldCapacity > 0 || elements != defaultElementList) {
            int newCapacity = (int) Math.max(minCapacity, oldCapacity * 1.5);
            return elements = Arrays.copyOf(elements, newCapacity);
        }
        else{
            return elements = new Object[Math.max(minCapacity, capacity)];
        }
    }

    private Object[] grow() {
        return grow(size + 1);
    }

    public void add(int index, E element){
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        }

        int currentSize = size;

        Object[] elementCopy = elements;

        if(currentSize == elementCopy.length){
            elementCopy = grow();
        }

        int elementsToMove = currentSize - index;
        if (elementsToMove > 0) {
            System.arraycopy(elementCopy, index, elementCopy, index + 1, elementsToMove);
        }

        elementCopy[index] = element;

        size = currentSize + 1;
        elements = elementCopy;
    }

    public boolean add(E e) {
        add(e, elements, size);
        return true;
    }

    private void add(E e, Object[] elementData, int s) {
        if (s == elementData.length)
            elementData = grow();
        elementData[s] = e;
        size = s + 1;
    }

    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        E oldValue = (E) elements[index];

        int elementsToMove = size - index - 1;
        if (elementsToMove > 0) {
            System.arraycopy(elements, index + 1, elements, index, elementsToMove);
        }


        elements[--size] = null;

        return oldValue;
    }
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, elements[i])) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    public int indexOf(Object o){
        if(o == null){
            for(int i = 0; i < size; i++){
                if(elements[i] == null){
                    return i;
                }
            }
        }
        else {
            for(int i = 0; i < size; i++){
                if(o.equals(elements[i])){
                    return i;
                }
            }
        }

        return -1;
    }
}
