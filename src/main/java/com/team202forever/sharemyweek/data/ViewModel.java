package com.team202forever.sharemyweek.data;

import lombok.Data;
import org.hashids.Hashids;
import org.springframework.data.annotation.Id;

@Data
public abstract class ViewModel {

    @Id
    protected Long id;

    public String getId() {
        if (id == null) {
            return null;
        }
        Hashids hashids = new Hashids("salt");
        return hashids.encode(id);
    }

}
