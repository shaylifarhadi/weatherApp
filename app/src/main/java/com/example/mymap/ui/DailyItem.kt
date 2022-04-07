package com.example.mymap.ui

import android.annotation.SuppressLint
import com.example.mymap.R
import com.example.mymap.databinding.ItemDailyBinding
import com.example.mymap.network.datamodel.Daily
import com.xwray.groupie.databinding.BindableItem
import java.text.SimpleDateFormat
import java.util.*

class DailyItem(val daily : Daily) : BindableItem<ItemDailyBinding>() {

    companion object {

        var celTemp : String = ""
    }

    @SuppressLint("SetTextI18n")
    override fun bind(dataBinding : ItemDailyBinding, position : Int) {
        dataBinding.daily = daily

        val kelvinTemp = daily.temp?.day ?: 0

        dataBinding.tvDay.text = formatDate((daily.dt?.toLong()?.times(1000)) ?: 0)

        celTemp = toCelsius((kelvinTemp) as Double).toString()

        dataBinding.tvTemp.text = celTemp.substring(0, celTemp.indexOf(".") - 1)
    }

    override fun getLayout() = R.layout.item_daily

    private fun toCelsius(kelvinTemp : Double) : Double =
        kelvinTemp - 273.15F

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(date : Long?) : String {
        return try {
            val dateFormat = SimpleDateFormat("yyyy/MM/dd")
            val date = Date(date ?: 0)
            dateFormat.format(date)
        } catch (e : Exception) {
            e.toString()
        }
    }


}




