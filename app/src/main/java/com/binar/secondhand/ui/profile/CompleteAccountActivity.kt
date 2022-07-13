package com.binar.secondhand.ui.profile

import androidx.appcompat.app.AppCompatActivity

class CompleteAccountActivity : AppCompatActivity()
//    private val binding by lazy { ActivityCompleteAccountBinding.inflate(layoutInflater) }
//    private val viewModel by viewModel<ProfileViewModel>()
//    private var imageUri: Uri? = null
//    private var realPath = ""
//    private val requestCode = 100
//    var selectedImg: Uri? = null
//
//    private val galleryResult =
//        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                try {
//                    grantUriPermission(
//                        packageName,
//                        result,
//                        FLAG_GRANT_READ_URI_PERMISSION or FLAG_GRANT_PERSISTABLE_URI_PERMISSION
//                    )
//                } catch (e: IllegalArgumentException) {
//                    // on Kitkat api only 0x3 is allowed (FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION)
//                    grantUriPermission(
//                        packageName,
//                        result,
//                        FLAG_GRANT_READ_URI_PERMISSION
//                    )
//                } catch (e: SecurityException) {
//                    Log.e("", e.toString())
//                }
//                try {
//                    var takeFlags = intent.flags
//                    takeFlags =
//                        takeFlags and (FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
//                    result?.let { contentResolver.takePersistableUriPermission(it, takeFlags) }
//                } catch (e: SecurityException) {
//                    Log.e("", e.toString())
//                }
//            }
//            imageUri = result
//            realPath = if (Build.VERSION.SDK_INT < 11)
//                RealPathUtil.getRealPathFromURIBelowAPI11(this, result).toString()
//
//            // SDK >= 11 && SDK < 19
//            else if (Build.VERSION.SDK_INT < 19)
//                RealPathUtil.getRealPathFromURIAPI11to18(this, result).toString()
//
//            // SDK > 19 (Android 4.4)
//            else
//                RealPathUtil.getRealPathFromURIAPI19(this, result)
//            binding.imgProfile.setImageURI(result)
//        }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//        binding.apply {
//            imgProfile.setOnClickListener { checkingPermissions()
//                openGallery()}
//        }
//    }
//
//    private fun checkingPermissions() {
//        if (isGranted(
//                this,
//                Manifest.permission.CAMERA,
//                arrayOf(
//                    Manifest.permission.CAMERA,
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.MANAGE_DOCUMENTS
//                ),
//                requestCode,
//            )
//        ) {
//            openGallery()
//        }
//    }
//
//    private fun isGranted(
//        activity: Activity,
//        permission: String,
//        permissions: Array<String>,
//        request: Int
//    ): Boolean {
//        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
//        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
//                showPermissionDeniedDialog()
//            } else {
//                ActivityCompat.requestPermissions(activity, permissions, request)
//            }
//            false
//        } else {
//            true
//        }
//    }
//
//    private fun showPermissionDeniedDialog() {
//        AlertDialog.Builder(this)
//            .setTitle("Permission Denied")
//            .setMessage("Permission is denied, Please allow permissions from App Settings.")
//            .setPositiveButton(
//                "App Settings"
//            ) { _, _ ->
//                val intent = Intent()
//                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                val uri = Uri.fromParts("package", packageName, null)
//                intent.data = uri
//                startActivity(intent)
//            }
//            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
//            .show()
//    }
//
//
//    private fun openGallery() {
//        val intent = Intent()
//        intent.action = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Intent.ACTION_OPEN_DOCUMENT
//        } else {
//            Intent.ACTION_PICK
//        }
//        intent.type = "image/*"
//        intent.addFlags(FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
//        intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION)
//        val chooser = Intent.createChooser(intent, "Choose a Picture")
//        launcherIntentGallery.launch(chooser)
//    }
//
//    private val launcherIntentGallery = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            try {
//                grantUriPermission(
//                    packageName,
//                    result.data?.data,
//                    FLAG_GRANT_READ_URI_PERMISSION or FLAG_GRANT_PERSISTABLE_URI_PERMISSION
//                )
//            } catch (e: IllegalArgumentException) {
//                // on Kitkat api only 0x3 is allowed (FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION)
//                grantUriPermission(
//                    packageName,
//                    result.data?.data,
//                    FLAG_GRANT_READ_URI_PERMISSION
//                )
//            } catch (e: SecurityException) {
//                Log.e("", e.toString())
//            }
//            try {
//                var takeFlags = intent.flags
//                takeFlags =
//                    takeFlags and (FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
//                result.data?.data?.let { contentResolver.takePersistableUriPermission(it, takeFlags) }
//            } catch (e: SecurityException) {
//                Log.e("", e.toString())
//            }
//        }
//        imageUri = result.data?.data
//        realPath = if (Build.VERSION.SDK_INT < 11)
//            RealPathUtil.getRealPathFromURIBelowAPI11(this, result.data?.data).toString()
//
//        // SDK >= 11 && SDK < 19
//        else if (Build.VERSION.SDK_INT < 19)
//            RealPathUtil.getRealPathFromURIAPI11to18(this, result.data?.data).toString()
//
//        // SDK > 19 (Android 4.4)
//        else
//            RealPathUtil.getRealPathFromURIAPI19(this, result.data?.data)
//        binding.imgProfile.setImageURI(result.data?.data)
//        binding.apply {
//            btnSave.setOnClickListener {
//            val name = edtName.text.toString()
//            val city = edtCity.text.toString()
//            val address = edtAddress.text.toString()
//            val tlp = edtTlp.text.toString()
//            if (name.isNotEmpty() && city.isNotEmpty() && address.isNotEmpty() && tlp.isNotEmpty()){
//                result.data?.data?.let { it1 -> uriToFile(it1, this@CompleteAccountActivity) }
//                    ?.let { it2 ->
//                        EditProfileRequest(
//                            name, "tester01@email.com", "123456", 0, "Bantul",
//                            it2
//                        )
//                    }?.let { it3 ->
//                        viewModel.completeAccount(
//                            184,
//                            it3
//                        ).observe(this@CompleteAccountActivity){ response ->
//                            when (response) {
//                                is Resource.Loading -> Toast.makeText(
//                                    this@CompleteAccountActivity,
//                                    "loading",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                                is Resource.Success -> {
//                                    Toast.makeText(
//                                        this@CompleteAccountActivity,
//                                        "success",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//                                is Resource.Error -> {
//                                    Toast.makeText(
//                                        this@CompleteAccountActivity,
//                                        "error ${response.message} ",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                    Log.d("err", "error ${response.message}")
//                                }
//                            }
//                        }
//                    }
//            } else {
//                Toast.makeText(this@CompleteAccountActivity, "Lengkapi semua data", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        }
//    }
//
//
//    private fun uriToFile(selectedImg: Uri, context: Context): File {
//        val contentResolver: ContentResolver = context.contentResolver
//        val myFile = createTempFile(context)
//
//        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
//        val outputStream: OutputStream = FileOutputStream(myFile)
//        val buf = ByteArray(1024)
//        var len: Int
//        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
//        outputStream.close()
//        inputStream.close()
//
//        return myFile
//    }
//
//    private fun createTempFile(context: Context): File {
//        val filenameFilter = "dd-MMM-yyyy"
//        val timeStamp: String = SimpleDateFormat(
//            filenameFilter,
//            Locale.US
//        ).format(System.currentTimeMillis())
//        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        return File.createTempFile(timeStamp, ".jpg", storageDir)
//    }
//
//
//}