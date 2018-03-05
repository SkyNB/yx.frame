package com.lnet.frame.core;

import com.lnet.frame.util.ExceptionHelper;

import java.util.List;
import java.util.function.Supplier;

public final class ResponseBuilder {

    public static <E> Response<E> fail(String message) {
        return new Response<E>()
                .setMessage(message)
                .setSuccess(false);
    }

    public static <E> Response<E> fail(Exception ex) {
        return new Response<E>()
                .setMessage(ExceptionHelper.getJoinedExceptionMessageChain(ex))
                .setSuccess(false);
    }

    public static <E> Response<E> success() {
        return new Response<E>().setSuccess(true);
    }

    public static <E> Response<E> success(E body) {
        return new Response<>(body);
    }

    public static <E> Response<E> success(E body, String message) {
        return new Response<>(body)
                .setMessage(message)
                .setSuccess(true);
    }

    //region list
    public static <E> ListResponse<E> list(List<E> body) {
        return new ListResponse<>(body);
    }

    public static <E> ListResponse<E> list(List<E> body, boolean success, String message) {
        return new ListResponse<>(body, success, message);
    }

    public static <E> ListResponse<E> listFail(String message) {
        return new ListResponse<E>().setMessage(message).setSuccess(false);
    }

    public static <E> ListResponse<E> listFail(Exception ex) {
        return new ListResponse<E>().setMessage(ExceptionHelper.getJoinedExceptionMessageChain(ex))
                .setSuccess(false);
    }
    //endregion

    //region page
    public static <E> PageResponse<E> page(List<E> body, long page, int pageSize, long totalItemCount) {
        return new PageResponse<>(body, page, pageSize, totalItemCount);
    }

    public static <E> PageResponse<E> pageFail(String message) {
        return new PageResponse<E>().setMessage(message).setSuccess(false);
    }

    public static <E> PageResponse<E> pageFail(Exception ex) {
        return new PageResponse<E>().setMessage(ExceptionHelper.getJoinedExceptionMessageChain(ex))
                .setSuccess(false);
    }
    //endregion

    public static <E> Response<E> supply(Supplier<E> supplier) {
        try {
            return success(supplier.get());
        } catch (Exception ex) {
            return fail(ExceptionHelper.getJoinedExceptionMessageChain(ex));
        }
    }

}
