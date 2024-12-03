package com.nicelink.nicer.model.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionDTO {

    @NonNull
    private ZonedDateTime time_stamp;

    @NonNull
    private String link_id;

    private String ip_of_user;
}
