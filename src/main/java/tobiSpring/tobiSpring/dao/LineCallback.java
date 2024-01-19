package tobiSpring.tobiSpring.dao;

import java.io.IOException;

public interface LineCallback<T> {

    T doSomethingWithLine(String line, T value);
}
