package dk.s4_g1.common.services;

public interface ISubscription<T> extends Runnable {
    public T getValue();
}
