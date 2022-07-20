package com.binar.secondhand.helper

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.ref.WeakReference
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class Util {

    private val format = "dd-MMM-yyyy"
    fun date(date: String): String {
        var kotlin = date
        kotlin = kotlin.drop(5)
        val month = kotlin.take(2)
        kotlin = kotlin.drop(3)
        val day = kotlin.take(2)
        kotlin = kotlin.drop(3)
        val hours = kotlin.take(2)
        kotlin = kotlin.drop(3)
        val minute = kotlin.take(2)
        val year = date.take(4)

        return "$day-$month-$year, $hours:$minute"
    }

    fun rupiah(number:Int): String{
        val kursIndonesia = DecimalFormat.getCurrencyInstance() as DecimalFormat
        val formatRp = DecimalFormatSymbols()

        formatRp.currencySymbol = "Rp. "
        formatRp.monetaryDecimalSeparator = ','
        formatRp.groupingSeparator = '.'

        kursIndonesia.decimalFormatSymbols = formatRp
        return kursIndonesia.format(number).dropLast(3)
    }

    fun setMaskingMoney(currencyText: String, harga: EditText) {
        harga.addTextChangedListener(object : MyTextWatcher {
            val editTextWatcher: WeakReference<EditText> =
                WeakReference<EditText>(harga)

            override fun afterTextChanged(editable: Editable?) {
                val editText = editTextWatcher.get() ?: return
                val s = editable.toString()
                editText.removeTextChangedListener(this)
                val cleanString = s.replace("[Rp,. ]".toRegex(), "")
                val newval = currencyText + cleanString.harga()

                editText.setText(newval)
                editText.setSelection(newval.length)
                editText.addTextChangedListener(this)
            }
        })
    }

    fun String.harga() : String {
        return if (this.isEmpty()) "0"
        else DecimalFormat("#,###").format(this.replace("[^\\d]".toRegex(), "").toLong())
    }

    interface MyTextWatcher : TextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }
}