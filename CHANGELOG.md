# Change Log
#### [0.0.1] - 2017-03-15
* Added:
* Changed:
* Fixed:

#### [0.0.2] - 2021-02-04
* Added: 
  * type conversion for Instant,LocalDate,LocalDateTime in CustomMapper class.
  * languages for customvalidation messages
* Changed:
* Fixed:

#### [0.0.3] - 2021-02-07
* Added:
* Changed:
  * remove graphql dependency and GraphQlSkipNoApiError class
* Fixed:
  * remove @Component from CustomObjectMapper class to avoid graphql error

#### [0.0.4] - 2021-02-09
* Added:
* Changed:
  * makes model classes serializable
* Fixed:

#### [0.0.5] - 2021-02-11
* Added:
* Changed:
  * makes the code more clean
* Fixed:

#### [0.0.6] - 2021-02-22
* Added:
  * add CalendarTools.isToday
* Changed:
* Fixed:

#### [0.0.7] - 2021-03-12
* Added:
  * add @NoArgsConstructor and @AllArgsConstructor to ExceptionDto and ExceptionMessageDto
* Changed:
* Fixed:

#### [0.0.8] - 2021-03-31
* Added:
  * add ExceptionTools,CustomResponseErrorHandler,ExternalCallException
* Changed:
  * improve CustomResponseDto to have exception field beside data
* Fixed:

#### [0.0.9] - 2021-03-31
* Added:
* Changed:
  * improve StringTools by PERSIAN_CHARACTERS_SPACE and PERSIAN_CHARACTERS_SPACE_NUMBERS cases
* Fixed:

#### [0.0.10] - 2021-04-06
* Added:
* Changed:
  * improve PasswordValidator added maxLength and complicatedSpecialChars
* Fixed:

#### [0.0.11] - 2021-04-23
* Added:
* Changed:
* Fixed:
  * fix complicatedSpecialChars problem in PasswordValidator
  
#### [0.0.12] - 2021-05-10
* Added:
* Changed:
  * changed JsonSerializerString and added "USER_MESSAGE."
* Fixed:

#### [0.0.13] - 2021-05-13
* Added:
  * added customdto/search/filter and customdto/search/data
  * added jalaliToGregorianDate and jalaliToGregorianDateTime for Instant
  * added gregorianToJalaliDate and gregorianToJalaliDateTime for Instant
* Changed:
  * changed custommodel/exceptionmodel to custommodel/exception
  * model names changed to dto names
* Fixed:

#### [0.0.14] - 2021-05-15
* Added:
  * added ImageTools.imageResize for desired auto scale resize
* Changed:
  * changed ImageTools.imageResize for desired width and height
* Fixed:
  * fixed PaginationTools.paginateList for empty and null lists

#### [0.0.15] - 2021-05-22
* Added:
  * added ImageTools.mapFromDate and CustomMapper.mapToDate
  * added CustomMapperTest class
* Changed:
* Fixed:
  * fixed ImageTools.getImageRotation for empty orientation in digital pictures
  
#### [0.0.16] - 2021-06-09
* Added:
  * added LocalDate , LocalDateTime conversion to CalendarTools and CustomMapper
* Changed:
  * changed CalendarTools jalali and gregorian conversion method names
* Fixed:  

#### [0.0.17] - 2021-06-13
* Added:
* Changed:
  * improve ExceptionTools.doException for null httpServletResponse parameter
* Fixed:

#### [0.0.18] - 2021-06-16
* Added:
* Changed:
* Fixed:
  * fix ExceptionTools.fixJalaliStringSeperator for half jalali years strings (99 will be replaced by 1399)
  
#### [0.0.19] - 2021-06-19
* Added:
* Changed:
* Fixed:
  * fix PaginationTools.paginateList for request pageRowSize more than initial size problem

#### [0.0.20] - 2021-07-19
* Added:
* Changed:
* Fixed:
  * fix PaginationTools.paginateList for request pageRowSize more than initial size problem
  
#### [0.0.21] - 2021-07-06
* Added:
* Changed:
  * changed ExternalCallException and added more fields about request 
  * changed ExceptionTools.getDtoFromExternalCallException to generalize messages 
* Fixed:  

#### [0.0.22] - 2021-07-08
* Added:
* Changed:
  * improve CalendarTools.fixJalaliString to handle short damaged jalali date strings
* Fixed:

#### [0.0.23] - 2021-07-09
* Added:
* Changed:
* Fixed:
  * fix CustomMapper.mapLocalDateToJalaliString withTime=false

#### [0.0.24] - 2021-07-19
* Added:
  * added NetworkTools.readCurrentRequestInfo
* Changed:
  * changed artifactId to ms-utility
* Fixed:

#### [0.0.25] - 2021-07-31
* Added:
* Changed:
  * changed ExceptionTypeEnum.EXTERNAL_CAL_EXCEPTION to ExceptionTypeEnum.EXTERNAL_CALL_EXCEPTION
* Fixed:

#### [0.0.26] - 2021-08-04
* Added:
  * added logKey to ExceptionDto to find log in Kibana (ELK) or log files
  * added RateLimitException
  * added RateLimitException handling in ExceptionTools
  * added exception_en.properties and exception_fa.properties for exception messages
* Changed:
  * ClientResponseDto generic type is not required to be serialized anymore
* Fixed:

#### [0.0.27] - 2021-08-06
* Added:
* Changed:
* Fixed:
  * fixed RateLimitException translation
  
#### [0.0.28] - 2021-08-22
* Added:
* Changed:
  * changed NetworkTools.readCurrentRequestInfo added withAgentOsDeviceData parameter to retrieve the optional browser, OS and device additional data
* Fixed:

#### [0.0.29] - 2021-09-15
* Added:
  * added @Url validation annotation
* Changed:
* Fixed:

#### [0.0.30] - 2021-09-21
* Added:
* Changed:
  * improve ExceptionTools in translation of business and validation exceptions only start with their keywords
  * improve custom validation override message and min-max control
* Fixed:

#### [0.0.31] - 2021-10-02
* Added:
  * added @LongRange validation annotation
* Changed:
* Fixed:

#### [0.0.32] - 2021-10-06
* Added:
  * enable custom validations to be used in controller method parameters
* Changed:
* Fixed:

#### [0.0.33] - 2021-10-16
* Added:
  * added RestTools.call
* Changed:
* Fixed:

#### [0.0.34] - 2021-10-17
* Added:
* Changed:
* Fixed:
  * fixed default ListLength.max,StringLength.max,DoubleRange.max to max number
  
#### [0.0.35] - 2021-10-19
* Added:
* Changed:
  * changed RestTools.call signature to get RestTemplate as a parameter
* Fixed:

#### [0.0.36] - 2021-10-20
* Added:
* Changed:
  * improve properties
* Fixed:

#### [0.0.37] - 2021-11-06
* Added:
  * added ExcelTools.generateBatch
  * added CalendarTools.getCurrentJalaliLeapYearStatus
  * added CalendarTools.getJalaliFirstDayOfMonth
  * added CalendarTools.getJalaliLastDayOfMonth
* Changed:
* Fixed:

#### [0.0.38] - 2021-11-10
* Added:
* Changed:
  * improve ExcelTools to use functional formatter
* Fixed:

#### [0.0.39] - 2021-11-14
* Added:
  * added FsoTools.fixPathTrailingSlash to fix path in Windows and Linux filesystem
* Changed:
  * improve getting system path from System.getProperty with FsoTools.fixPathTrailingSlash
  * improve ExceptionTools.doException by adding CustomResponseDto.getException().getExternalMessage() for EXTERNAL_CALL_EXCEPTION errors
* Fixed:

#### [0.0.40] - 2021-11-15
* Added:
* Changed:
  * improve ExcelTools.generateBatch to remove its created temp files in os temp directory
* Fixed:

#### [0.0.41] - 2021-11-21
* Added:
  * added SecurityTools.getUntrustedSSLContext to use in RestTemplate and Jsoup SSL config
* Changed:
* Fixed:

#### [0.0.42] - 2021-12-05
* Added:
  * added CustomEnum and JsonSerializerEnum
* Changed:
  * changed JsonSerializerString to only translate strings with a prefix: "USER_MESSAGE."
  * changed CustomObjectMapper add builder and equals hashcode and override copy method
* Fixed:

#### [0.0.43] - 2021-12-07
* Added:
* Changed:
  * removed JsonSerializerNullValue from CustomObjectMapper config
* Fixed:

#### [0.0.44] - 2021-12-08
* Added:
* Changed:
* Fixed:
  * fixed default serialize and deserialize features in CustomObjectMapper
