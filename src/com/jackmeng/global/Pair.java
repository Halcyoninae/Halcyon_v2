package com.jackmeng.global;

/**
 * This class dedicates a Map style data structure,
 * however it is not a list-like data structure.
 *
 * @author Jack Meng
 * @since 3.0
 */
public class Pair<T, E> {
  public T first;
  public E second;

  /**
   * Constructs the Pair object
   * 
   * @param first  First element of T type (generic)
   * @param second Second element of E type (generic)
   */
  public Pair(T first, E second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Retrieves the first element
   * 
   * @return The first element returned as the original T type (generic)
   */
  public T getFirst() {
    return first;
  }

  /**
   * Retrieves the second element
   * 
   * @return The second element returned as the original E type (generic)
   */
  public E getSecond() {
    return second;
  }
}
