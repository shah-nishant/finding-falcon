package shah.nishant.findingfalcone.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showShortToast(@StringRes resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
