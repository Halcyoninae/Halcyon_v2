package com.jackmeng.global;

/**
 * A rarely used data structure class that holds
 * 3 generic objects. It extends a pair class.
 *
 * @author Jack Meng
 * @see com.jackmeng.global.Pair
 * @since 3.0
 */
public class Trio<T, E, M> extends Pair<T, E> {
  public M third;

  /**
   * Constructs a trio with the given first and second elements and third elements
   * @param first The first element which will be found in a Pair data structure
   * @param second The second element which will also be supered to the extended Pair class
   * @param third The third element which is held by this child class
   */
  public Trio(T first, E second, M third) {
    super(first, second);
    this.third = third;
  }
}
