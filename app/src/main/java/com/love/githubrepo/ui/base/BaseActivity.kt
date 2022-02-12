package com.love.githubrepo.ui.base


import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.love.githubrepo.R
import com.love.githubrepo.util.*
import io.reactivex.disposables.CompositeDisposable


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(),
    BaseFragment.Callback, CommonNavigator
{


    var viewDataBinding: T? = null
        private set
    private var mViewModel: V? = null
    private var permission: Array<String>? = null
    private var pDialog: ProgressDialog? = null

    // private val dialog: ProgressDialog? = null
    private val disposable = CompositeDisposable()
    var broadcastReceiver: BroadcastReceiver? = null
    public var alertDialog: Dialog? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }

    private val UPDATE_INTERVAL = 20 * 1000.toLong()
    val REQUEST_CODE_FOR_PERMISSION_SETTING = 1111
    private var onLocationUpdateListener: onLocationUpdate? = null
    private var a = 0
    private var latitude = 0.0
    private var longitude = 0.0

    interface onLocationUpdate {
        fun updateLatLong(lat: Double, longi: Double)
    }



    fun setUpdateLatLongListener(onLocationUpdateListener: onLocationUpdate) {

        this.onLocationUpdateListener = onLocationUpdateListener


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permission = null
        performDataBinding()
        viewDataBinding!!.lifecycleOwner = this

    }

/*    fun moveToApplicationSetting() {
        DialogConstant.showAlertDialog(applicationContext.resources.getString(R.string.dialog_alert_heading),
            applicationContext.resources.getString(R.string.allow_permission_setting),
            this, object : DialogConstant.OnConfirmedListener {
                override fun onConfirmed() {

                    startActivityForResult(
                        Intent(
                            android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                        ), REQUEST_CODE_FOR_PERMISSION_SETTING
                    );

                }
            })

    }*/

    /*private fun getToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
            AppPreference.addValue(PreferenceKeys.DEVICE_ID, instanceIdResult.token)
            Logger.e("Refresh Token:", instanceIdResult.token)
        }
    }
*/

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        this.mViewModel = if (mViewModel == null) viewModel else mViewModel
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }


    //rx method to check permission
   /* fun checkPermission(context: Context, vararg permissions: String) {
        disposable.add(
            RxPermissions(this)
                .request(*permissions)
                .subscribe { granted ->
                    if (granted!!) {
                        rxPermissionGranted()
                    } else {
                        rxPermissionDenied()
                    }
                })
    }*/

    /*invoked when permission granted*/
    protected open fun rxPermissionGranted() {

    }

    /*invoked when permission denied*/
    protected open fun rxPermissionDenied() {

    }

    fun setStatusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//  set status text dark
            window.statusBarColor =
                ContextCompat.getColor(this, color)// set status background white
        }
    }


    fun hideStatusBar() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    fun showStatusBarWithCustomColor(white: Int) {
        // if you remove this line status bar icons show in white color, otherwise in black
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = white
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()

    }


    override fun showProgress() {
        if (pDialog != null) {
            pDialog!!.dismiss()
        } else {
        }
        pDialog = ProgressHUD.init(this@BaseActivity, false, false)
        pDialog!!.show()
    }

    override fun hideProgress() {
        if (pDialog != null) pDialog!!.dismiss()
        if (pDialog != null) pDialog!!.cancel()
    }

    override fun showNetworkError(error: String) {
        DialogConstant.showAlertDialog(
            getStringResource(R.string.dialog_alert_heading),
            error,
            this,
            null
        )
    }

    override fun showServerError(error: String) {
        DialogConstant.showAlertDialog(
            getStringResource(R.string.dialog_alert_heading),
            error,
            this,
            object : DialogConstant.OnConfirmedListener {
                override fun onConfirmed() {


                }
            }
        )
    }

    override fun showAppUpdateError(error: String) {
        DialogConstant.showAlertDialog(
            getStringResource(R.string.dialog_alert_heading),
            error,
            this,
            object : DialogConstant.OnConfirmedListener {
                override fun onConfirmed() {
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$packageName")
                            )
                        );
                    } catch (anfe: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                            )
                        );
                    }
                }
            }
        )
    }



    /*fun redirectToLoginPage() {

        try {
            val intent1 = Intent(
                this,
                LoginActivity::class.java
            )

            intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent1)
            finish()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }*/


    /*override fun appModeChange(checkJsonErrorBody: JSONObject) {
        DialogConstant.showAlertDialogSessionExpire(getStringResource(R.string.dialog_alert_heading),
            checkJsonErrorBody.getJSONObject("error").getString("message") ,
            this, object : DialogConstant.OnConfirmedListener {
                override fun onConfirmed() {

                    //add preference

                    val i = Intent(this@BaseActivity  , HomeActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                    finish()

                }
            })

    }*/
    override fun showSessionExpireAlert(error: String ,activity: Activity) {
        DialogConstant.showAlertDialogSessionExpire(getStringResource(
            R.string.dialog_alert_heading
        ),
            getString(R.string.unauthorized_access),
            this, object : DialogConstant.OnConfirmedListener {
                override fun onConfirmed() {


                    ClearPreference.clearDataLogout(this@BaseActivity, activity)

                }
            })
    }


    override fun setRootViewVisibility() {

    }

    override fun getStringResource(id: Int): String {
        return resources.getString(id)
    }

    override fun getIntegerResource(id: Int): Int {
        return resources.getInteger(id)
    }

    override fun showValidationError(message: String) {
        CommonUtils.showMessage(message, this)
    }

    override fun onBackClick() {
        onBackPressed()
    }

    fun checkIfInternetOn(): Boolean {
        if (CommonUtils.isInternetOn(this)) {
            return true
        } else {
            CommonUtils.showMessage(
                getStringResource(R.string.validation_internet_connection),
                this
            )
            return false
        }
    }

    fun checkIfInternetOnDialog(tryAgainClick: () -> Unit?): Boolean {
        return if (CommonUtils.isInternetOn(this)) {
            true
        } else {
            DialogConstant.noInternetDialog(this, tryAgainClick = {
                showInternetDialog()
                tryAgainClick.invoke()
            })
            false
        }
    }

//    fun checkIfInternetOnDialog(): Boolean {
//
//        try {
//            return if (CommonUtils.isInternetOn(this)) {
//                true
//            } else {
//                showInternetDialog()
//                false
//            }
//        }catch (e: java.lang.Exception){
//            return false
//        }
//        //return baseActivity!!.checkIfInternetOn()
//    }

    private fun showInternetDialog(){
        DialogConstant.noInternetDialog(this,tryAgainClick = {
            if(!CommonUtils.isInternetOn(this!!)){
                checkIfInternetOn()
            }
        })
    }


    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> when (resultCode) {
                Activity.RESULT_OK -> {
                  //  getLatLong()
                }
                Activity.RESULT_CANCELED -> {
                   // locationPopUp()
                }
                GPS_STATUS_REQUEST_CODE -> {

                }
                else -> {
                }
            }

        }

            if (requestCode == REQUEST_CODE_FOR_PERMISSION_SETTING) {
                checkPermission(this, *AppConstants.READ_LOCATION)
            }

    }

*/



    fun createDrawableFromView(context: Context, view: View): Bitmap {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay
            .getMetrics(displayMetrics)
        view.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(
            0, 0, displayMetrics.widthPixels,
            displayMetrics.heightPixels
        )
        view.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(
            view.measuredWidth,
            view.measuredHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }


    companion object {
        /**
         * Constant used in the location settings dialog.
         */
        val REQUEST_CHECK_SETTINGS = 0x1
    }

    override fun showAlertDialog1Button(
        alertTitle: String,
        alertMsg: String,
        buttonTitle: String,
        onClick: () -> Unit?
    ) {
        DialogConstant.showAlertDialog1Button(
            context = this,
            alertMsg = alertMsg,
            alertTitle = alertTitle,
            buttonTitle = buttonTitle,
            onClick = onClick
        )
    }



}

