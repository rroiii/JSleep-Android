package com.netlab.RoyOswaldhaJSleepRJ;

/**
 * Algorithm to modify data
 * @author Roy Oswaldha
 */

import java.util.*;
import java.util.Arrays;
import java.util.Iterator;
public class Algorithm {
    private Algorithm(){}

    /**
     * @param iterable the data
     * @param value filter
     * @return
     * @param <T> data type
     */
    public static <T> List<T> collect (Iterable<T> iterable, T value){
        Iterator<T> iterator = iterable.iterator();
        Predicate<T> pred = value::equals;
        return collect(iterator,pred);
    }

    /**
     * @param iterable the data
     * @param pred
     * @return
     * @param <T> data type
     */
    public static <T> List<T> collect (Iterable<T> iterable, Predicate<T> pred){
        Iterator<T> iterator = iterable.iterator();
        return collect(iterator,pred);
    }

    /**
     * @param array
     * @param value
     * @return
     * @param <T>
     */
    public static <T> List<T> collect (T[] array, T value){
        Iterator<T> iterator = Arrays.stream(array).iterator();
        Predicate<T> pred = value::equals;
        return collect(iterator,pred);
    }
    /**
     * @param iterator
     * @param value
     * @return
     * @param <T>
     */
    public static <T> List<T> collect (Iterator<T> iterator, T value){
        Predicate<T> pred = value::equals;
        return collect(iterator,pred);
    }
    /**
     * @param array
     * @param pred
     * @return
     * @param <T>
     */
    public static <T> List<T> collect (T[] array, Predicate<T> pred){
        Iterator<T> iterator = Arrays.stream(array).iterator();
        return collect(iterator,pred);
    }
    /**
     * @param iterator
     * @param pred
     * @return
     * @param <T>
     */
    public static <T> List<T> collect (Iterator<T> iterator, Predicate<T> pred){
        List<T> list = new ArrayList<T>();
        T check;
        while (iterator.hasNext()) {
            check = iterator.next();
            if (pred.predicate(check)) {
                list.add(check);
            }
        }
        return list;
    }
    /**
     * @param iterator
     * @param value
     * @return
     * @param <T>
     */
    public static <T> int count(Iterator<T> iterator, T value){
        final Predicate<T> pred = value::equals;
        return count(iterator, pred);
    }
    /**
     * @param array
     * @param value
     * @return
     * @param <T>
     */
    public static <T> int count(T[] array, T value){
        final Iterator<T> iter = Arrays.stream(array).iterator();
        return count(iter, value);
    }
    /**
     * @param iterable
     * @param pred
     * @return
     * @param <T>
     */
    public static <T> int count(Iterable<T> iterable, Predicate<T> pred){
        final Iterator<T> iter = iterable.iterator();
        return count(iter, pred);
    }
    /**
     * @param array
     * @param pred
     * @return
     * @param <T>
     */
    public static <T> int count(T[] array, Predicate<T> pred){
        final Iterator<T> iter = Arrays.stream(array).iterator();
        return count(iter, pred);
    }
    /**
     * @param iterable
     * @param value
     * @return
     * @param <T>
     */
    public static <T> int count(Iterable<T> iterable, T value){
        final Iterator<T> iter = iterable.iterator();
        return count(iter, value);
    }
    /**
     * @param iterator
     * @param pred
     * @return
     * @param <T>
     */
    public static <T> int count(Iterator<T> iterator, Predicate<T> pred){
        int counter = 0;
        while(iterator.hasNext()) {
            T x = iterator.next();

            if (pred.predicate(x)){
                counter++;
            }
        }
        return counter;
    }
    /**
     * @param iterator
     * @param value
     * @return
     * @param <T>
     */
    public static <T> boolean exists(Iterable<T> iterator, T value){
        final Iterator<T> iter = iterator.iterator();
        return exists(iter, value);
    }
    /**
     * @param iterator
     * @param predicator
     * @return
     * @param <T>
     */
    public static <T> boolean exists(Iterable<T> iterator, Predicate<T> predicator){
        final Iterator<T> iter = iterator.iterator();
        return exists(iter, predicator);
    }

    /**
     * @param array
     * @param predicator
     * @return
     * @param <T>
     */
    public static <T> boolean exists(T[] array, Predicate<T> predicator){
        final Iterator<T> iter = Arrays.stream(array).iterator();
        return exists(iter, predicator);
    }

    /**
     * @param array
     * @param value
     * @return
     * @param <T>
     */
    public static <T> boolean exists(T[] array, T value){
        final Iterator<T> it = Arrays.stream(array).iterator();
        return exists(it, value);
    }

    /**
     * @param iterator
     * @param value
     * @return
     * @param <T>
     */
    public static <T> boolean exists(Iterator<T> iterator, T value){
        final Predicate<T> pred = value::equals;
        return exists(iterator, pred);
    }

    /**
     * @param iterator
     * @param pred
     * @return
     * @param <T>
     */
    public static <T> boolean exists(Iterator<T> iterator, Predicate<T> pred){
        while(iterator.hasNext()){
            T x = iterator.next();

            if(pred.predicate(x))
                return true;
        }
        return false;
    }

    /**
     * @param array
     * @param pred
     * @return
     * @param <T>
     */
    public static <T> T find(T[] array, Predicate<T> pred){
        final Iterator<T> iter = Arrays.stream(array).iterator();
        return find(iter, pred);
    }
    /**
     * @param iterable
     * @param pred
     * @return
     * @param <T>
     */
    public static <T> T find(Iterable<T> iterable, Predicate<T> pred){
        final Iterator<T> iter = iterable.iterator();
        return find(iter, pred);
    }

    /**
     * @param array
     * @param value
     * @return
     * @param <T>
     */
    public static <T> T find(T[] array, T value){
        final Iterator<T> iter = Arrays.stream(array).iterator();
        return find(iter, value);
    }

    /**
     *
     * @param iterable
     * @param value
     * @return
     * @param <T>
     */
    public static <T> T find(Iterable<T> iterable, T value){
        final Iterator<T> iter = iterable.iterator();
        return find(iter, value);
    }

    /**
     *
     * @param iterator
     * @param value
     * @return
     * @param <T>
     */
    public static <T> T find(Iterator<T> iterator, T value){
        final Predicate<T> pred = value::equals;
        return find(iterator, pred);
    }

    /**
     *
     * @param iterator
     * @param pred
     * @return
     * @param <T>
     */
    public static <T> T find(Iterator<T> iterator, Predicate<T> pred){
        while(iterator.hasNext()) {
            T x = iterator.next();

            if (pred.predicate(x)) {
                return x;
            }
        }
        return null;
    }

    /**
     *
     * @param array
     * @param page
     * @param pageSize
     * @param pred
     * @return
     * @param <T>
     */
    public static <T> List<T> paginate (T[] array, int page, int pageSize, Predicate<T> pred){
        Iterator<T> iterator = Arrays.stream(array).iterator();
        return paginate(iterator, page, pageSize, pred);
    }

    /**
     *
     * @param iterator
     * @param page
     * @param pageSize
     * @param pred
     * @return
     * @param <T>
     */
    public static <T> List<T> paginate (Iterator<T> iterator, int page, int pageSize, Predicate<T> pred){
        List<T> paginateList = new ArrayList<T>();
        int count = 0;
        int start = page*pageSize;
        int end = start + pageSize;

        while(iterator.hasNext()) {
            T check = iterator.next();
            paginateList.add(check);
            count++;
        }

        if(end > count){
            end = count;
        }

        return paginateList.subList(start, end);
    }

    /**
     *
     * @param iterable
     * @param page
     * @param pageSize
     * @param pred
     * @return
     * @param <T>
     */
    public static <T> List<T> paginate (Iterable<T> iterable, int page, int pageSize, Predicate<T> pred){
        Iterator<T> iterator = iterable.iterator();
        return paginate(iterator, page, pageSize, pred);
    }
}
