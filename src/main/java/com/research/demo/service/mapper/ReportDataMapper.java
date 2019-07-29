package com.research.demo.service.mapper;

import com.research.demo.domain.ReportData;
import com.research.demo.service.dto.PersonDTO;
import com.research.demo.service.dto.ReportDataDTO;
import com.research.demo.service.mapper.util.ConvertDtoToString;
import com.research.demo.service.mapper.util.ConvertStringToDto;
import javax.persistence.Lob;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity ReportData and its DTO ReportDataDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReportDataMapper extends EntityMapper<ReportDataDTO, ReportData> {

    @Mapping(target = "cronInfo", source = "entity.cronInfo", qualifiedBy = {ConvertStringToDto.class})
    ReportDataDTO toDto(ReportData entity);

    @Mapping(target = "cronInfo", source = "dto.cronInfo", qualifiedBy = {ConvertDtoToString.class})
    ReportData toEntity(ReportDataDTO dto);

    default ReportData fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReportData reportData = new ReportData();
        reportData.setId(id);
        return reportData;
    }


    @ConvertStringToDto
    default PersonDTO convertFromStringToDTO( String value) {
        JSONParser parser = new JSONParser();
        PersonDTO personDTO = null;
        try {
             personDTO = (PersonDTO) parser.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        PersonDTO personDTO = new PersonDTO();
//        personDTO.setName("AAAAA" + value);
        return personDTO;
    }

    @ConvertDtoToString
    default String convertDtoToString(PersonDTO personDTO) {
        return personDTO.toString();
    }

}
