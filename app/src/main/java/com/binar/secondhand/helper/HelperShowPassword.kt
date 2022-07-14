package com.binar.secondhand.helper

import android.media.Image
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.binar.secondhand.R
import java.util.zip.Inflater

class HelperShowPassword {

     fun showPassword(show : Boolean, edit : EditText, eye : ImageView){
         if (show){
            edit.transformationMethod = HideReturnsTransformationMethod.getInstance()
            eye.setImageResource(R.drawable.ic_outline_remove_red_eye_24)
         }else{
            edit.transformationMethod = PasswordTransformationMethod.getInstance()
            eye.setImageResource(R.drawable.ic_outline_visibility_off_24)
         }
         edit.setSelection(edit.text.toString().length)
    }

}