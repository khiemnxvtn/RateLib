package com.example.librate

import android.animation.ObjectAnimator
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRatingBar
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task


class RateAppDiaLog(var context: Activity, var builder: RateBuilder) : Dialog(context) {
    private var tvTitle: TextView? = null
    private var tvContent: TextView? = null
    private var btnRate: TextView? = null
    private var  btnNotnow: TextView? = null
    private var imgRate: ImageView? = null
    private var rtb: AppCompatRatingBar? = null
    private var dialog: LinearLayout? = null
    private var bg_star: RelativeLayout? = null
    private var isZoomedIn = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_rate)
        window!!.setLayout(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        initView()
    }

    private fun initView() {
        tvTitle = findViewById(R.id.tvTitle);
        tvContent = findViewById(R.id.tvContent);
        imgRate = findViewById(R.id.imgRate);
        rtb = findViewById(R.id.rtb);
        btnRate = findViewById(R.id.btnRate);
        btnNotnow = findViewById(R.id.btnNotnow);
        dialog = findViewById(R.id.dialog);
        bg_star = findViewById(R.id.bg_star);
        if (builder.title != null) tvTitle?.text = builder.title
        if (builder.content != null) tvContent?.text = builder.content
        if (builder.titleColor != 0) tvTitle?.setTextColor(builder.titleColor)
        if (builder.contentColor != 0) tvContent?.setTextColor(builder.contentColor)
        if (builder.rateUsColor != 0) {
            btnRate?.setTextColor(builder.rateUsColor)
        }
        if (builder.notNowColor != 0) {
             btnNotnow?.setTextColor(builder.notNowColor)
        }
        if (builder.colorStart != null && builder.colorEnd != null) {
            val paint = tvTitle?.paint
            val width = paint?.measureText(tvTitle?.text.toString())
            val textShader: Shader = LinearGradient(
                0f, 0f, width!!, tvTitle?.textSize!!.toFloat(), intArrayOf(
                    Color.parseColor(builder.colorStart),
                    Color.parseColor(builder.colorEnd)
                ), null, Shader.TileMode.CLAMP
            )
            tvTitle!!.paint.shader = textShader
        }
        if (builder.titleSize != 0) {
            tvTitle?.textSize = builder.titleSize.toFloat()
        }
        if (builder.rateNowSize != 0) {
            btnRate?.textSize = builder.rateNowSize.toFloat()
        }
        if (builder.notNowSize != 0) {
             btnNotnow?.textSize = builder.notNowSize.toFloat()
        }
        if (builder.notNow != null && builder.rateUs != null) {
            btnRate?.text = builder.rateUs
            btnNotnow?.text = builder.notNow
        }
        if (builder.drawableRateUs != 0) {
            btnRate?.setBackgroundResource(builder.drawableRateUs)
        }
        if (builder.contentSize != 0) {
            tvContent?.textSize = builder.contentSize.toFloat()
        }
        if (builder.typeface != null) {
            tvTitle?.typeface = builder.typeface
            tvContent?.typeface = builder.typeface
            btnRate?.typeface = builder.typeface
            btnNotnow?.typeface = builder.typeface
        }
        if (builder.typefaceTitle != null) {
            tvTitle?.typeface = builder.typefaceTitle
        }
        if (builder.typefaceContent != null) {
            tvContent?.typeface = builder.typefaceContent
        }
        if (builder.typefaceRateUs != null) {
            btnRate?.typeface = builder.typefaceRateUs
        }
        if (builder.typefaceNotNow != null) {
            btnNotnow?.typeface = builder.typefaceNotNow
        }
        if (builder.drawableDialog != 0) {
            dialog?.setBackgroundResource(builder.drawableDialog)
        }
        if (builder.drawableBgStar != 0) {
            bg_star?.setBackgroundResource(builder.drawableBgStar)
        }
         btnNotnow?.setOnClickListener(View.OnClickListener {
             builder.onClickBtn!!.onclickNotNow()
            dismiss()
        })
        btnRate?.setOnClickListener(View.OnClickListener {
            builder.onClickBtn!!.onClickRate(rtb!!.rating)
            if (rtb?.rating!! >= builder.numberRateInApp) {
                if (builder.isRateInApp) {
                    reviewApp(context)
                } else {
                    dismiss()
                }
            } else {
                if (rtb!!.rating > 0) {
                    dismiss()
                }
            }
        })
        changeRating()
        if (builder.colorRatingBar != null) rtb?.progressTintList = ColorStateList.valueOf(
            Color.parseColor(
                builder.colorRatingBar
            )
        )
        if (builder.colorRatingBarBg != null) rtb?.progressBackgroundTintList = ColorStateList.valueOf(
            Color.parseColor(builder.colorRatingBarBg)
        )
        if (builder.numberRateDefault in 1..5) {
            rtb?.rating = builder.numberRateDefault.toFloat()
        }
    }

    fun reviewApp(context: Context) {
        val manager: ReviewManager = ReviewManagerFactory.create(context)
        val request: com.google.android.play.core.tasks.Task<com.google.android.play.core.review.ReviewInfo> =
            manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo: ReviewInfo = task.result
                val flow: Task<Void> =
                    manager.launchReviewFlow(context as Activity, reviewInfo)
                flow.addOnCompleteListener {
                    builder.onClickBtn?.onReviewAppSuccess()
                    dismiss()
                }
            } else {
                Log.e("ReviewError", "" + task.exception.toString())
            }
        }
    }

    fun changeRating() {
        rtb!!.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                val getRating = rtb!!.rating.toString()
                animationStar()
                when (getRating) {
                    "1.0" -> imgRate!!.setImageResource(builder.arrStar[1])
                    "2.0" -> imgRate!!.setImageResource(builder.arrStar[2])
                    "3.0" -> imgRate!!.setImageResource(builder.arrStar[3])
                    "4.0" -> imgRate!!.setImageResource(builder.arrStar[4])
                    "5.0" -> imgRate!!.setImageResource(builder.arrStar[5])
                    else -> {
                        rtb!!.rating = 1f
                        imgRate!!.setImageResource(builder.arrStar[0])
                    }
                }
            }
    }

    private fun animationStar() {
        if (!isZoomedIn) {
            animateZoomIn()
        }
    }

    private fun animateRotation() {
        val rotationAnimator = ObjectAnimator.ofFloat(imgRate, "rotation", 0f, 360f)
        rotationAnimator.duration = 500 // Animation duration in milliseconds
        rotationAnimator.interpolator =
            LinearInterpolator() // Use a linear interpolator for constant speed
        rotationAnimator.start()
        animateZoomOut()
    }

    private fun animateZoomIn() {
        val scaleX = ObjectAnimator.ofFloat(imgRate, "scaleX", 1f, 1.2f)
        val scaleY = ObjectAnimator.ofFloat(imgRate, "scaleY", 1f, 1.2f)
        scaleX.duration = 500
        scaleY.duration = 500
        scaleX.start()
        scaleY.start()
        isZoomedIn = true
        animateRotation()
    }

    private fun animateZoomOut() {
        val scaleX = ObjectAnimator.ofFloat(imgRate, "scaleX", 1.2f, 1f)
        val scaleY = ObjectAnimator.ofFloat(imgRate, "scaleY", 1.2f, 1f)
        scaleX.duration = 800
        scaleY.duration = 800
        scaleX.start()
        scaleY.start()
        isZoomedIn = false
    }
}