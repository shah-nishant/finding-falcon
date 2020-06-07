package shah.nishant.findingfalcone.coroutines

import retrofit2.Response

sealed class Result<out T : Any?> {
    data class Success<out T : Any?>(val result: T) : Result<T>()
    data class Failure(val failure: String?) : Result<Nothing>()
    data class Error(val throwable: Throwable?) : Result<Nothing>()

    fun isSuccessful() = this is Success<*>
}

operator fun <T, R> Result<T>.plus(result: Result<R>): Result<Pair<T, R>> {
    return when {
        this is Result.Error -> Result.Error(this.throwable)
        result is Result.Error -> Result.Error(result.throwable)
        this is Result.Failure -> Result.Failure(this.failure)
        result is Result.Failure -> Result.Failure(result.failure)
        else -> Result.Success((this as Result.Success).result to (result as Result.Success).result)
    }
}

fun <T, R, X> Result<T>.mergeWith(result: Result<R>, transform: (T, R) -> X): Result<X> {
    return when {
        this is Result.Error -> Result.Error(this.throwable)
        result is Result.Error -> Result.Error(result.throwable)
        this is Result.Failure -> Result.Failure(this.failure)
        result is Result.Failure -> Result.Failure(result.failure)
        else -> Result.Success(
            transform(
                (this as Result.Success).result, (result as Result.Success).result
            )
        )
    }
}

fun <T> Result<T>.getValue(): T? {
    if (this is Result.Success) {
        return result
    }
    return null
}

fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> {
    return when (this) {
        is Result.Error -> this
        is Result.Failure -> this
        is Result.Success -> Result.Success(transform(result))
    }
}

fun <T> Response<T>.toResult(): Result<T> {
    return if (isSuccessful) {
        val body = body()
        if (body != null) {
            Result.Success(body)
        } else {
            Result.Failure(errorBody()?.string())
        }
    } else {
        Result.Failure(errorBody()?.string())
    }
}

fun Throwable.toResult(): Result<Nothing> = Result.Error(this)
