package org.vsu.pt.team2.utilitatemmetrisapp.ui.tools

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.orhanobut.logger.Logger
import org.vsu.pt.team2.utilitatemmetrisapp.MyApplication
import org.vsu.pt.team2.utilitatemmetrisapp.R
import org.vsu.pt.team2.utilitatemmetrisapp.network.ApiResult
import org.vsu.pt.team2.utilitatemmetrisapp.network.ErrorResponse
import org.vsu.pt.team2.utilitatemmetrisapp.ui.main.MainActivity

fun AppCompatActivity.hideKeyboard(view: View) {
    hideKeyboardFrom(this, view)
}

fun Fragment.hideKeyboard() {
    if (context != null && view != null)
        hideKeyboardFrom(context!!, view!!)
}

fun <T : AppCompatActivity> AppCompatActivity.openActivity(
    activity: Class<T>,
    finishThis: Boolean = false,
    applyIntent: (Intent) -> Unit = {}
) {
    val intent = Intent(this, activity)
    applyIntent.invoke(intent)
    startActivity(intent)
    if (finishThis)
        this.finish()
}

fun <T : AppCompatActivity> AppCompatActivity.replaceActivity(
    activity: Class<T>,
    applyIntent: (Intent) -> Unit = {}
) = openActivity(activity, true, applyIntent)

fun Fragment.showToast(message: String, isShort: Boolean = true) {
    Toast.makeText(this.context, message, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG)
        .show()
}

fun Fragment.networkConnectionErrorToast() {
    showToast(getString(R.string.lost_internet_connection))
}

fun Fragment.genericErrorToast(code: Int? = null, genericError: ErrorResponse? = null) {
    showToast(getString(R.string.generic_error_default_message))
}

fun Fragment.genericErrorToast(err: ApiResult.GenericError) {
    genericErrorToast(err.code, err.error)
}

fun AppCompatActivity.showToast(message: String, isShort: Boolean = true) {
    if (Looper.myLooper() == null)
        Looper.prepare()
    Toast.makeText(this, message, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG)
        .show()
}

fun AppCompatActivity.networkConnectionErrorToast() {
    showToast(getString(R.string.lost_internet_connection))
}

fun AppCompatActivity.genericErrorToast(code: Int? = null, genericError: ErrorResponse? = null) {
    showToast(getString(R.string.generic_error_default_message))
}

fun AppCompatActivity.genericErrorToast(err: ApiResult.GenericError) {
    genericErrorToast(err.code, err.error)
}

fun Fragment.appCompatActivity(): AppCompatActivity? {
    if (activity == null)
        Logger.e("Cant get activity. it is null")
    (activity as? AppCompatActivity).let {
        if (it == null)
            Logger.e("Cant transform activity to AppCompatActivity")
        return it
    }
}

fun Fragment.requireAppCompatActivity(): AppCompatActivity {
    return appCompatActivity()!!
}

fun Fragment.mainActivity(): MainActivity? {
    if (activity == null)
        Logger.e("Cant get activity. it is null")
    (activity as? MainActivity).let {
        if (it == null)
            Logger.e("Cant transform activity to AppCompatActivity")
        return it
    }
}

fun Fragment.requireMainActivity(): AppCompatActivity {
    return mainActivity()!!
}

fun Fragment.replaceFragment(fragment: Fragment, closingCurrent: Boolean = false) {
    if (closingCurrent)
        parentFragmentManager.popBackStackImmediate()
    parentFragmentManager
        .beginTransaction()
        .addToBackStack(null)
        .replace(R.id.dataContainer, fragment)
        .commit()
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, closingCurrent: Boolean = false) {
    if (closingCurrent)
        supportFragmentManager.popBackStackImmediate()
    supportFragmentManager.beginTransaction()
        .addToBackStack(null)
        .replace(R.id.dataContainer, fragment)
        .commit()
    fragment.view?.let { hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}


fun AppCompatActivity.setTitleIfMain(title: String) {
    (this as? MainActivity)?.titleTV?.text = title
}

fun AppCompatActivity.myApplication(): MyApplication? = application as? MyApplication

fun AppCompatActivity.requireMyApplication(): MyApplication = application as? MyApplication
    ?: throw RuntimeException("cant get my application, but its required")

fun Fragment.myApplication(): MyApplication? = appCompatActivity()?.myApplication()