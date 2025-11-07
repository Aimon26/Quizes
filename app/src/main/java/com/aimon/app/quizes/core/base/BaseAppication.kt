package com.aimon.app.quizes.core.base // We will fix this package next

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: Application() { // <-- Corrected class name
}
