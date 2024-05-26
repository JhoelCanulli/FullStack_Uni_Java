import { UnaryFunction } from "rxjs";
import { Chef } from "./chef";

export class Recipe {


    id: string | undefined;

	title: string | undefined;

    description: string | undefined;

    image: string | undefined;

    writer: Chef | undefined;

}
