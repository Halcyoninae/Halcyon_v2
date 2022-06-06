package com.jackmeng.global;

public class Trio<T, E, M> extends Pair<T, E> {
  public M third;

  public Trio(T first, E second, M third) {
    super(first, second);
    this.third = third;
  }
}

