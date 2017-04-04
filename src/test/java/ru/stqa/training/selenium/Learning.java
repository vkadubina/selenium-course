package ru.stqa.training.selenium;

import org.junit.Test;

import java.util.function.Function;

import static junit.framework.TestCase.*;

/**
 * @author Victoria Kadubina
 */
public class Learning {

    Opt<String> opt1 = new Some<>("1");
    Opt<String> opt2 = new Some<>("2");
    Opt<String> opt3 = new None<>();

    @Test
    public void testMapWithSomes() {
        Opt<Integer> resOpt = opt1.map(str -> Integer.parseInt(str));
        assertTrue(resOpt.isDefined());
        assertTrue(resOpt.get() == 1);
    }

    @Test
    public void testMapWithNone() {
        Opt<Integer> resOpt = opt3.map(str -> Integer.parseInt(str));
        assertTrue(resOpt.isEmpty());
    }


    @Test
    public void testFlatMapWithSomes() {
        Opt<Integer> resOpt = opt1.flatMap(
                str1 -> opt2.map(str2 -> Integer.parseInt(str1) + Integer.parseInt(str2)));
        assertTrue(resOpt.isDefined());
        assertTrue(resOpt.get() == 3);
    }

    @Test
    public void testFlatMapWithOutterNone() {
        Opt<Integer> resOpt = opt3.flatMap(str1 -> opt2.map(str2 -> Integer.parseInt(str1) + Integer.parseInt(str2)));
        assertTrue(resOpt.isEmpty());
    }

    @Test
    public void testFlatMapWithInnerNone() {
        Opt<Integer> resOpt = opt1.flatMap(str1 -> opt3.map(str2 -> Integer.parseInt(str1) + Integer.parseInt(str2)));
        assertTrue(resOpt.isEmpty());
    }


    @Test(expected = NullPointerException.class)
    public void testGetFromNoneThrowException() {
        opt3.get();
    }

    @Test()
    public void testGetOrElseWithSomeReturnsOriginalValue() {
        assertTrue(opt1.getOrElse("6").equals("1"));
    }

    @Test()
    public void testGetOrElseWithNoneReturnsSpecifiedValue() {
        assertTrue(opt3.getOrElse("6").equals("6"));
    }
}

interface Opt<T> {

    boolean isDefined();

    boolean isEmpty();

    T get();

    T getOrElse(T other);

    <R> Opt<R> map(Function<T, R> function);

    <R> Opt<R> flatMap(Function<T, Opt<R>> function);

}

class Some<T> implements Opt<T> {

    private T value;

    public Some(T value) {
        this.value = value;
    }

    @Override
    public boolean isDefined() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public T getOrElse(T other) {
        return value;
    }

    @Override
    public <R> Opt<R> map(Function<T, R> function) {
        R result = function.apply(value);
        return new Some(result);
    }

    @Override
    public <R> Opt<R> flatMap(Function<T, Opt<R>> function) {
        Opt<R> result = function.apply(value);
        return result;
    }

    @Override
    public String toString() {
        return "Some(" + value + ')';
    }
}

class None<T> implements Opt<T> {

    @Override
    public boolean isDefined() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public T get() {
        throw new NullPointerException("Value is not defined");
    }

    @Override
    public T getOrElse(T other) {
        return other;
    }

    @Override
    public <R> Opt<R> map(Function<T, R> function) {
        return new None<R>();
    }

    @Override
    public <R> Opt<R> flatMap(Function<T, Opt<R>> function) {
        return new None<R>();
    }

    @Override
    public String toString() {
        return "None";
    }
}
