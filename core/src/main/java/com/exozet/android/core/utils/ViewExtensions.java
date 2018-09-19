package com.exozet.android.core.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import static android.os.Build.VERSION.SDK_INT;
import static com.exozet.android.core.utils.BitmapExtensions.getBitmap;
import static com.exozet.android.core.utils.DeviceExtensions.hideKeyboard;
import static com.exozet.android.core.utils.MathExtensions.dpToPx;
import static com.exozet.android.core.utils.ResourceExtensions.color;
import static kotlin.math.MathKt.roundToInt;
import static net.kibotu.ContextHelper.getActivity;
import static net.kibotu.ContextHelper.getContext;

/**
 * Created by Jan Rabe on 24/09/15.
 */
final public class ViewExtensions {

    private ViewExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static void setOneTimeGlobalLayoutListener(@NonNull final View v, @NonNull final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (SDK_INT >= 16)
                    v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                else
                    v.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                onGlobalLayoutListener.onGlobalLayout();

            }
        });
    }

    public static void flipHorizontically(@NonNull final ImageView imageView) {
        imageView.setImageBitmap(BitmapExtensions.flipHorizontally(getBitmap(imageView)));
    }

    public static void setLayoutHeight(@NonNull final View v, final int height) {
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
        params.height = height;
        v.setLayoutParams(params);
    }

    public static void getLocationOnScreen(@Nullable final View view, @Nullable final Rect src, @Nullable Rect dst) {
        if (view == null || src == null)
            return;

        if (dst == null)
            dst = new Rect();

        final int[] location = new int[2];
        view.getLocationOnScreen(location);
        final int offsetX = location[0];
        final int offsetY = location[1];

        dst.set(src.left + offsetX, src.top + offsetY, src.right + offsetX,
                src.bottom + offsetY);
    }


    public static void setFont(@Nullable final Typeface font, @Nullable final ArrayList<View> views) {
        if (font == null)
            return;

        if (views == null)
            return;

        final List<TextView> textViews = new ArrayList<>();
        for (final View view : views) {
            if (view instanceof TextView)
                textViews.add((TextView) view);
        }

        for (final TextView v : textViews)
            v.setTypeface(font);
    }

    @NonNull
    public static int[] getScreenLocation(@NonNull final View view) {
        final int[] locations = new int[2];
        view.getLocationOnScreen(locations);
        return locations;
    }

    public static void moveViewRelatively(@NonNull final View view, final int left, final int top) {
        final int[] location = getScreenLocation(view);
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(left + location[0], top + location[1], 0, 0);
        final ViewGroup.LayoutParams p = view.getLayoutParams();
        params.width = p.width;
        params.height = p.height;
        view.setLayoutParams(params);
    }

    public static void moveView(@NonNull final View view, final int left, final int top) {
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(left, top, 0, 0);
        final ViewGroup.LayoutParams p = view.getLayoutParams();
        params.width = p.width;
        params.height = p.height;
        view.setLayoutParams(params);
    }

    public static void setFont(@NonNull final Typeface font, @NonNull final TextView... views) {
        for (final TextView v : views)
            v.setTypeface(font);
    }

    public static void addMargin(@NonNull final View view, final int left, final int top, final int right, final int bottom) {
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(left, top, right, bottom);
        view.setLayoutParams(layoutParams);
    }

    @NonNull
    public static Drawable getScaledDrawable(@DrawableRes final int resourceId, final int scaleInDp) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        final DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        options.inScreenDensity = metrics.densityDpi;
        options.inTargetDensity = metrics.densityDpi;
        options.inDensity = DisplayMetrics.DENSITY_DEFAULT;
        final int px = roundToInt(dpToPx(scaleInDp));
        final Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), resourceId, options);
        final BitmapDrawable drawable = new BitmapDrawable(getContext().getResources(), Bitmap.createScaledBitmap(bitmap, px, px, true));
        Log.v("Scaling", "Scaling image [" + scaleInDp + "dp to " + px + "px] => [" + bitmap.getWidth() + "x" + bitmap.getHeight() + " px] to [" + drawable.getIntrinsicWidth() + "x" + drawable.getIntrinsicHeight() + " px]");
        bitmap.recycle();
        return drawable;
    }

    public static View getContentRoot() {
        return getActivity()
                .getWindow()
                .getDecorView()
                .findViewById(android.R.id.content);
    }

    public static void addHtmlContentToTextView(@RawRes final int resourceId, @NonNull final TextView view) {
        final InputStream inputStream = getContext().getResources().openRawResource(resourceId);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        final StringBuffer body = new StringBuffer();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
            reader.close();
        } catch (@NonNull final IOException e) {
            e.printStackTrace();
        }
        view.setText(Html.fromHtml(body.toString()));
        view.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @NonNull
    public static Rect getLocationOnScreen(@NonNull final View textView) {
        final Rect rect = new Rect();
        final int[] location = new int[2];

        textView.getLocationOnScreen(location);

        rect.left = location[0];
        rect.top = location[1];
        rect.right = location[0] + textView.getWidth();
        rect.bottom = location[1] + textView.getHeight();

        return rect;
    }

    public static void hideOnLostFocus(@NonNull final MotionEvent event, @Nullable final View... views) {

        if (views == null)
            return;

        boolean hit = false;

        for (final View view : views)
            hit |= getLocationOnScreen(view).contains((int) event.getX(), (int) event.getY());

        if (event.getAction() == MotionEvent.ACTION_DOWN && !hit)
            hideKeyboard(views[0]);
    }

    public static void sendEmail(final String address, final String subject, final Spanned body, final int requestCode, final String popupTitle) {
        final Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", address, null));
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, body);
        try {
            getActivity().startActivityForResult(Intent.createChooser(i, popupTitle), requestCode);
        } catch (@NonNull final ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void showMarket() {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        final String packageName = getContext().getApplicationContext().getPackageName();
        intent.setData(Uri.parse("market://details?id=" + packageName));
        getContext().startActivity(intent);
    }

    public static void setViewBackgroundWithoutResettingPadding(@NonNull final View v, @DrawableRes final int backgroundResId) {
        final int paddingBottom = v.getPaddingBottom(), paddingLeft = v.getPaddingLeft();
        final int paddingRight = v.getPaddingRight(), paddingTop = v.getPaddingTop();
        v.setBackgroundResource(backgroundResId);
        v.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public static void setViewBackgroundColorWithoutResettingPadding(@NonNull final View v, @ColorRes final int color) {
        final int paddingBottom = v.getPaddingBottom(), paddingLeft = v.getPaddingLeft();
        final int paddingRight = v.getPaddingRight(), paddingTop = v.getPaddingTop();
        v.setBackgroundColor(v.getResources().getColor(color));
        v.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }


    public static void addFadeOutToText(@Nullable final TextView textView, final int galleryHeight, @ColorRes final int fromColor, @ColorRes final int toColor) {
        if (textView == null) {
            return;
        }

        final Resources resources = textView.getContext().getResources();
        final Rect bounds = new Rect();
        final Paint textPaint = textView.getPaint();
        final CharSequence text = textView.getText();
        textPaint.getTextBounds(text.toString(), 0, text.length(), bounds);
        final int viewWidth = textView.getWidth();
        final int startHeight = textView.getLineCount() > 2 ? galleryHeight / 2 : 0;
        final int availableWidth = (int) (viewWidth - textView.getPaddingLeft() - textView.getPaddingRight() - textPaint.measureText(text.toString()));
        if (availableWidth < 0) {
            final Shader textShader = new LinearGradient(3 * viewWidth / 4, startHeight, viewWidth, textView.getPaint().getTextSize(),
                    new int[]{resources.getColor(fromColor), resources.getColor(toColor)},
                    null, Shader.TileMode.CLAMP);
            textView.getPaint().setShader(textShader);
        }
    }

    public static void setLayoutSize(@NonNull final View v, final int width, final int height) {
        final ViewGroup.LayoutParams params = v.getLayoutParams();
        params.width = width;
        params.height = height;
        v.post(() -> v.setLayoutParams(params));
    }

    public static void addContextMenu(@NonNull final View view, @NonNull final Fragment fragment) {
        fragment.registerForContextMenu(view);
        view.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                hideKeyboard(v);
                fragment.getActivity().openContextMenu(v);
            }
        });
        view.setOnClickListener(v -> fragment.getActivity().openContextMenu(v));
    }

    public static void focusView(@NonNull final View v, @NonNull final Fragment fragment) {
        v.requestFocus();
        final InputMethodManager imm = (InputMethodManager) fragment.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static View getContentRoot(@NonNull final Activity context) {
        return context
                .getWindow()
                .getDecorView()
                .findViewById(android.R.id.content);
    }

    public static void addLinearLayoutMargin(@NonNull final View view, final int left, final int top, final int right, final int bottom) {
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(left, top, right, bottom);
        view.setLayoutParams(layoutParams);
    }

    public static void setRelativeLayoutHeight(@NonNull final View v, final int height) {
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
        params.height = height;
        v.setLayoutParams(params);
    }

    public static void smoothScroll(@Nullable final RecyclerView scrollableParent, final int dx, final int dy) {
        if ((dx == 0 && dy == 0) || scrollableParent == null) {
            return;
        }
        scrollableParent.post(() -> scrollableParent.smoothScrollBy(dx, dy));
    }

    public static void colorize(@NonNull final TextView text, @ColorRes final int color) {
        text.setTextColor(color(color));
    }

    @Nullable
    public static <T extends View> T setColor(@Nullable final T view, @ColorRes final int color) {
        if (view == null)
            return view;
        view.setBackgroundColor(getContext().getResources().getColor(color));
        return view;
    }

    public static void setLayoutMargin(@NonNull final View view, final int left, final int top, final int right, final int bottom) {
        final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(left, top, right, bottom);
            view.setLayoutParams(layoutParams);
        }
    }

    public static View inflate(@LayoutRes final int layout, @Nullable final ViewGroup parent, final boolean attachToRoot) {
        return getActivity().getLayoutInflater().inflate(layout, parent, attachToRoot);
    }

    public static View inflate(@LayoutRes final int layout, @Nullable final ViewGroup parent) {
        return getActivity().getLayoutInflater().inflate(layout, parent, false);
    }

    public static void clearFocus() {
        final View currentFocus = getActivity().getCurrentFocus();
        if (currentFocus != null)
            currentFocus.clearFocus();
    }

    public static void colorizeProgressBar(@NonNull final ProgressBar progressBar, @ColorRes final int progressDrawable, @ColorRes final int indeterminateDrawable) {
        progressBar.getProgressDrawable().setColorFilter(color(progressDrawable), PorterDuff.Mode.SRC_IN);
        progressBar.getIndeterminateDrawable().setColorFilter(color(indeterminateDrawable), PorterDuff.Mode.SRC_IN);
    }

    public static void setSize(@NonNull final View searchIcon, final int widthInDp, final int heightInDp) {
        final ViewGroup.LayoutParams params = searchIcon.getLayoutParams();
        params.width = dpToPx(widthInDp);
        params.height = dpToPx(heightInDp);
        searchIcon.setLayoutParams(params);
    }

    public static long getDuration(String videoUrl) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            mmr.setDataSource(videoUrl, new HashMap<String, String>());
        } else {
            mmr.setDataSource(videoUrl);
        }
        return Long.parseLong(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
    }

    public static void changeStatusBarColorRes(@ColorRes final int color) {
        changeStatusBarColor(color(color));
    }

    public static void changeStatusBarColor(@ColorInt final int color) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            return;

        final Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(color);
    }

    public static int getBackgroundColor(@Nullable final View view) {
        if (view != null && view.getBackground() instanceof ColorDrawable)
            return ((ColorDrawable) view.getBackground()).getColor();

        return color(android.R.color.transparent);
    }

    public static void forceRipple(View view) {
        forceRipple(view, (int) (view.getX() - view.getWidth() / 2f), (int) (view.getY() - view.getHeight() / 2f));
    }

    /**
     * see <a href="http://stackoverflow.com/a/38242102">how-to-trigger-ripple-effect-on-android-lollipop-in-specific-location</a>
     */
    public static void forceRipple(View view, int x, int y) {
        Drawable background = view.getBackground();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && background instanceof RippleDrawable) {
            background.setHotspot(x, y);
        }
        view.setPressed(true);
        // For a quick ripple, you can immediately set false.
        view.setPressed(false);
    }

    @Nullable
    public static Drawable scaleImage(@Nullable Drawable image, float scaleFactor) {

        if ((image == null) || !(image instanceof BitmapDrawable)) {
            return image;
        }

        Bitmap b = ((BitmapDrawable) image).getBitmap();

        int sizeX = Math.round(image.getIntrinsicWidth() * scaleFactor);
        int sizeY = Math.round(image.getIntrinsicHeight() * scaleFactor);

        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, sizeX, sizeY, false);

        image = new BitmapDrawable(getContext().getResources(), bitmapResized);

        return image;

    }

    public static void setEditTextUnderlineColor(EditText editText, @ColorRes int colorId) {
        editText.getBackground().setColorFilter(color(colorId), PorterDuff.Mode.SRC_IN);
    }
}