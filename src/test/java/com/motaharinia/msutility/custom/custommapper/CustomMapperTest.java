package com.motaharinia.msutility.custom.custommapper;


import com.motaharinia.msutility.custom.custommapper.sample.AppUser;
import com.motaharinia.msutility.custom.custommapper.sample.AppUserDto;
import com.motaharinia.msutility.custom.custommapper.sample.AppUserMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس تست CustomMapper
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class CustomMapperTest {


    @Autowired
    AppUserMapper appUserMapper;


    /**
     * این متد مقادیر پیش فرض قبل از هر تست این کلاس تست را مقداردهی اولیه میکند
     */
    @BeforeEach
    void initUseCase() {
        Locale.setDefault(new Locale("fa", "IR"));
    }

    /**
     * این متد بعد از هر تست این کلاس اجرا میشود
     */
    @AfterEach
    void finalizeEach() {
        Locale.setDefault(Locale.US);
    }


    @Order(1)
    @Test
    void toDtoTest() {
        try {
            //ایجاد سورس
            AppUser entity = new AppUser();
            entity.setTestDate(new Date());
            entity.setTestInstant(Instant.now());
            entity.setTestLocalDate(LocalDate.now());
            entity.setTestLocalDateTime(LocalDateTime.now());

            //تبدیل
            AppUserDto dto=appUserMapper.toDto(entity);

            //تست عدم وجود خطا
            assertThat(dto).isNotNull();
            assertThat(dto.getTestDate()).isNotNull();
            assertThat(dto.getTestInstant()).isNotNull();
            assertThat(dto.getTestLocalDate()).isNotNull();
            assertThat(dto.getTestLocalDateTime()).isNotNull();

        } catch (Exception ex) {
            fail(ex.toString());
        }
    }


    @Order(2)
    @Test
    void toEntityTest() {
        try {

            Long testEpochMili = Instant.now().toEpochMilli();

            //ایجاد سورس
            AppUserDto dto = new AppUserDto();
            dto.setTestDate(testEpochMili);
            dto.setTestInstant(testEpochMili);
            dto.setTestLocalDate(testEpochMili);
            dto.setTestLocalDateTime(testEpochMili);

            //تبدیل
            AppUser entity=appUserMapper.toEntity(dto);

            //تست عدم وجود خطا
            assertThat(entity).isNotNull();
            assertThat(entity.getTestDate()).isNotNull();
            assertThat(entity.getTestInstant()).isNotNull();
            assertThat(entity.getTestLocalDate()).isNotNull();
            assertThat(entity.getTestLocalDateTime()).isNotNull();

        } catch (Exception ex) {
            fail(ex.toString());
        }
    }
}
