package com.team202forever.sharemyweek.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class ViewModel extends ResourceSupport {

    @Id
    protected HashId hashId = new HashId();

    @Override
    @JsonIgnore
    public List<Link> getLinks() {
        return super.getLinks();
    }


}
