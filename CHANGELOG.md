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
