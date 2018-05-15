package com.jaesun.popone.uberrave

import com.uber.rave.Rave
import com.uber.rave.RaveException
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type



class RaveConverterFactory : Converter.Factory() {
    companion object {
        fun create() : RaveConverterFactory {
            return RaveConverterFactory()
        }
    }

    override fun responseBodyConverter(type : Type,
                                       annotations : Array<out Annotation>,
                                       retrofit : Retrofit?) : Converter<ResponseBody, *> {
        val delegateConverter : Converter<ResponseBody, *> = retrofit!!.nextResponseBodyConverter<Any>(this, type, annotations)
        return RaveResponseConverter(delegateConverter)
    }

    class RaveResponseConverter(val delegateConverter : Converter<ResponseBody, *>) : Converter<ResponseBody, Any> {
        override fun convert(value : ResponseBody) : Any {
            val convert = delegateConverter.convert(value)
            try {
                Rave.getInstance().validate(convert)
            } catch (e : RaveException) {
                throw RuntimeException()
            }
            return convert
        }
    }
}