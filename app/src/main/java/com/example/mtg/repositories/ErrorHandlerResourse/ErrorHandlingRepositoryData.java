package com.example.mtg.repositories.ErrorHandlerResourse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class ErrorHandlingRepositoryData<T> {

    @NonNull
    public final Status status;
    @Nullable public final T data;
    @Nullable public final String message;

    private ErrorHandlingRepositoryData(@NonNull Status status, @Nullable T data,
                                        @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> ErrorHandlingRepositoryData<T> success(@NonNull T data) {
        return new ErrorHandlingRepositoryData<>(Status.SUCCESS, data, null);
    }

    public static <T> ErrorHandlingRepositoryData<T> error(String msg, @Nullable T data) {
        return new ErrorHandlingRepositoryData<>(Status.ERROR, data, msg);
    }

    public static <T> ErrorHandlingRepositoryData<T> loading(@Nullable T data) {
        return new ErrorHandlingRepositoryData<>(Status.LOADING, data, null);
    }


    public enum Status { SUCCESS, ERROR, LOADING }

}
