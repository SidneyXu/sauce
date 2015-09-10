package com.bookislife.sauce;

/**
 * Created by mrseasons on 2015/09/08.
 */
public abstract class SourceHandle {

    public static abstract class ProgressCallback {
        private Integer maxProgressSoFar = 0;

        public abstract boolean onProgress(int percentDone);

        boolean innerOnProgress(Integer percentDone) {
            if (percentDone > maxProgressSoFar) {
                maxProgressSoFar = percentDone;
                return onProgress(percentDone);
            }
            return true;
        }
    }

    protected boolean broadcastProgressIfNeeded(ProgressCallback progressCallback, int progress) {
        return progressCallback == null || progressCallback.innerOnProgress(progress);
    }
}
