package com.example.mainactivity.data.response

import com.example.mainactivity.data.model.Result

data class BaseResponse(
    val status:String = "",
    val copyright:String = "",
    val num_results:Int = 0,
    val results: List<Result> = listOf(),
)