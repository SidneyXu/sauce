package com.bookislife.sauce;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by SidneyXu on 2015/10/23.
 */
public class ProgressCallbackTest extends TestCase {

    public void testOnProgress() throws Exception {

        for (int i = 0; i < 200; i++) {
            final int j = i;
            SourceHandle.ProgressCallback progressCallback = new SourceHandle.ProgressCallback() {
                @Override
                public boolean onProgress(final int percentDone) {
                    assertThat(percentDone).isEqualTo(j)
                            .isLessThanOrEqualTo(200);
                    return true;
                }
            };
            boolean result = progressCallback.innerOnProgress(j);
            assertThat(result);
        }
    }

    public void testCancelProgress() throws Exception {
        for (int i = 0; i < 10; i++) {
            final int j = i;
            SourceHandle.ProgressCallback progressCallback = new SourceHandle.ProgressCallback() {
                @Override
                public boolean onProgress(final int percentDone) {
                    assertThat(percentDone).isEqualTo(j)
                            .isLessThanOrEqualTo(6);
                    return percentDone != 5;
                }
            };
            boolean result = progressCallback.innerOnProgress(j);
            if (!result) {
                break;
            }
        }
    }
}