package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.librate.RateBuilder
import com.example.librate.callback.IClickBtn

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        var builder = RateBuilder(this)
            .setArrStar(
                intArrayOf(
                    R.drawable.ic_mstar_0,
                    R.drawable.ic_mstar_1,
                    R.drawable.ic_mstar_2,
                    R.drawable.ic_mstar_3,
                    R.drawable.ic_mstar_4,
                    R.drawable.ic_mstar_5
                )
            )
            .setTextTitle("Rate us")
            .setTextContent("Tap a star to set your rating")
            .setTextButton("Rate now", "Not now")
            .setTextTitleColor(Color.parseColor("#000000"))
            .setTextNotNowColor(Color.parseColor("#EDEDED"))
            .setDrawableButtonRate(R.drawable.border_rate)
            .setBackgroundDialog(R.drawable.border_bg_dialog)
            .setBackgroundStar(R.drawable.border_bg_star)
            .setColorRatingBar("#FAFF00")
            .setColorRatingBarBG("#E0E0E0")
            .setTextNotNowSize(12)
            .setNumberRateInApp(5)
            .setFontFamily(ResourcesCompat.getFont(this, R.font.poppins_regular))
            .setFontFamilyTitle(ResourcesCompat.getFont(this, R.font.poppins_semibold))
            .setOnclickBtn(object : IClickBtn {
                override fun onclickNotNow() {
                    Toast.makeText(this@MainActivity, "onclickNotNow", Toast.LENGTH_SHORT).show()
                }

                override fun onClickRate(rate: Float) {
                    Toast.makeText(this@MainActivity, rate.toString() + "", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onReviewAppSuccess() {
                    finishAffinity()
                }
            })
        builder.build()
        if ( builder.rateAppDiaLog!=null){
            builder.rateAppDiaLog?.show()
        }

    }
}