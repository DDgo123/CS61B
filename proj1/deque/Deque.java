package deque;

public interface Deque<T> {
    void addFirst(T item);
    void addLast(T item);
    default boolean isEmpty(){
        if(size()== 0){
            return true;
        }
        return false;
    };
    public int size();
    public void printDeque();
    public T removeFirst();
    public T removeLast();
    public T get(int index);
    public boolean equals(Object o) ;

}
