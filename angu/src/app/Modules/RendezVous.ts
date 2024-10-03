import { Time } from "@angular/common";

export interface RendezVous{
    idRendezVous: number;
    heure: Time;
    date: Date;
    lieu: string;
    statutRendezVous: string;
    idtache: number;
    idclient: number;
    idjardinier: number;
}
