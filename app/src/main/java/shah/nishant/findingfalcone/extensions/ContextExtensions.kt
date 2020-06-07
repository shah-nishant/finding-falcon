package shah.nishant.findingfalcone.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Context.showShortToast(@StringRes resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()

fun Context.showShortToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.isConnectedToInternet() =
    (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        .activeNetworkInfo?.isConnected
        ?: false
