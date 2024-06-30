import { Image } from "./image";
import { Ticket } from "./ticket";

export class Client {
    name: string | undefined;
    surname: string | undefined;
    phone: string | undefined;
    dateOfBirth: string | undefined;
    image: Image | undefined;
    tickets: Ticket[] | undefined;
}
