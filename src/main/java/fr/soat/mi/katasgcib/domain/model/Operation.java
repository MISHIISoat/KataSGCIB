package fr.soat.mi.katasgcib.domain.model;

import java.time.LocalDate;

public record Operation(
        String name,
        LocalDate date,
        Double amount,
        Double balance,
        String accountName
) {

}