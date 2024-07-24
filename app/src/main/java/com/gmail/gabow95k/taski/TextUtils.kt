package com.gmail.gabow95k.taski

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat

fun Context.generateWelcomeMessage(): SpannableString {
    val randomName = generateRandomName(this)
    val welcomeText = getString(R.string.welcome_user_prompt, randomName)
    val spannableString = SpannableString(welcomeText)

    val blackColor = ContextCompat.getColor(this, R.color.purple)
    val blueColor = ContextCompat.getColor(this, R.color.primaryColor)

    val blackColorSpan = ForegroundColorSpan(blackColor)
    spannableString.setSpan(blackColorSpan, 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    val blueColorSpan = ForegroundColorSpan(blueColor)
    spannableString.setSpan(
        blueColorSpan,
        9,
        9 + randomName.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    spannableString.setSpan(
        blackColorSpan,
        spannableString.length - 1,
        spannableString.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    return spannableString
}

private fun generateRandomName(context: Context): String {
    val names = listOf(
        context.getString(R.string.tiger_optimist),
        context.getString(R.string.shy_elephant),
        context.getString(R.string.melancholic_dolphin),
        context.getString(R.string.philosophical_leopard),
        context.getString(R.string.musical_gorilla),
        context.getString(R.string.dreaming_kangaroo),
        context.getString(R.string.prudent_giraffe),
        context.getString(R.string.adventurous_penguin),
        context.getString(R.string.astute_rhino),
        context.getString(R.string.curious_panda)
    )
    return names.random()
}