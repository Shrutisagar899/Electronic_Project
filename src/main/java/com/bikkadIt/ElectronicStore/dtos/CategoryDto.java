package com.bikkadIt.ElectronicStore.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private String catagoryId;
    @NotBlank
    @Min(value = 4, message = "tithle must be of minimum 4 charecher !!")
    private String title;
    @NotBlank(message = "Description required ")
    private String description;

    private String coverImage;
}
