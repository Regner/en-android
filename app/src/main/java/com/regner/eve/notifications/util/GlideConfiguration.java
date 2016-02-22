package com.regner.eve.notifications.util;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

import org.apache.commons.lang.StringUtils;

import java.io.File;

//PLEASE FOXFOUR FIX THE IMAGE SERVER RESPONSES
//1 - Add cache directives
//2 - Don't return image "1" when not found; return a 404.
public final class GlideConfiguration implements GlideModule {

    static class EveDiskCache implements DiskCache {
        private final DiskCache delegate;

        public EveDiskCache(DiskCache delegate) {
            this.delegate = delegate;
        }

        @Override
        public File get(Key key) {
            return this.delegate.get(key);
        }

        @Override
        public void put(Key key, Writer writer) {
            final String id = idOf(key);
            if ("1".equals(id)) {
                return;
            }
            this.delegate.put(key, writer);
        }

        @Override
        public void delete(Key key) {
            this.delegate.delete(key);
        }

        @Override
        public void clear() {
            this.delegate.clear();
        }

        //FIXME this is horrible...using toString()? Am I serious?
        private static String idOf(final Key key) {
            final String url = StringUtils.substringBetween(key.toString(), "EngineKey{", "+");
            return StringUtils.substringBefore(StringUtils.substringAfterLast(url, "/"), "_");
        }
    }

    static class EveDiskCacheFactory implements DiskCache.Factory {
        private final DiskCache.Factory delegate;

        public EveDiskCacheFactory(DiskCache.Factory delegate) {
            this.delegate = delegate;
        }

        @Override
        public DiskCache build() {
            return new EveDiskCache(this.delegate.build());
        }
    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        builder.setDiskCache(new EveDiskCacheFactory(new InternalCacheDiskCacheFactory(context)));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
