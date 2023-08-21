package com.example.librate

import android.app.Activity
import android.graphics.Typeface
import com.example.librate.callback.IClickBtn

class RateBuilder(val context: Activity) {
    var title: String? = null
        private set
    var content: String? = null
        private set
    var rateUs: String? = null
        private set
    var notNow: String? = null
        private set
    var titleColor = 0
        private set
    var contentColor = 0
        private set
    val rateUsDra = 0
    var rateUsColor = 0
        private set
    var notNowColor = 0
        private set
    var colorStart: String? = null
        private set
    var colorEnd: String? = null
        private set
    var titleSize = 0
        private set
    var contentSize = 0
        private set
    var rateNowSize = 0
        private set
    var notNowSize = 0
        private set
    var drawableRateUs = 0
        private set
    var onClickBtn: IClickBtn? = null
        private set
    val isExitApp = false
    var isRateInApp = true
        private set
    var numberRateInApp = 4
        private set
    var colorRatingBar: String? = null
        private set
    var colorRatingBarBg: String? = null
        private set
    var typeface: Typeface? = null
        private set
    var typefaceTitle: Typeface? = null
        private set
    var typefaceContent: Typeface? = null
        private set
    var typefaceRateUs: Typeface? = null
        private set
    var typefaceNotNow: Typeface? = null
        private set
    var drawableDialog = 0
        private set
    var drawableBgStar = 0
        private set
    var numberRateDefault = 5
        private set
    var rateAppDiaLog: RateAppDiaLog? = null
    var arrStar = intArrayOf(
        R.drawable.ic_star_0,
        R.drawable.ic_star_1,
        R.drawable.ic_star_2,
        R.drawable.ic_star_3,
        R.drawable.ic_star_4,
        R.drawable.ic_star_5
    )
        private set

    fun setTextTitle(title: String?): RateBuilder {
        this.title = title
        return this
    }

    fun setTextContent(content: String?): RateBuilder {
        this.content = content
        return this
    }

    fun setTextButton(rateUs: String?, notNow: String?): RateBuilder {
        this.rateUs = rateUs
        this.notNow = notNow
        return this
    }

    fun setRateInApp(isRateInApp: Boolean): RateBuilder {
        this.isRateInApp = isRateInApp
        return this
    }

    fun setTextTitleColorLiner(colorStart: String?, colorEnd: String?): RateBuilder {
        this.colorStart = colorStart
        this.colorEnd = colorEnd
        return this
    }

    fun setDrawableButtonRate(drawable: Int): RateBuilder {
        drawableRateUs = drawable
        return this
    }

    fun setTextTitleColor(color: Int): RateBuilder {
        titleColor = color
        return this
    }

    fun setTextRateUsColor(color: Int): RateBuilder {
        rateUsColor = color
        return this
    }

    fun setTextNotNowColor(color: Int): RateBuilder {
        notNowColor = color
        return this
    }

    fun setTextTitleSize(titleSize: Int): RateBuilder {
        this.titleSize = titleSize
        return this
    }

    fun setTextContentSize(contentSize: Int): RateBuilder {
        this.contentSize = contentSize
        return this
    }

    fun setTextRateSize(rateSize: Int): RateBuilder {
        rateNowSize = rateSize
        return this
    }

    fun setTextNotNowSize(notNSize: Int): RateBuilder {
        notNowSize = notNSize
        return this
    }

    fun setTextContentColor(color: Int): RateBuilder {
        contentColor = color
        return this
    }

    fun setColorRatingBar(color: String?): RateBuilder {
        colorRatingBar = color
        return this
    }

    fun setColorRatingBarBG(color: String?): RateBuilder {
        colorRatingBarBg = color
        return this
    }

    fun setOnclickBtn(onClickBtn: IClickBtn?): RateBuilder {
        this.onClickBtn = onClickBtn
        return this
    }

    /* public Builder setExitApp(Boolean isExitApp) {
         this.isExitApp = isExitApp;
         return this;
     }*/
    fun setNumberRateInApp(numberRate: Int): RateBuilder {
        numberRateInApp = numberRate
        return this
    }

    fun setFontFamily(typeface: Typeface?): RateBuilder {
        this.typeface = typeface
        return this
    }

    fun setFontFamilyTitle(typeface: Typeface?): RateBuilder {
        typefaceTitle = typeface
        return this
    }

    fun setFontFamilyContent(typeface: Typeface?): RateBuilder {
        typefaceContent = typeface
        return this
    }

    fun setFontFamilyRateUs(typeface: Typeface?): RateBuilder {
        typefaceRateUs = typeface
        return this
    }

    fun setFontFamilyNotNow(typeface: Typeface?): RateBuilder {
        typefaceNotNow = typeface
        return this
    }

    fun setBackgroundDialog(drawable: Int): RateBuilder {
        drawableDialog = drawable
        return this
    }

    fun setBackgroundStar(drawable: Int): RateBuilder {
        drawableBgStar = drawable
        return this
    }

    fun setNumberRateDefault(number: Int): RateBuilder {
        numberRateDefault = number
        return this
    }

    fun setArrStar(arr: IntArray): RateBuilder {
        if (arr.size == arrStar.size) {
            arrStar = arr
        }
        return this
    }

    fun build(): RateAppDiaLog? {
        rateAppDiaLog = RateAppDiaLog(context, this)
        return rateAppDiaLog
    }
}