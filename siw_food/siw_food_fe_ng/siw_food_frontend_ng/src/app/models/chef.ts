import { Recipe } from "./recipe";
import { Role } from "./role";
import { Token } from "./token";

export class Chef {

    id: string | undefined;

    name: string | undefined;

    surname: string | undefined;

    birth: string | undefined;

    email: string | undefined;
    
    username: string | undefined;

    password: string | undefined;
  
    photo: string | undefined;

    role: Role | undefined;
    
    tokens: Token[] | undefined;

    writedRecipes: Recipe[] | undefined;

}
