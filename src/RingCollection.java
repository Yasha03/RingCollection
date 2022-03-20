import java.util.*;


/**
 * A ring collection class that updates elements
 * <p>
 * The ring collection has many useful methods and two constructors
 * @author Yasha Ivanov
 * @version 1.1.0
 * @param <T>
 */
public class RingCollection<T> extends AbstractCollection<T> {
    private int maxSize;
    private int index;
    private T[] arr;
    private T lastElement;

    /**
     * Constructor that set the initial size of the ring collection
     * @param maxSize
     * @exception IllegalArgumentException
     */
    RingCollection(int maxSize){
        if (maxSize > 0) {
            this.maxSize = maxSize;
            this.index = 0;
            this.arr = (T[]) new Object[maxSize];
        }else{
            throw new IllegalArgumentException();
        }
    }

    /**
     * Constructor that will set the collection size to 10
     */
    RingCollection(){
        this(10);
    }

    /**
     * @return returns a ring collection as an array
     */
    public T[] getArr(){
        return arr;
    }

    /**
     * @param element - added element
     * @return will return true if the element was added correctly
     */
    public boolean add(T element){
        if(this.index >= this.maxSize){
            this.lastElement = this.arr[0];
            this.index = 0;
        }
        this.arr[this.index] = element;
        this.index++;
        return true;
    }

    /**
     * @return will return the number of duplicate elements
     */
    public int countRepeatingElements(){
        return (int) (size() - Arrays.stream(this.arr).distinct().count());
    }

    /**
     * Clears the entire collection, turning the values to null
     */
    public void clearAll(){
        this.arr = (T[]) Arrays.stream(this.arr).map(x -> null).toArray();
    }

    /**
     * Sorts the elements of a collection according to a comparator
     * @param comparator - comparator object to sort
     */
    public void sortElements(Comparator comparator){
        this.arr = (T[]) Arrays.stream(this.arr).sorted(comparator).toArray();
    }

    /**
     * Removes an element from the collection at the specified position
     * @param pos - position to remove
     */
    public void delete(int pos){
        T last;
        for (int i = pos; i < this.arr.length-1; i++){
            last = this.arr[i+1];
            this.arr[i] = last;
        }
        this.index--;
        this.arr[this.arr.length-1] = this.lastElement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RingCollection<?> that = (RingCollection<?>) o;
        return maxSize == that.maxSize && Arrays.equals(arr, that.arr);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(maxSize);
        result = 31 * result + Arrays.hashCode(arr);
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new RingIterator<>(this.arr);
    }

    /**
     * @return maximum collection size
     */
    @Override
    public int size() {
        return maxSize;
    }

    private class RingIterator<T> implements Iterator<T> {
        private T[] arr;
        private int cursor;

        public RingIterator(T[] arr){
            this.arr = Arrays.copyOf(arr, arr.length);
            this.cursor = 0;
        }

        @Override
        public boolean hasNext() {
            checkCursor();
            while(this.cursor < arr.length){
                if(this.arr[cursor] != null){
                    return true;
                }
                this.cursor++;
            }
            return true;
        }

        @Override
        public T next() {
            checkCursor();
            try{
                T obj = this.arr[cursor];
                this.cursor++;
                return obj;
            }catch (ArrayIndexOutOfBoundsException ex){
                throw new NoSuchElementException();
            }
        }

        private void checkCursor(){
            if(this.cursor >= size()){
                this.cursor = 0;
            }
        }

        @Override
        public void remove() {
            Iterator.super.remove();
        }
    }

}
