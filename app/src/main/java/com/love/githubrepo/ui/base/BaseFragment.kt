package com.love.githubrepo.ui.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.love.githubrepo.BuildConfig
import com.love.githubrepo.R
import com.love.githubrepo.util.*
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONObject
import java.lang.Exception

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> : Fragment(),
    CommonNavigator {

    val REQUEST_CODE_FOR_PERMISSION_SETTING = 1110
    private var baseActivity: BaseActivity<*, *>? = null
    private var mRootView: View? = null
    private val disposable = CompositeDisposable()
    var viewDataBinding: T? = null
        private set
    private var mViewModel: V? = null

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

    private val pDialog: CustomProgressDialog? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity = context as BaseActivity<*, *>?
            this.baseActivity = activity
            activity!!.onFragmentAttached()
            //activity.buildGoogleApiClient()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = viewModel
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewDataBinding!!.lifecycleOwner = this
        mRootView = viewDataBinding!!.root
        return mRootView
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }

    fun getLatLong() {
      //  baseActivity!!.getLatLong()
    }
    fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }
    /* protected void showCommonLoadingView(String msg){
        if(pDialog == null){
            pDialog = new CustomProgressDialog(getContext());
        }
        pDialog.showProgressDialog(msg);
    }

    protected void hideCommonLoadingView(){
        pDialog.hideProgressDialog();
    }*/

    override fun showProgress() {
        /* if(dialog == null){
            dialog = new ProgressDialog(this);
            dialog.setMessage("loading please wait");
            dialog.setCancelable(false);
        }
        dialog.show();*/
        /*if (pDialog != null) {
            pDialog.dismiss();
        }
        pDialog = new CustomProgressDialog(getContext());
        pDialog.show();*/
        baseActivity!!.showProgress()
    }


    //rx method to check permission
  /*  fun checkPermission(context: Context, vararg permissions: String) {
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

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()

    }

    fun showStatusBarWithCustomColor(white: Int) {
        baseActivity?.showStatusBarWithCustomColor(white)
    }


    override fun hideProgress() {
        /*if (pDialog != null) {
            pDialog.dismiss();
            pDialog.cancel();
        }*/
        baseActivity?.hideProgress()
    }

    override fun showNetworkError(error: String) {
        DialogConstant.showAlertDialog(
            getStringResource(R.string.dialog_alert_heading), error, requireActivity(),
            null
        )
    }

    override fun showServerError(error: String) {
        DialogConstant.showAlertDialog(
            getStringResource(R.string.dialog_alert_heading), error, requireActivity(),
            null
        )
    }

    override fun showAppUpdateError(error: String) {
        DialogConstant.showAlertDialog(
            getStringResource(R.string.dialog_alert_heading), error, requireActivity(),
            null
        )
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun showSessionExpireAlert(error: String , activity: Activity) {
        baseActivity!!.showSessionExpireAlert(error, activity)
    }


    override fun setRootViewVisibility() {

    }

    override fun showValidationError(message: String) {
        CommonUtils.showMessage(message, this.activity!!)
    }

    override fun getStringResource(id: Int): String {
        return resources.getString(id)
    }

    override fun getIntegerResource(id: Int): Int {
        return resources.getInteger(id)
    }

    override fun onBackClick() {

    }

    fun checkIfInternetOn(): Boolean {

        try {
            return if (CommonUtils.isInternetOn(baseActivity!!)) {
                true
            } else {
                showInternetDialog()
                false
            }
        }catch (e: Exception){
            return false
        }
        //return baseActivity!!.checkIfInternetOn()
    }

    private fun showInternetDialog(){
        DialogConstant.noInternetDialog(baseActivity!!,tryAgainClick = {
            if(!CommonUtils.isInternetOn(baseActivity!!)){
                checkIfInternetOn()
            }
        })
    }

    override fun showAlertDialog1Button(
        alertTitle: String,
        alertMsg: String,
        buttonTitle: String,
        onClick: () -> Unit?
    ) {
        baseActivity!!.showAlertDialog1Button(
            alertTitle = alertTitle,
            alertMsg = alertMsg,
            buttonTitle = buttonTitle,
            onClick = onClick
        )
    }


   /* fun moveToApplicationSetting() {
        DialogConstant.showAlertDialog(this.resources.getString(R.string.dialog_alert_heading),
            this.resources.getString(R.string.allow_permission_setting),
            context!! , object : DialogConstant.OnConfirmedListener {
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
}
