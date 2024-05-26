import { Chef } from "./chef";

export class Token {
    id: string | undefined;

    accessToken: string | undefined;

    refreshToken: string | undefined;

    loggedOut: boolean | undefined;

    chef: Chef | undefined;
}
