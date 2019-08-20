package com.example.currencyapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyRate(
    @PrimaryKey val currencyCode: String,
    @ColumnInfo(name = "currencyDescription")val currencyDescription: String? = null,
    @ColumnInfo(name = "currencyFlag") val currencyFlag: String? = null,
    @ColumnInfo(name = "currencyRate") val currencyRate: Float
)