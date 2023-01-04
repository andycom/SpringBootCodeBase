package com.fancv.login;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@ApiModel("enterDTO")
@Data
public class EnterDTO {

    @NotNull(message = "not empty")
    @Min(1)
    private Long version;
}
