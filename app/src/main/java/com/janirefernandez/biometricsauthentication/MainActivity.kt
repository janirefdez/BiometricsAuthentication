package com.janirefernandez.biometricsauthentication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.view.isVisible
import com.janirefernandez.biometricsauthentication.databinding.ActivityMainBinding
import com.janirefernandez.biometricsauthentication.utils.BiometricPromptUtils

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!BiometricPromptUtils.canAuthenticateWithBiometrics(this)) {
            binding.buttons.isVisible = false
            binding.unsupportedTv.isVisible = true
        }

        binding.biometricStrongBtn.setOnClickListener {
            showBiometricPrompt(BIOMETRIC_STRONG)
        }
        binding.biometricWeakBtn.setOnClickListener {
            showBiometricPrompt(BIOMETRIC_WEAK)
        }
        binding.deviceCredentialBtn.setOnClickListener {
            showBiometricPrompt(DEVICE_CREDENTIAL)
        }
        binding.biometricWeakDeviceBtn.setOnClickListener {
            showBiometricPrompt(BIOMETRIC_WEAK_DEVICE_CREDENTIAL)
        }

    }

    private fun showBiometricPrompt(option: Int) {
        val canAuthenticate = BiometricManager.from(applicationContext)
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.BIOMETRIC_STRONG)
        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            val biometricPrompt = BiometricPromptUtils.createBiometricPrompt(this) {
                Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show()
            }

            val promptInfo: BiometricPrompt.PromptInfo
            when (option) {
                BIOMETRIC_STRONG -> {
                    promptInfo = BiometricPromptUtils.createBiometricStrongPrompt(this)
                }
                BIOMETRIC_WEAK -> {
                    promptInfo = BiometricPromptUtils.createBiometricWeakPrompt(this)
                }
                DEVICE_CREDENTIAL -> {
                    promptInfo = BiometricPromptUtils.createDeviceCredentialPrompt(this)
                }
                BIOMETRIC_WEAK_DEVICE_CREDENTIAL -> {
                    promptInfo = BiometricPromptUtils.createBiometricWeakDevicePrompt(this)
                }
                else -> {
                    promptInfo = BiometricPromptUtils.createDefaultPrompt(this)
                }

            }
            biometricPrompt.authenticate(promptInfo)
        }
    }


    companion object {
        private const val BIOMETRIC_STRONG = 0
        private const val BIOMETRIC_WEAK = 1
        private const val DEVICE_CREDENTIAL = 2
        private const val BIOMETRIC_WEAK_DEVICE_CREDENTIAL = 3

    }
}