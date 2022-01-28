
//
// DSColor.h
//

// Do not edit directly
// Generated on Fri, 28 Jan 2022 08:56:42 GMT


#import <UIKit/UIKit.h>

typedef NS_ENUM(NSInteger, DSColorName) {
ColorRed,
ColorGreen,
ColorBlue
};

@interface DSColor : NSObject
+ (NSArray *)values;
+ (UIColor *)color:(DSColorName)color;
@end
