import {
  ActivatedRouteSnapshot,
  BaseRouteReuseStrategy,
} from '@angular/router';

export class CustomReuseStrategy extends BaseRouteReuseStrategy {
  public override shouldReuseRoute(
    future: ActivatedRouteSnapshot,
    curr: ActivatedRouteSnapshot
  ): boolean {
    window.scrollTo(0, 0);
    return future.data['reuseComponent'];
  }
}
