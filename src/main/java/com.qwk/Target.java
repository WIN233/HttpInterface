package com.qwk;

/**
 * @author qiuwenke
 */
public interface Target<T> {
     Class<T> type();

     String url();

    public static class DefaultTarget<T> implements Target<T> {
        public DefaultTarget(Class<T> type) {
            this.type = type;
        }

        private Class<T> type;
        private String url;

        public Class<T> getType() {
            return type;
        }

        public void setType(Class<T> type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public Class<T> type() {
            return type;
        }

        @Override
        public String url() {
            return url;
        }
    }
}
