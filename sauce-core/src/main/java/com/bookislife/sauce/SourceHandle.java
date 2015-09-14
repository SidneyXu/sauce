package com.bookislife.sauce;

/**
 * The class represents a util that is used to handle the specified source.
 * <p/>
 *
 * @author SidneyXu
 */
public abstract class SourceHandle {

    /**
     * The class is called when a progress operation is needed.
     *
     * @author SidneyXu
     */
    public static abstract class ProgressCallback {
        private Integer maxProgressSoFar = 0;

        /**
         * Overrides this when the progress has changed.
         *
         * @param percentDone the current percent done
         * @return True if should continue process. False otherwise.
         */
        public abstract boolean onProgress(int percentDone);

        boolean innerOnProgress(Integer percentDone) {
            if (percentDone > maxProgressSoFar) {
                maxProgressSoFar = percentDone;
                return onProgress(percentDone);
            }
            return true;
        }
    }

    /**
     * Call this when want to change the current progress..
     *
     * @param progressCallback progressCallback.onProgress() is called if not null
     * @param progress         the current percent
     * @return True if should continue process. False otherwise.
     */
    protected boolean broadcastProgressIfNeeded(ProgressCallback progressCallback, int progress) {
        return progressCallback == null || progressCallback.innerOnProgress(progress);
    }
}
