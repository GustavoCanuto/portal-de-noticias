import { ErrorHandler, Injectable } from "@angular/core";
import { Router } from "@angular/router";

@Injectable()
export class CustomErrorHandler implements ErrorHandler {

    constructor(private router: Router) { }

    handleError(error: any): void {
        this.router.navigate(['/home']);
    }
}