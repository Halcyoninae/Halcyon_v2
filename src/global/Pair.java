package global;

public class Pair<T, E> {
  public T first;
  public E second;

  public Pair(T first, E second) {
    this.first = first;
    this.second = second;
  }

  public T getFirst() {
    return first;
  }

  public E getSecond() {
    return second;
  }
}
