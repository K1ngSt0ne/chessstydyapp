package com.example.chessstudyappver11;

import java.util.List;

public class DebutTopic {
    private List<DebutsDescription> open_debuts;
    private List<DebutsDescription> half_open_debuts;
    private List<DebutsDescription> close_debuts;

    public DebutTopic(List<DebutsDescription> open_debuts, List<DebutsDescription> half_open_debuts, List<DebutsDescription> close_debuts) {
        this.open_debuts = open_debuts;
        this.half_open_debuts = half_open_debuts;
        this.close_debuts = close_debuts;
    }

    public List<DebutsDescription> getOpen_debuts() {
        return open_debuts;
    }

    public void setOpen_debuts(List<DebutsDescription> open_debuts) {
        this.open_debuts = open_debuts;
    }

    public List<DebutsDescription> getHalf_open_debuts() {
        return half_open_debuts;
    }

    public void setHalf_open_debuts(List<DebutsDescription> half_open_debuts) {
        this.half_open_debuts = half_open_debuts;
    }

    public List<DebutsDescription> getClose_debuts() {
        return close_debuts;
    }

    public void setClose_debuts(List<DebutsDescription> close_debuts) {
        this.close_debuts = close_debuts;
    }
}
