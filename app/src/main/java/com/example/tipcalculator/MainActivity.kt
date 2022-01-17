package com.example.tipcalculator

import android.animation.ArgbEvaluator
import android.graphics.Color.green
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.time.temporal.TemporalAmount

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sbTipPercent.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                tvTipPercent.text = "$progress%"
                computeTipAndTotal()
                updateTipDiscription(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        etBaseAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {

                computeTipAndTotal()
            }
        })
    }

    private fun updateTipDiscription(progress: Int) {
        val tipDescription = when (progress) {
            in 0..9 -> "Poor"
            in 10..19 -> "Acceptable"
            else -> {
                "Awesome"
            }
        }

        tvTipDescription.text = tipDescription

        val color = ArgbEvaluator().evaluate(
            (progress / sbTipPercent.max).toFloat(),
            ContextCompat.getColor(this, R.color.red),
            ContextCompat.getColor(this, R.color.Green)
        )as Int

        tvTipDescription.setTextColor(color)
    }

    private fun computeTipAndTotal() {
        if (etBaseAmount.text.isEmpty()) {
            tvTotalAmount.text = ""
            tvTipAmount.text = ""
            return
        }

        // 1)get base amt and tip percent
        val baseAmount = etBaseAmount.text.toString().toDouble()
        val tipPercent = sbTipPercent.progress

        // 2)compute tip amount and total
        val tipAmount = baseAmount * tipPercent / 100
        tvTipAmount.text = "%.2f".format(tipAmount)
        tvTotalAmount.text = "%.2f".format(baseAmount + tipAmount)
    }
}