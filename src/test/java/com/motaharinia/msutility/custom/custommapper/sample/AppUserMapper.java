package com.motaharinia.msutility.custom.custommapper.sample;

import com.motaharinia.msutility.custom.custommapper.CustomMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper extends CustomMapper {
    AppUserDto toDto(AppUser entity);
    AppUser toEntity(AppUserDto dto);
}
