package com.health.vistacan.di

import android.R
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Handler
import android.os.Looper
import android.support.v4.media.RatingCompat.Style
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.app.ActivityCompat.finishAffinity
import com.health.vistacan.BuildConfig
import com.health.vistacan.MyApplication
import com.health.vistacan.api.ApiService
import com.health.vistacan.login.view.LoginActivity
import com.zeoner.vistacan.Sharedpref.Constants
import com.zeoner.vistacan.Sharedpref.SharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.util.*
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }


    @Provides
    @Singleton
    fun provideTrustManagerFactory(): TrustManagerFactory {
        try {
            val context: Context = MyApplication.getContext()
            val cf = CertificateFactory.getInstance("X.509")

            val caInput: InputStream = context.resources.openRawResource(com.health.vistacan.R.raw.ssl)
            var ca: Certificate? = null
            try {
                ca = cf.generateCertificate(caInput)

            } catch (er: Exception) {
                Log.e("error ", er.message.toString())
            } finally {
                try {
                    caInput.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            val keyStoreType = KeyStore.getDefaultType()
            val keyStore = KeyStore.getInstance(keyStoreType)
            keyStore.load(null, null)
            keyStore.setCertificateEntry("ca", ca)
            val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
            val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
            tmf.init(keyStore)
            return tmf
        } catch (e: java.lang.Exception) {
            Log.e("exception", (e.message)!!)
            throw e
        }
    }

    @Provides
    @Singleton
    fun provideX509TrustManager(trustManagerFactory: TrustManagerFactory): X509TrustManager {
        val trustManager: X509TrustManager
        try {
            val trustManagers = trustManagerFactory.trustManagers
            if (trustManagers.size != 1 || trustManagers.get(0) !is X509TrustManager) {
                throw java.lang.IllegalStateException(
                    "Unexpected default trust managers:"
                            + Arrays.toString(trustManagers)
                )
            }
            trustManager = trustManagers[0] as X509TrustManager
            return trustManager
        } catch (e: java.lang.Exception) {
            Log.e("exception", (e.message)!!)
            throw e
        }
    }

    @Provides
    @Singleton
    fun provideSslContext(trustManagerFactory: TrustManagerFactory): SSLContext {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustManagerFactory.trustManagers, null)
        return sslContext
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        logging: HttpLoggingInterceptor,
        x509TrustManager: X509TrustManager,
        sslContext: SSLContext,
        @ApplicationContext context: Context
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(logging)
        }

        val IS_LOGIN: Boolean = SharedPref.get(
            context,
            Constants.LOGIN_SESSION,
            Constants.IS_LOGIN,
            false
        ) as Boolean
        if (IS_LOGIN) {
            builder.addInterceptor { chain ->
                val countrycode: String = SharedPref.get(
                    context,
                    Constants.COMMON_SESSION,
                    Constants.CountryCode,
                    ""
                ) as String
                Log.e("countrycode", countrycode)
                val originalRequest = chain.request()
                val modifiedRequest = originalRequest.newBuilder()
                    .header("CountryCode", countrycode)
                    .build()

                var response: Response? = null
                try {
                    response = chain.proceed(modifiedRequest)

                    when (response.code) {
                        200 -> {
                            Log.d("ApiModule", "Success")
                         Handler(Looper.getMainLooper()).post {
//                                   Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                            popupMessage(context,"Success")
                            }

                        }
                        404 -> {
                            Log.d("ApiModule", "Page Not Found")

                            Handler(Looper.getMainLooper()).post {
                                Toast.makeText(
                                    context,
                                    "Page Not Found",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        401 -> {
                            Log.d("ApiModule", "Unauthorized")

                            Handler(Looper.getMainLooper()).post {
                                Toast.makeText(
                                    context,
                                    "Unauthorized. Please log in again.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            SharedPref.sessionClear(context, Constants.LOGIN_SESSION)
                        }
                        500 -> {
                            Log.d("ApiModule", "Internal Server Error")

                            Handler(Looper.getMainLooper()).post {
                                Toast.makeText(
                                    context,
                                    "internalerror",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        else -> {
                            Log.d("ApiModule", "Unknown Error")
                        }
                    }
                } finally {
                    response?.close()
                }

                chain.proceed(modifiedRequest)
            }
        }

        builder.sslSocketFactory(sslContext.socketFactory, x509TrustManager)
        return builder.build()

    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
    fun popupMessage(context: Context, message: String) {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context,)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setTitle("Error")
        alertDialogBuilder.setNegativeButton("OK") { dialogInterface, i ->
            Log.d("internet", "OK button pressed")
            if (context is Activity) {
                context.finishAffinity()
            }

        }
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}