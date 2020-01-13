package ca.nivtech.demowebsocket.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

public interface DTOConverter<DTO, DOMAIN> {
    DOMAIN toDomain(DTO from);

    DTO toDTO(DOMAIN from);

    default List<DOMAIN> toDomain(List<DTO> list) {
        return list.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    default List<DTO> toDTO(List<DOMAIN> list) {
        return list.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
