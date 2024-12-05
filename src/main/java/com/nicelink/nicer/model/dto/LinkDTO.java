package com.nicelink.nicer.model.dto;

import com.nicelink.nicer.model.Link;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkDTO {

    private Integer id;

    @Size(min = 4)
    private String orig_link;

    @Size(min = 3)
    private String nice_link;

    private Integer owner_id;

    @Size(min = 3)
    private String owner_name;

    public Link convertToLink() {
        Link link = new Link();
        link.setOrig_link(orig_link);
        link.setNice_link(nice_link);
        link.setOwner_id(owner_id);
        return link;
    }

}
