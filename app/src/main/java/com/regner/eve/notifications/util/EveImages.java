package com.regner.eve.notifications.util;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;

import rx.Observable;
import rx.schedulers.Schedulers;

public final class EveImages {

    private static long H24 = 24l * 3600l * 1000l;
    private static final String IMAGES = "https://image.eveonline.com";

    private static final String IMAGE_CHARACTER = "%s/Character/%s_512.jpg";
    private static final String ICON_CHARACTER = "%s/Character/%s_128.jpg";

    public static void loadCharacterImage(final long ownerId, final ImageView intoView) {
        loadCharacterImage(intoView.getContext(), ownerId, intoView);
    }

    private static void loadCharacterImage(final Context context, final long ownerId, final ImageView... intoViews) {
        final BitmapRequestBuilder c = load(context, formatUrl(context, IMAGE_CHARACTER, ownerId), H24).dontAnimate();
        for (ImageView v: intoViews) {
            c.into(v);
        }
    }

    public static void loadCharacterIcon(final long ownerId, final ImageView intoView) {
        loadCharacterIcon(intoView.getContext(), ownerId, intoView);
    }

    private static void loadCharacterIcon(final Context context, final long ownerId, final ImageView... intoViews) {
        BitmapRequestBuilder c = load(context, formatUrl(context, ICON_CHARACTER, ownerId), H24).dontAnimate();
        for (ImageView v: intoViews) {
            c.into(v);
        }
    }

    public static String getCharacterImageURL(final Context context, final long charID) {
        return formatUrl(context, IMAGE_CHARACTER, charID);
    }

    public static String getCharacterIconURL(final Context context, final long charID) {
        return formatUrl(context, ICON_CHARACTER, charID);
    }

    private static String formatUrl(final Context context, final String urlConstant, final long typeID) {
        return String.format(urlConstant, IMAGES, Long.toString(typeID));
    }

    public static BitmapRequestBuilder load(final Fragment fragment, final String url) {
        return loadImpl(Glide.with(fragment), url);
    }

    public static BitmapRequestBuilder load(final Context context, final String url) {
        return loadImpl(glide(context), url);
    }

    private static BitmapRequestBuilder load(final Context context, final String url, final long expires) {
        return loadImpl(glide(context), url, expires);
    }

    public static void clear(final Context context) {
            Observable.just(Glide.get(context.getApplicationContext())).
            subscribeOn(Schedulers.io()).
            observeOn(Schedulers.io()).
            subscribe(Glide::clearDiskCache);
    }

    private static BitmapRequestBuilder loadImpl(final RequestManager glide, final String url) {
        return glide.load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    private static BitmapRequestBuilder loadImpl(final RequestManager glide, final String url, final long expires) {
        return loadImpl(glide, url).signature(new StringSignature(Long.toString(System.currentTimeMillis() / expires)));
    }

    private static RequestManager glide(final Context context) {
        RequestManager glide = null;
        if (context instanceof FragmentActivity) {
            glide = Glide.with((FragmentActivity)context);
        }
        else if (context instanceof Activity) {
            glide = Glide.with((Activity)context);
        }
        else {
            glide = Glide.with(context);
        }

        return glide;
    }

}
