package shah.nishant.findingfalcone.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import shah.nishant.findingfalcone.BuildConfig
import shah.nishant.findingfalcone.MainActivity
import java.util.concurrent.TimeUnit

@Module
abstract class ApplicationModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun gsonConverterFactory() = GsonConverterFactory.create()

        @Provides
        @JvmStatic
        fun interceptor(): Interceptor {
            val interceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
            } else {
                interceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
            }
            return interceptor
        }

        @Provides
        @JvmStatic
        fun okHttpClient(interceptor: Interceptor): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build()
        }

        @Provides
        @JvmStatic
        fun provideRetrofitBase(okHttpClient: OkHttpClient, converterFactory: GsonConverterFactory
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://findfalcone.herokuapp.com/")
                .addConverterFactory(converterFactory)
                .client(okHttpClient)
                .build()
        }
    }

}
