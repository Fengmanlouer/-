package com.franz.easyweather.param

class ErrorCodeParam {
    companion object{
        const val NetworkError : Int = 1
        const val PostError : Int = 2
        const val JsonError : Int = 3
        const val ServiceError : Int = 4
        const val NullError : Int = 0
    }
}