package com.ab.github_api_pq.network.retrofit.repo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface OnNetworkResponse<T> {
    void success(@NonNull final T success);

    void error(@Nullable final Throwable throwable);
}
