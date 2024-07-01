import { Action } from "rxjs/internal/scheduler/Action";
import { Image } from "./image";
import { Ticket } from "./ticket";
import { Actor } from "./actor";

export class Opera {
    name: string | undefined;
    year: string | undefined;
    description: string | undefined;
    time: string | undefined;
    image: Image | undefined;
    actors: Actor | undefined;
    tickets: Ticket | undefined; 
}
