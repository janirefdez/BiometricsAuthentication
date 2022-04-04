# BiometricsAuthentication
[![Kotlin](https://img.shields.io/badge/Kotlin-1.6.10-blue.svg)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android->21-blue.svg)](https://developer.android.com/)
[![Gradle](https://img.shields.io/badge/Gradle-7.2-blue.svg)](https://docs.gradle.org/7.2/userguide/userguide.html)


Basic android example where you can find how to implement Biometrics Prompt

## Requirements
- Android API 21 - 31
- Gradle 7.2
- Kotlin 1.6.10

## Functionalities
There are multiple options to display biometrics prompt. In this example there are 4 options:
- `Unlock with Biometric String`: Fingerprint authentication prompt will appear
<img width="250" alt="image" src="https://user-images.githubusercontent.com/20024632/161520293-872cce3d-2fb7-4eb0-a42c-0a7fa767efb2.png">


- `Unlock with Biometric Weak`: Fingerprint and Face authentication prompt will appear
<div class="align-center">
<img width="250" alt="image" src="https://user-images.githubusercontent.com/20024632/161522557-231164f3-0775-4055-adc9-8d252c624204.png">
<img width="250" alt="image" src="https://user-images.githubusercontent.com/20024632/161521287-8324731f-28c4-4a2a-8cd4-59f93d368ad3.png">
</div>
- `Unlock with Device Credential`: Device credential authentication prompt will appear (pattern or pin code authentication)

<img width="250" alt="image" src="https://user-images.githubusercontent.com/20024632/161522368-e4336a06-702d-4c1e-8385-b59de9f1f45e.png">

- `Unlock with Biometric Weak and Device Credential`: Fingerprint, Face and Device credential authentication prompt will appear
 <img width="250" alt="image" src="https://user-images.githubusercontent.com/20024632/161522864-290b42cd-b38f-4b5c-9052-769a8ce6681d.png">

## Notes
Prompt's description can be modified `setDescription("New Description")`
```
BiometricPrompt.PromptInfo.Builder().apply {
            setTitle(activity.getString(R.string.prompt_info_title))
            setDescription("New Description")
            setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            setConfirmationRequired(false)
        }.build()
```
In this case it has not been modified, so in the screenshots you can see that the description is in another language.
The description is set to the default value and is set to the language of the device.

Same in option `Unlock with Biometric Weak and Device Credential`, the device credential authentication button 
is set based on the device settings (pattern or PIN code) and also set based on the device language.
