package com.health.vistacan.login.repo

object LoginRepository {
//    fun login(
//        baseURL: String,
//        username: String,
//        password: String,
//        StatusTypeID:String,
//        callback: OperationCallback<LoginResponse>,
//    ) {
//
//
//        ApiClient(baseURL).instance.Login_API(username, password,StatusTypeID)
//            .enqueue(object : Callback<LoginResponse> {
//                override fun onResponse(
//                    call: Call<LoginResponse>,
//                    response: Response<LoginResponse>
//                ) {
//                    when (response.code()) {
//                        200 -> callback.onSuccess(response.body()!!)
//                        422 -> callback.onErrorMsg(response.errorBody())
//                        401 -> callback.onError(R.string.invalid_login)
//                        else -> callback.onError(R.string.unknown_error)
//                    }
//                }
//
//                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                    if (t is SocketTimeoutException) {
//                        callback.onError(R.string.timed_out)
//                    } else {
//                        callback.onError(R.string.internet_connection)
//                    }
//                }
//            }
//            )
//
//    }
}