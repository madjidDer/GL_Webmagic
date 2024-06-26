package us.codecraft.webmagic;

/**
 * Listener of Spider on page processing. Used for monitor and such on.
 *
 * @author code4crafer@gmail.com
 * @since 0.5.0
 */
public interface SpiderListener {

    void onSuccess(Request request);

    default void onError(Request request, Exception e) {
        this.onError(request, e);
    }

}
