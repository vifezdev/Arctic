package lol.pots.core.procedure;

import lombok.Getter;
import lombok.Setter;
import lol.pots.core.grant.Grant;
import lol.pots.core.punishment.Punishment;

import java.util.UUID;

@Getter
@Setter
public class Procedure {

    private ProcedureType procedureType;
    private ProcedureStage procedureStage;
    private UUID target;
    private Grant grant;
    private Punishment punishment;
    private String reason;
    private long duration;

    public Procedure(ProcedureType procedureType, ProcedureStage procedureStage, UUID target, Grant grant, Punishment punishment, String reason, long duration) {
        this.procedureType = procedureType;
        this.procedureStage = procedureStage;
        this.target = target;
        this.grant = grant;
        this.punishment = punishment;
        this.reason = reason;
        this.duration = duration;
    }

}
