import androidx.compose.ui.window.ComposeUIViewController
import com.therxmv.napoleon.MainScreen
import platform.UIKit.UIViewController

fun mainViewController(): UIViewController = ComposeUIViewController { MainScreen() }
