import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import io.appium.java_client.android.options.signing.KeystoreConfig
import java.net.URI


fun main(args: Array<String>) {
    try {
        val driver = AndroidDriver(URI("http://127.0.0.1:4723").toURL(), generateOption())
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

private fun generateOption() = UiAutomator2Options().apply {
    setAppPackage("arakene.test.package")
    setAppActivity("arakene.test.package.ui.MainActivity")
    setPlatformName("android")
    setAutomationName("UiAutomator2")
    setKeystoreConfig(
        KeystoreConfig(
            "path",
            "keystore password",
            "keyAlias",
            "key password"
        )
    )
}