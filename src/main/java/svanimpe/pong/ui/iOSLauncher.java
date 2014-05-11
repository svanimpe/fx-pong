package svanimpe.pong.ui;

import javafx.application.Application;
import org.robovm.cocoatouch.foundation.NSAutoreleasePool;
import org.robovm.cocoatouch.foundation.NSDictionary;
import org.robovm.cocoatouch.uikit.UIApplication;
import org.robovm.cocoatouch.uikit.UIApplicationDelegate;

public class iOSLauncher extends UIApplicationDelegate.Adapter
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
