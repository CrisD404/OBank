package model;

import entity.ATMTransaction;
import entity.Atm;
import entity.SavingsAccount;
import interfaces.UserI;
import lombok.Data;

import java.util.List;

public @Data class UserModel {
    private Boolean isEmployee;
    private UserI user;
    private List<ATMTransaction> transaction;
    private SavingsAccount account;
    private Atm atm;
}
