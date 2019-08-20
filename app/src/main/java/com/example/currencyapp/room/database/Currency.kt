package com.example.currencyapp.room.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Currency(
    @PrimaryKey val currencyCode: String,
    @ColumnInfo(name = "currencyDescription") val currencyDescription: String? = null,
    @ColumnInfo(name = "currencyFlag") val currencyFlag: String? = null,
    @ColumnInfo(name = "currencyRate") val currencyRate: String
)