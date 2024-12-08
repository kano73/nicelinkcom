package com.nicelink.nicer.model.dto;

import com.nicelink.nicer.model.Link;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLinkDTO {
    @NotNull
    @Size(min = 4)
    private String orig_link;

    @NotNull
    private String nice_link;

    private Integer owner_id;

    public String toString() {
        return "PostLinkDTO{" +
                "orig_link='" + orig_link + '\'' +
                ", nice_link='" + nice_link + '\'' +
                ", owner_id=" + owner_id +
                '}';
    }

    public Link toLink(){
        Link link = new Link();
        link.setNice_link(nice_link);
        link.setOrig_link(orig_link);
        link.setOwner_id(owner_id);
        return link;
    }
}
