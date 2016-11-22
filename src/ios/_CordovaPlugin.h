#import <Cordova/CDV.h>

@interface _CordovaPlugin : CDVPlugin
- (void)start:(CDVInvokedUrlCommand *)command;
- (void)stop:(CDVInvokedUrlCommand *)command;
@end