package com.example.themovieapp.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import com.example.themovieapp.R
import com.google.android.material.button.MaterialButton

class CustomButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet,
    style:Int=0
):MaterialButton(ContextThemeWrapper(context,R.style.customButton),attributeSet,style){
    init {
        height = resources.getDimension(R.dimen.dp_50).toInt()
    }
}