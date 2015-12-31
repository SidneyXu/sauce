package com.bookislife.sauce.utils;

/**
 * Created by SidneyXu on 2015/12/31.
 */
public final class Pools {

    public static interface Pool<T> {

        public T acquire();

        public boolean release(T instance);
    }

    private Pools() {
    }

    public static class SimplePool<T> implements Pool<T> {
        private final Object[] pool;
        private int size;

        public SimplePool(int maxPoolSize) {
            if (maxPoolSize <= 0) {
                throw new IllegalArgumentException("The max pool size must be > 0");
            }
            pool = new Object[maxPoolSize];
        }

        @Override
        @SuppressWarnings("unchecked")
        public T acquire() {
            if (size > 0) {
                final int lastPooledIndex = size - 1;
                T instance = (T) pool[lastPooledIndex];
                pool[lastPooledIndex] = null;
                size--;
                return instance;
            }
            return null;
        }

        @Override
        public boolean release(T instance) {
            if (isInPool(instance)) {
                throw new IllegalStateException("Already in the pool!");
            }
            if (size < pool.length) {
                pool[size] = instance;
                size++;
                return true;
            }
            return false;
        }

        private boolean isInPool(T instance) {
            for (int i = 0; i < size; i++) {
                if (pool[i] == instance) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class SynchronizedPool<T> extends SimplePool<T> {
        private final Object lock = new Object();

        public SynchronizedPool(int maxPoolSize) {
            super(maxPoolSize);
        }

        @Override
        public T acquire() {
            synchronized (lock) {
                return super.acquire();
            }
        }

        @Override
        public boolean release(T element) {
            synchronized (lock) {
                return super.release(element);
            }
        }
    }
}

