package com.janirefernandez.biometricsauthentication.utils

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.janirefernandez.biometricsauthentication.R

object BiometricPromptUtils {
    private const val TAG = "BiometricPromptUtils"
    fun createBiometricPrompt(
        activity: AppCompatActivity,
        processSuccess: (BiometricPrompt.AuthenticationResult) -> Unit
    ): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(activity)

        val callback = object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errCode, errString)
                Log.d(TAG, "errCode is $errCode and errString is: $errString")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.d(TAG, "Biometric authentication failed for unknown reason.")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Log.d(TAG, "Authentication was successful")
                processSuccess(result)
            }
        }
        return BiometricPrompt(activity, executor, callback)
    }

    fun createDefaultPrompt(activity: AppCompatActivity): BiometricPrompt.PromptInfo =
        BiometricPrompt.PromptInfo.Builder().apply {
            setTitle(activity.getString(R.string.prompt_info_title))
            setConfirmationRequired(false)
            setNegativeButtonText(activity.getString(R.string.cancel))
        }.build()

    fun createBiometricStrongPrompt(activity: AppCompatActivity): BiometricPrompt.PromptInfo =
        BiometricPrompt.PromptInfo.Builder().apply {
            setTitle(activity.getString(R.string.prompt_info_title))
            setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            setConfirmationRequired(false)
            setNegativeButtonText(activity.getString(R.string.cancel))
        }.build()

    fun createBiometricWeakPrompt(activity: AppCompatActivity): BiometricPrompt.PromptInfo =
        BiometricPrompt.PromptInfo.Builder().apply {
            setTitle(activity.getString(R.string.prompt_info_title))
            setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK)
            setConfirmationRequired(false)
            setNegativeButtonText(activity.getString(R.string.cancel))
        }.build()

    fun createDeviceCredentialPrompt(activity: AppCompatActivity): BiometricPrompt.PromptInfo =
        BiometricPrompt.PromptInfo.Builder().apply {
            setTitle(activity.getString(R.string.prompt_info_title))
            setAllowedAuthenticators(BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            setConfirmationRequired(false)
        }.build()

    fun createBiometricWeakDevicePrompt(activity: AppCompatActivity): BiometricPrompt.PromptInfo =
        BiometricPrompt.PromptInfo.Builder().apply {
            setTitle(activity.getString(R.string.prompt_info_title))
            setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            setConfirmationRequired(false)
        }.build()

    fun canAuthenticateWithBiometrics(activity: AppCompatActivity): Boolean {
        val biometricManager = BiometricManager.from(activity)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                return true
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Toast.makeText(
                    activity,
                    activity.getString(R.string.message_no_hardware_available),
                    Toast.LENGTH_LONG
                ).show()
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Toast.makeText(
                    activity,
                    activity.getString(R.string.message_no_hardware_available),
                    Toast.LENGTH_LONG
                ).show()
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Toast.makeText(
                    activity,
                    activity.getString(R.string.message_no_enrolled_biometrics),
                    Toast.LENGTH_LONG
                ).show()
            }
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                Toast.makeText(
                    activity,
                    activity.getString(R.string.message_error_security_update_required),
                    Toast.LENGTH_LONG
                ).show()
            }
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                Toast.makeText(
                    activity,
                    activity.getString(R.string.error_unknown),
                    Toast.LENGTH_LONG
                ).show()
            }
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                Toast.makeText(
                    activity,
                    activity.getString(R.string.error_unknown),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        return false
    }
}