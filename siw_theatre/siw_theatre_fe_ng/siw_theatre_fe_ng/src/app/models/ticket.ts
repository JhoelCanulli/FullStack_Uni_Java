import { Client } from "./client";
import { Opera } from "./opera";

export class Ticket {
    price: string | undefined;
    type: string | undefined;
    dateEvent: string | undefined;
    owner: Client | undefined;
    opera: Opera | undefined;
}
