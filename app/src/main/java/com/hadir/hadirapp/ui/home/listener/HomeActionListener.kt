package com.hadir.hadirapp.ui.home.listener

import com.hadir.hadirapp.model.DailyDataModel

interface HomeActionListener {
    fun onClickItem(dailyDataModel: DailyDataModel)
}