package deque;

public interface Deque<T> {
    void addFirst(T item);
    void addLast(T item);
    public boolean isEmpty();
    public int size();
    public void printDeque();
    public T removeFirst();
    public T removeLast();
    public T get(int index);
    public boolean equals(Object o) ;
}
