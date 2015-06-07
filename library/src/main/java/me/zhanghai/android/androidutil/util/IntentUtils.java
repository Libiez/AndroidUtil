/*
 * Copyright (c) 2014 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.android.androidutil.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

public class IntentUtils {

    private static final String MIME_TYPE_TEXT_PLAIN = "text/plain";

    private static final String MIME_TYPE_IMAGE_ANY = "image/*";

    public static Intent makeInstallShortcut(int iconRes, int nameRes, Class<?> intentClass,
                                             Context context) {
        return new Intent()
                .putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(context.getApplicationContext(),
                        intentClass))
                .putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(nameRes))
                .putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                        Intent.ShortcutIconResource.fromContext(context, iconRes));
    }

    public static Intent makeInstallShortcutWithAction(int iconRes, int nameRes,
                                                       Class<?> intentClass, Context context) {
        return makeInstallShortcut(iconRes, nameRes, intentClass, context)
                .setAction("com.android.launcher.action.INSTALL_SHORTCUT");
    }

    public static Intent makePickFile() {
        return new Intent(Intent.ACTION_GET_CONTENT)
                .setType("*/*")
                .addCategory(Intent.CATEGORY_OPENABLE);
    }

    // NOTE: Before Build.VERSION_CODES.JELLY_BEAN htmlText will be no-op.
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static Intent makeSendText(CharSequence text, String htmlText) {
        Intent intent = new Intent()
                .setAction(Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_TEXT, text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && htmlText != null) {
            intent.putExtra(Intent.EXTRA_HTML_TEXT, htmlText);
        }
        return intent.setType(MIME_TYPE_TEXT_PLAIN);
    }

    public static Intent makeSendText(CharSequence text) {
        return makeSendText(text, null);
    }

    public static Intent makeSendImage(Uri imageUri, CharSequence text) {
        return new Intent()
                .setAction(Intent.ACTION_SEND)
                // For maximum compatibility.
                .putExtra(Intent.EXTRA_TEXT, text)
                .putExtra(Intent.EXTRA_TITLE, text)
                .putExtra(Intent.EXTRA_SUBJECT, text)
                // HACK: WeChat circle respects this extra only.
                .putExtra("Kdescription", text)
                .putExtra(Intent.EXTRA_STREAM, imageUri)
                .setType(MIME_TYPE_IMAGE_ANY);
    }

    public static Intent makeView(Uri uri) {
        return new Intent(Intent.ACTION_VIEW, uri);
    }


    private IntentUtils() {}
}