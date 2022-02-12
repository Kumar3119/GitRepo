package com.love.githubrepo.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.location.LocationManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.UnderlineSpan
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.love.githubrepo.R
import com.love.githubrepo.data.local.AppPreference
import com.love.githubrepo.data.local.PreferenceKeys
import java.io.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DateFormat
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.ceil

object CommonUtils {

    private var myLocale: Locale? = null

    fun setLocale(appLanguage: String, context: Context) {
        val getLanguage =
            Resources.getSystem().configuration.locale.getDisplayLanguage(Locale.getDefault())
        if (appLanguage != getLanguage) {
            myLocale = Locale(appLanguage)
            Locale.setDefault(myLocale)
            val config = Configuration()
            config.locale = myLocale

            if (Build.VERSION.SDK_INT >= 17) {
                config.setLocale(myLocale);
                //context.createConfigurationContext(config);
            } else {
                config.locale = myLocale;
            }
            context.resources.updateConfiguration(config, context.resources.displayMetrics)

        }
    }

    /*permissions*/
    val READ_WRITE_EXTERNAL_STORAGE_AND_CAMERA =
        arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )


    var ACCESS_LOCATION = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    private var internetCallback: InternetCallback? = null
    private val dialog: Dialog? = null
    var mp: MediaPlayer? = null
    private lateinit var mAudioManager: AudioManager;
    private var media_current_volume = 0
    private var media_max_volume = 0
    val currentTimeZone: String
        get() = TimeZone.getDefault().id.toString()

    fun validateEmail(strEmail: String): Boolean {
        if (checkEmailManually(strEmail)) {
            val pattern: Pattern
            val matcher: Matcher
            val EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
            pattern = Pattern.compile(EMAIL_PATTERN)
            matcher = pattern.matcher(strEmail)
            return matcher.matches()
        } else {
            return false
        }

    }


    fun getSpannedText(text: String): Spanned? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(text)
        }
    }

    open fun isPhoneNumberValid(phoneNumber: String?): Boolean {
        /* val PHONE_NUMBER_PATTERN = "^[6-9]\\d{10}$"
         val pattern: Pattern
         val matcher: Matcher
         pattern = Pattern.compile(PHONE_NUMBER_PATTERN)
         matcher = pattern.matcher(phoneNumber)
         return matcher.matches()*/
        if (!Pattern.matches("[a-zA-Z]+", phoneNumber)) {
            return phoneNumber!!.length > 5 && phoneNumber.length <= 15
        }
        return false;
    }


    fun makeFirstLetterUpperCase(string: String): String {
        if (string.isNotEmpty()) {
            return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase()
        }
        return ""
    }




//    val appVersionCode: String
//        get() = BuildConfig.VERSION_NAME

    val osVersion: Int
        get() = android.os.Build.VERSION.SDK_INT

    /**
     * Returns the consumer friendly device name
     */
    val deviceName: String?
        get() {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                capitalize(model)
            } else capitalize(manufacturer) + " " + model
        }

    fun setInernetCallback(internetCallback1: InternetCallback) {
        internetCallback = internetCallback1
    }

    @SuppressLint("all")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    @SuppressLint("all")
    fun getDeviceType(): String {
        return "android";
    }

    @SuppressLint("all")
    fun getCertificationType(): String {
        return "development";
    }

    fun wordFirstCap(str: String): String {
        var capStr = ""
        try {
            val words = str.trim { it <= ' ' }.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            val ret = StringBuilder()
            for (i in words.indices) {
                if (words[i].trim { it <= ' ' }.length > 0) {
                    Log.e("words[i].trim", "" + words[i].trim { it <= ' ' }[0])
                    ret.append(Character.toUpperCase(words[i].trim { it <= ' ' }[0]))
                    ret.append(words[i].trim { it <= ' ' }.substring(1))
                    if (i < words.size - 1) {
                        ret.append(' ')
                    }
                }
            }
            capStr = ret.toString()

        } catch (e: Exception) {
            e.printStackTrace()
            capStr = ""
        }

        return capStr
    }

    @Throws(IOException::class)
    fun loadJSONFromAsset(context: Context, jsonFileName: String): String {
        val manager = context.assets
        val `is` = manager.open(jsonFileName)

        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        return String(buffer, Charsets.UTF_8)
    }

    fun isValidEmail(target: String?): Boolean {

        return if (target == null) {

            false

        } else {

            Patterns.EMAIL_ADDRESS.matcher(target).matches()

        }

    }

    fun isEmailValid(strEmail: String): Boolean {
        if (checkEmailManually(strEmail)) {
            val pattern: Pattern
            val matcher: Matcher
            val EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
            pattern = Pattern.compile(EMAIL_PATTERN)
            matcher = pattern.matcher(strEmail)
            return matcher.matches()
        } else {
            return false
        }

    }

    fun checkPassword(password: String): Boolean {
        val PASSWORD_PATTERN = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$")
        return try {
            PASSWORD_PATTERN.matcher(password).matches()
        } catch (exception: NullPointerException) {
            false
        }
    }

    fun checkName(name: String): Boolean {
        var regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]")

        if (regex.matcher(name).find()) {
            Log.e(javaClass.name, "SPECIAL CHARS FOUND");
            return true
        }

        return false
    }


    fun daysFromCurrentDate(date: String): String {
        try {
            val utc = TimeZone.getTimeZone("UTC")
            val sourceFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val destFormat = SimpleDateFormat("dd MMM, hh:mm a")
            sourceFormat.timeZone = utc
            val convertedDate = sourceFormat.parse(date)

            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val resultdate = Date(System.currentTimeMillis())
            val current = (inputFormat.format(resultdate))
            val currentDate = inputFormat.parse(current)
            val cal = Calendar.getInstance()
            cal.time = currentDate!!
            val cYear = cal.get(Calendar.YEAR)
            val cMonth = cal.get(Calendar.MONTH)
            val cDay = cal.get(Calendar.DAY_OF_MONTH)
            val calTo = Calendar.getInstance()
            calTo.time = convertedDate!!
            val toYear = calTo.get(Calendar.YEAR)
            val toMonth = calTo.get(Calendar.MONTH)
            val toDay = calTo.get(Calendar.DAY_OF_MONTH)
            var date1 = Calendar.getInstance()
            var date2 = Calendar.getInstance()
            date1.clear()
            date1.set(cYear, cMonth, cDay)
            date2.clear()
            date2.set(toYear, toMonth, toDay)
            val difference = date2.timeInMillis - date1.timeInMillis
            val days = difference / (24 * 60 * 60 * 1000)
            return days.toInt().toString()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ""
    }


    fun calculatePageLimit(totalCount: Int, limit: Int): Double {
        try {

            val page = totalCount.toDouble() / limit
            return ceil(page)
        } catch (w: Exception) {
            w.printStackTrace()
        }

        return 0.0
    }

    fun checkMobile(mobile: String): Boolean {
        val MOBILE_NUMBER_PATTERN =
            Pattern.compile("^[0-9]{9,14}$")
        return try {
            MOBILE_NUMBER_PATTERN.matcher(mobile).matches()
        } catch (exception: NullPointerException) {
            false
        }
    }


    fun checkEmailManually(strEmail: String): Boolean {
        return !strEmail.startsWith(".") &&
                !strEmail.contains(".-_") &&
                !strEmail.contains("_-.") &&
                !strEmail.contains("_.-") &&
                !strEmail.contains("@.") &&
                !strEmail.contains("..") &&
                !strEmail.contains(".@.") &&
                !strEmail.contains("__") &&
                !strEmail.contains("-@") &&
                !strEmail.contains("@-") &&
                !strEmail.contains("--") &&
                !strEmail.contains("..")
    }


    fun getPixelValue(dimenId: Int, context: Context): Int {
        val resources = context.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dimenId.toFloat(),
            resources.displayMetrics
        ).toInt()
    }

    fun showInstructionPopup(context: Context) {
        var alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
        alertDialogBuilder.setMessage(context.getString(R.string.on_location_instruction))
            .setCancelable(false)
            .setPositiveButton(
                context.getString(R.string.ok)
            ) { dialog, id ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri: Uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
            }
        alertDialogBuilder.setNegativeButton(
            context.getString(R.string.cancel)
        ) { dialog, id -> dialog.cancel(); }
        val alert: AlertDialog = alertDialogBuilder.create()
        alert.show()
    }

    fun showNoInternetPopup(context: Context, listener: DialogConstant.OnConfirmedListener) {
        DialogConstant.showAlertDialog(
            context.getString(R.string.dialog_alert_heading),
            context.getString(R.string.no_internet),
            context,
            listener
        )
    }

    fun getImageUri(inImage: Bitmap, context: Context): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(context.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }


    fun isInternetOn(context: Context): Boolean {
        val connectivity = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val networkInfo = connectivity.activeNetworkInfo
            return (networkInfo != null && networkInfo.isAvailable
                    && networkInfo.isConnected)
        }
        return false
    }

    fun showMessage(message: String, context: Context) {

        try {
            val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
          //  val toastView = toast.view
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                val view = toast.view
                toast!!.view!!.setBackgroundResource(R.drawable.dr_custom_toast)
                val tview = view!!.findViewById<TextView>(android.R.id.message)
                tview.setTextColor(Color.WHITE)
            }
           // val toastMessage = toastView!!.findViewById<TextView>(android.R.id.message)
            //toastMessage.setTextColor(Color.WHITE)
         //   toastMessage.gravity = Gravity.CENTER

            //val font = Typeface.createFromAsset(context.assets, "font/cerapro_medium.ttf")
            // toastMessage.typeface = font


            toast.show()
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }

    }

    /*** method for use getting real path of media file ***/
    fun getRealPathFromURI(activity: Activity, contentURI: Uri): String? {
        var result: String? = null

        val cursor = activity.contentResolver.query(contentURI, null, null, null, null)

        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            if (cursor.moveToFirst()) {
                val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                result = cursor.getString(idx)
            }
            cursor.close()
        }
        return result
    }

    fun getFileImages(bmap: Bitmap, name: String): File {

        val image_file = File(Environment.getExternalStorageDirectory(), "$name.jpeg")

        val outStream: FileOutputStream

        try {

            outStream = FileOutputStream(image_file)

            val isComp = bmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)

            outStream.flush()

            outStream.close()

        } catch (e: FileNotFoundException) {

            e.printStackTrace()

        } catch (e: IOException) {

            e.printStackTrace()

        } catch (e: Exception) {

            e.printStackTrace()
        }

        return image_file
    }


    fun getDateInFormat(dateInput: String, dateOutput: String, _date: String): String {

        try {
            @SuppressLint("SimpleDateFormat") val inputFormat = SimpleDateFormat(dateInput)
            @SuppressLint("SimpleDateFormat") val outputFormat = SimpleDateFormat(dateOutput)
            val date = inputFormat.parse(_date)
            return outputFormat.format(date!!)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""

        }

    }

    fun getDateInFormat(
        dateInput: String,
        dateOutput: String,
        istime: Boolean,
        dateString: String
    ): String {
        var parseString = dateString
        try {
            val formatter2 = SimpleDateFormat(dateOutput)
            val symbols = DateFormatSymbols()
            symbols.amPmStrings = arrayOf("AM", "PM")
            formatter2.setDateFormatSymbols(symbols)
            parseString = formatter2.format(SimpleDateFormat(dateInput).parse(dateString)!!)
            Logger.d("NEW TEST----->>  parsed format date ------- ", parseString)
        } catch (e1: Exception) {
            e1.printStackTrace()
        }

        return parseString
    }

    fun Convert24to12(
        dateInput: String,
        dateOutput: String, dateString: String
    ): String {
        var convertedTime = ""
        try {
            val displayFormat = SimpleDateFormat(dateOutput)
            val parseFormat = SimpleDateFormat(dateInput)
            val date = parseFormat.parse(dateString)

            val symbols = DateFormatSymbols()
            symbols.amPmStrings = arrayOf("AM", "PM")
            displayFormat.setDateFormatSymbols(symbols)

            convertedTime = displayFormat.format(date!!)
            Logger.d("convertedTime : ", convertedTime)

        } catch (e: ParseException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return convertedTime
        //Output will be 10:23 PM
    }

    fun setUnderLineText(contentText: AppCompatTextView) {
        val content = SpannableString(contentText.text.toString())
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        contentText.text = content
    }

    /**
     * this method is used when we want to start any activity and at the same time
     * we want all previous activities to be finished.
     */
    fun startActivityAndKillAll(context: Context, cls: Class<*>) {
        val intent = Intent(context, cls)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
        (context as Activity).overridePendingTransition(R.anim.slide_for_in, R.anim.slide_for_out)
    }

    fun startActivityWithBundle(context: Context, cls: Class<*>, bn: Bundle) {
        val intent = Intent(context, cls)
        intent.putExtras(bn)
        context.startActivity(intent)
        (context as Activity).overridePendingTransition(R.anim.slide_for_in, R.anim.slide_for_out)
    }

    /*fun changeTabsFont(tabLayout: TabLayout) {
        val vg = tabLayout.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount
        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup
            val tabChildsCount = vgTab.childCount
            for (i in 0 until tabChildsCount) {
                val tabViewChild = vgTab.getChildAt(i)
                if (tabViewChild is TextView) {
                    tabViewChild.setTypeface(
                        ResourcesCompat.getFont(
                            tabLayout.context,
                            R.font.titillium_web_regular
                        )
                    )
                }
            }

        }
    }*/


    fun isValidPhoneNumber(phoneNumber: String, countryCode: String): Boolean {
        if (isStringNullOrBlank(phoneNumber)) {
            return false
        }

        return true
    }


    private fun capitalize(str: String): String? {
        if (TextUtils.isEmpty(str)) {
            return str
        }
        val arr = str.toCharArray()
        var capitalizeNext = true

        val phrase = StringBuilder()
        for (c in arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c))
                capitalizeNext = false
                continue
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true
            }
            phrase.append(c)
        }

        return phrase.toString()
    }

    fun getKeyHash(context: Context) {
        try {
            val info = context.packageManager.getPackageInfo(
                "com.savourapp",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KEY_HASH", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

    }

    /*** method for string validation ***/
    fun isStringNullOrBlank(str: String?): Boolean {

        try {
            if (str == null) {
                return true
            } else if (str.toLowerCase() == "null" || str == "" || str.isEmpty() || str.toLowerCase()
                    .equals(
                        "null",
                        ignoreCase = true
                    )
            ) {
                return true
            }
        } catch (e: Exception) {
            Logger.e(AppConstants.LOG_CAT, e.message!!)
        }
        return false
    }

    fun showBadgeCount(str: String?): Boolean {
        try {
            if (str == null) {
                return true
            } else if (str.toLowerCase() == "null" || str == "" || str.isEmpty() || str.toLowerCase()
                    .equals(
                        "null",
                        ignoreCase = true
                    )
            ) {
                return true
            } else if (str.toInt() <= 0) {
                return true
            }
        } catch (e: Exception) {
            Logger.e(AppConstants.LOG_CAT, e.message!!)
        }
        return false
    }

    fun toTitleCase(str: String?): String? {

        if (str == null) {
            return null
        }

        var space = true
        val builder: StringBuilder = StringBuilder(str)
        val len: Int = builder.length

        for (index in 0 until len) {
            val c: Char = builder[index]
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(index, Character.toTitleCase(c))
                    space = false
                }
            } else if (Character.isWhitespace(c)) {
                space = true
            } else {
                builder.setCharAt(index, Character.toLowerCase(c))
            }
        }
        return builder.toString()
    }

    fun getValueInPercentageFormate(value: String?): String {
        return "${value!!.substring(0, 2)}% Off"
    }


    fun firstLetterCapital(str: String): String {
        return if (!isStringNullOrBlank(str)) {
            str.capitalize()
        } else {
            ""
        }

    }

    fun getTodayDate(): String {
        val c: Date = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MMM-yyyy")
        return df.format(c)
    }

    fun getTimeInMiliSecond(time: String): Long? {
//        val date: String = time
//        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
//        val localDate: LocalDateTime = LocalDateTime.parse(date, formatter)
//        val timeInMilliseconds: Long = localDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()

        val givenDateString: String = getTodayDate() + " " + time
        Log.d("givenDateString", givenDateString)
        val sdf = SimpleDateFormat(/*AppConstants.TIME_REQUIRED_FORMAT*/"dd-MMM-yyyy HH:mm:ss")
        try {
            val mDate: Date = sdf.parse(givenDateString)
            return mDate.time
            println("Date in milli :: ${mDate.time}")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    @Throws(ParseException::class)
    fun convertDateFormat(dateStr: String?, dateStatus: Boolean): String? {
        val utc = TimeZone.getTimeZone("UTC")
        val sourceFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        if (dateStatus) {
            val destFormat = SimpleDateFormat("dd MMM, hh:mm a")
            sourceFormat.timeZone = utc
            val convertedDate = sourceFormat.parse(dateStr)
            return destFormat.format(convertedDate)
        } else {
            val destFormat = SimpleDateFormat("hh:mm a")
            sourceFormat.timeZone = utc
            val convertedDate = sourceFormat.parse(dateStr)
            return destFormat.format(convertedDate)
        }
    }

    @Throws(ParseException::class)
    fun convertDateFormatYear(dateStr: String?, dateStatus: Boolean): String? {
        val utc = TimeZone.getTimeZone("UTC")
        val sourceFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        if (dateStatus) {
            val destFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a")
            sourceFormat.timeZone = utc
            val convertedDate = sourceFormat.parse(dateStr)
            return destFormat.format(convertedDate)
        } else {
            val destFormat = SimpleDateFormat("hh:mm a")
            sourceFormat.timeZone = utc
            val convertedDate = sourceFormat.parse(dateStr)
            return destFormat.format(convertedDate)
        }
    }

    fun getCurrentTimeInMiliseconds(): Long {
        return System.currentTimeMillis()
    }


    fun showCustomToast(context: Context, msg: String) {
        val toast = Toast.makeText(
            context,
            msg,
            Toast.LENGTH_LONG
        )

        toast.view!!.setBackgroundResource(R.drawable.dr_custom_toast)
        //toast.setGravity(Gravity.BOTTOM, 0, 0)

        toast.show()
    }

    fun getDateInFormatTime(dateInput: String, dateOutput: String, _date: String): String {

        try {
            @SuppressLint("SimpleDateFormat") val inputFormat = SimpleDateFormat(dateInput)
            @SuppressLint("SimpleDateFormat") val outputFormat = SimpleDateFormat(dateOutput)
            val date = inputFormat.parse(_date)
            return outputFormat.format(date!!)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""

        }

    }

    fun convertdateYmDToDMY(date: String): String {
        // val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        val inputDateStr = date
        val date: Date = inputFormat.parse(inputDateStr)
        val outputDateStr: String = outputFormat.format(date)
        return outputDateStr
    }

    fun convertdateYmD_To_DMY(date: String): String {
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy")
        val inputDateStr = date
        val date: Date = inputFormat.parse(inputDateStr)
        val outputDateStr: String = outputFormat.format(date)
        return outputDateStr
    }

    fun convertdatehhmmss_To_hma(date: String): String {

        return try {
            val inFormat = SimpleDateFormat("HH:mm:ss")
            val date = inFormat.parse(date)
            val outFormat = SimpleDateFormat("hh:mm a")
            outFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    fun turnGPSOn(ctx: Context): Boolean? {
        val manager: LocationManager =
            ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (checkLocationPermission(ctx)) {
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !manager.isProviderEnabled(
                    LocationManager.NETWORK_PROVIDER
                )
            ) {
                val builder = AlertDialog.Builder(ctx)
                builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes")
                    {

                            dialog, id ->
                        (ctx as Activity).startActivityForResult(
                            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                            101
                        )

                        dialog.dismiss()
                    }


                val alert = builder.create()
                if (!alert.isShowing) {
                    alert.show()
                }


            } else {
                return true
            }
        }
        return false
    }

    fun CheckEnableGPS(context: Context): Boolean? {
        val provider = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.LOCATION_PROVIDERS_ALLOWED
        )
        if (provider != "") {
            //GPS Enabled
            return true
        } else {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes")
                { dialog, id ->
                    (context as Activity).startActivityForResult(
                        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                        101
                    )

                    dialog.dismiss()
                }

            val alert = builder.create()
            if (!alert.isShowing) {
                alert.show()
            }
        }
        return false
    }


    fun checkLocationPermission(mContext: Context): Boolean {
        val permissionCheck_fine = ContextCompat.checkSelfPermission(
            mContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val permissionCheck_coarse = ContextCompat.checkSelfPermission(
            mContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (permissionCheck_fine != PackageManager.PERMISSION_GRANTED || permissionCheck_coarse != PackageManager.PERMISSION_GRANTED) {

            return false
        }
        return true
    }


//    fun checkLocationPermission(context: Context): Boolean {
//        val permissionState = ActivityCompat.checkSelfPermission(
//            context,
//            Manifest.permission.ACCESS_COARSE_LOCATION
//        )
//        return permissionState == PackageManager.PERMISSION_GRANTED
//    }

    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }



}

