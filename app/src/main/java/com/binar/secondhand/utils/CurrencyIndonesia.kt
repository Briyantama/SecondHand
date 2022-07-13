package com.binar.secondhand.utils

import java.text.DecimalFormat

object CurrencyIndonesia {
    fun idr(number: String): String {
        return if (number == "") {
            "IDR 0"
        } else {
            val formatRupiah = DecimalFormat("#,###.##")
            "IDR ${formatRupiah.format(number.toDouble())}"
        }
    }

    fun rp(number: String): String {
        return if (number == "") {
            "Rp 0"
        } else {
            val formatRupiah = DecimalFormat("#,###.##")
            "Rp ${formatRupiah.format(number.toDouble())}"
        }
    }

    fun currencyFormat(number: String): String {
        return if (number == "") {
            "0"
        } else {
            val formatRupiah = DecimalFormat("#,###.##")
            formatRupiah.format(number.toDouble())
        }
    }
}
