package com.example.srpexample.tmp;

import com.example.srpexample.models.CReg;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TmpStorage {

    private List<CReg> regs = new ArrayList<>();

    public void save(CReg cReg) {
        regs.add(cReg);
    }

    public CReg findByNick(String nick) {
        return regs.stream().filter(r -> r.getNick().equals(nick)).findFirst().orElseThrow(() -> new RuntimeException(String.format("User %s not found", nick)));
    }
}
