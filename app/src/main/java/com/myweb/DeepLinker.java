package com.myweb;


import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;

public class DeepLinker {

    private static final String KEY_LINK = "link";
    private static final String KEY_ID = "id";
    private static final String AUTHORITY = "*";
    private static final String PATH_ID = "/*";

    public static enum Link {
        HOME(null),
        PROFILE("profile"),
        PROFILE_OTHER("profile" + PATH_ID),
        SETTINGS("settings");

        final String path;

        private Link(String path) {
            this.path = path;
        }
    }

    public static Link getLinkFromBundle(Bundle bundle) {
        Link link = null;
        int val = bundle.getInt(KEY_LINK, -1);
        if (val > -1 && val < Link.values().length) {
            link = Link.values()[val];
        }
        return link;
    }

    public static long getIdFromBundle(Bundle bundle) {
        return bundle.getLong(KEY_ID);
    }

    private final UriMatcher mUriMatcher;

    public DeepLinker() {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Add all links to the matcher
        for (Link link : Link.values()) {
            mUriMatcher.addURI(AUTHORITY, link.path, link.ordinal());
        }
    }

    /**
     * Builds a bundle from the given URI
     */
    public Bundle buildBundle(Uri uri) {
        int code = mUriMatcher.match(uri);

        // Default to home
        Link link = Link.HOME;

        if (code == UriMatcher.NO_MATCH) {
            // Revert code to match default link
            code = link.ordinal();
        } else {
            // Update default link with the matched one
            link = Link.values()[code];
        }

        Bundle bundle = new Bundle();
        bundle.putInt(KEY_LINK, code);

        switch (link) {
            case HOME:
                break;
            case PROFILE:
                break;
            case PROFILE_OTHER:
                // Capture the profile ID
                bundle.putLong(KEY_ID, Long.valueOf(uri.getLastPathSegment()));
                break;
            case SETTINGS:
                break;
        }

        return bundle;
    }
}
