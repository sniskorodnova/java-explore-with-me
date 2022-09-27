package ru.practicum.ewm.model.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewStatsDto {
    @NotEmpty
    @NotBlank
    private String app;
    @NotEmpty
    @NotBlank
    private String uri;
    @NotEmpty
    @NotBlank
    private String ip;
}