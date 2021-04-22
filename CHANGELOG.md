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
