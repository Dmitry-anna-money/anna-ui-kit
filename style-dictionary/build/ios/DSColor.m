
//
// DSColor.m
//

// Do not edit directly
// Generated on Fri, 28 Jan 2022 08:56:42 GMT


#import "DSColor.h"

@implementation DSColor

+ (UIColor *)color:(DSColorName)colorEnum{
  return [[self values] objectAtIndex:colorEnum];
}

+ (NSArray *)values {
  static NSArray* colorArray;
  static dispatch_once_t onceToken;

  dispatch_once(&onceToken, ^{
    colorArray = @[
[UIColor colorWithRed:0.922f green:0.341f blue:0.341f alpha:1.000f],
[UIColor colorWithRed:0.129f green:0.588f blue:0.325f alpha:1.000f],
[UIColor colorWithRed:0.184f green:0.502f blue:0.929f alpha:1.000f]
    ];
  });

  return colorArray;
}

@end
