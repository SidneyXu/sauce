package com.bookislife.sauce;


import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by SidneyXu on 2015/09/11.
 */
public class SourceHandleTest extends TestCase {

    public void testBroadcastProgressIfNeeded() throws Exception {
        SourceHandle sourceHandle = new SourceHandle() {
            @Override
            protected boolean broadcastProgressIfNeeded(final ProgressCallback progressCallback,
                                                        final int progress) {
                return super.broadcastProgressIfNeeded(progressCallback, progress);
            }
        };
        sourceHandle.broadcastProgressIfNeeded(new SourceHandle.ProgressCallback() {
            @Override
            public boolean onProgress(final int percentDone) {
                assertThat(percentDone).isEqualTo(1);
                return true;
            }
        }, 1);

    }
}