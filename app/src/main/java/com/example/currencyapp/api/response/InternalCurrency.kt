package com.example.currencyapp.api.response

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.JsonAdapter
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

data class InternalCurrency(
    val base: String = "",
    @JsonAdapter(InternalRatesDeserializer::class)
    val rates: HashMap<String, Float> = hashMapOf()
) {

    class InternalRatesDeserializer : JsonDeserializer<HashMap<String, Float>> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): HashMap<String, Float> {
            return Gson().fromJson(json, object : TypeToken<HashMap<String, String>>() {}.type) as HashMap<String, Float>
        }
    }
}