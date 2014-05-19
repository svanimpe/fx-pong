package svanimpe.pong.ui;

import javafx.application.Application;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSDictionary;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIApplicationDelegateAdapter;

public class iOSLauncher extends UIApplicationDelegateAdapter
{
    @Override
    public boolean didFinishLaunching(UIApplication application, NSDictionary launchOptions)
    {
        Thread fxThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Application.launch(Pong.class);
            }
        });
        fxThread.setDaemon(true);
        fxThread.start();
        return true;
    }
    
    public static void main(String... args)
    {
        System.setProperty("glass.platform", "ios");
        System.setProperty("prism.text", "native");
        
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(args, null, iOSLauncher.class);
        pool.drain();
    }
}
