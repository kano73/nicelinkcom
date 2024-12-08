package com.nicelink.nicer.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLinkDTO {
    private Integer id;

    @Size(min = 4)
    private String orig_link_old;

    @Size(min = 4)
    private String orig_link_new;

    @NotNull
    private String nice_link_old;

    @NotNull
    private String nice_link_new;

    private Integer owner_id;

    private String owner_name;

    public Object[] getParams() {
        ArrayList<Object> params = new ArrayList<Object>();

        if(nice_link_new!=null) params.add(nice_link_new);
        if(nice_link_old!=null) params.add(nice_link_old);

        if(orig_link_new!=null) params.add(orig_link_new);
        if(orig_link_old!=null) params.add(orig_link_old);

        if(owner_id!=null) params.add(owner_id);

        if(owner_name!=null) params.add(owner_name);

        if(id!=null) params.add(id);

        return params.toArray();
    }
}
