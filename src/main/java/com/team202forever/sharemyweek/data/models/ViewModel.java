package com.team202forever.sharemyweek.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class ViewModel extends ResourceSupport {

    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected HashId hashId = new HashId();

    @Override
    @JsonIgnore
    @Transient
    public List<Link> getLinks() {
        return super.getLinks();
    }


}
