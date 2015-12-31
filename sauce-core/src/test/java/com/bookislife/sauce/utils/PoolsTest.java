package com.bookislife.sauce.utils;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by SidneyXu on 2015/12/31.
 */
public class PoolsTest extends TestCase {

    public void testAcquire() {
        int poolSize = 5;
        int releasedSize = 10;
        int acquireSize = 100;
        Pools.SimplePool<View> pool = new Pools.SimplePool<>(poolSize);
        for (int i = 0; i < releasedSize; i++) {
            View view = new View("view " + i);
            pool.release(view);
        }

        for (int i = 0; i < acquireSize; i++) {
            View view = pool.acquire();
            assertThat(view.name).isIn("view 0", "view 1", "view 2", "view 3", "view 4");
        }
    }

    class View {
        public String name;

        public View(String name) {
            this.name = name;
        }

    }
}