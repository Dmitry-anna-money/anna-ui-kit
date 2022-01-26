
//
// DSColor.h
//

// Do not edit directly
// Generated on Wed, 26 Jan 2022 16:12:16 GMT


#import <UIKit/UIKit.h>

typedef NS_ENUM(NSInteger, DSColorName) {
ColorColorsMultipleFills0,
ColorColorsMultipleFills1,
ColorColorsSingleBlue,
ColorColorsRefBlue,
ColorColorsSpecialCharacters,
ColorColorsSpecialCharactersNderung,
ColorLightBackground,
ColorDarkBackground
};

@interface DSColor : NSObject
+ (NSArray *)values;
+ (UIColor *)color:(DSColorName)color;
@end
