#import <Cordova/CDV.h>
#import "ASScreenRecorder.h"

#import "_CordovaPlugin.h"

@implementation _CordovaPlugin
ASScreenRecorder *recorder;
- (void)start:(CDVInvokedUrlCommand *)command{
                
    NSLog(@"in the start");
    
    if(recorder==nil)
    {
    	recorder = [ASScreenRecorder sharedInstance];
    }
    if (!recorder.isRecording) {
    	[recorder startRecording];
         NSLog(@"Start recording");
    }
}

- (void)stop:(CDVInvokedUrlCommand *)command{
              
    NSLog(@"in the stop");
    if(recorder==nil)
    {
    	recorder = [ASScreenRecorder sharedInstance];
    }

    if (recorder.isRecording) {
        [recorder stopRecordingWithCompletion:^{
            NSLog(@"Finished recording");
            CDVPluginResult * result=[CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:recorder.videoURL.absoluteString];
            [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
        }];
    }
}

@end	