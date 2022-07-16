package com.binar.secondhand.helper

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

        formatRp.currencySymbol = "Rp "
        formatRp.monetaryDecimalSeparator = ','
        formatRp.groupingSeparator = '.'

        kursIndonesia.decimalFormatSymbols = formatRp
        return kursIndonesia.format(number).dropLast(3)
    }

    fun harga(string: String) : String {
        return if (string.isEmpty()) "0"
        else DecimalFormat("#,###").format(string.replace("[^\\d]".toRegex(), "").toLong())
    }
}