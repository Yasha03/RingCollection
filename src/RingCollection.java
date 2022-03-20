import java.util.*;

public class RingCollection<T> extends AbstractCollection<T> {
    private int maxSize;
    private int index;
    private T[] arr;

    RingCollection(int maxSize){
        if (maxSize > 0) {
            this.maxSize = maxSize;
            this.index = 0;
            this.arr = (T[]) new Object[maxSize];
        }else{
            throw new IllegalArgumentException();
        }
    }

    RingCollection(){
        this(10);
    }

    public T[] getArr(){
        return arr;
    }

    public boolean add(T element){
        if(this.index >= this.maxSize){
            this.index = 0;
        }
        this.arr[this.index] = element;
        this.index++;
        return true;
    }


    public void delete(int index){

        for (int i = index; i < this.arr.length-1; i++){
            this.arr[i] = this.arr[i+1];
        }
        //this.index--;
        this.arr[this.arr.length-1] = null;
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
