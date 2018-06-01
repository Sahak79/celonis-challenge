import {Injectable} from '@angular/core';
import {Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import {tokenNotExpired} from 'angular2-jwt';

import {TOKEN_NAME} from '../services/auth.constant';
import {UserService} from '../services/user.service';

@Injectable()
export class AdminAuthGuard implements CanActivate {
  constructor(private router: Router, private userService: UserService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const isAdmin = this.userService.isAdminUser();
    if (isAdmin) {
      return true;
    } else {
      if (tokenNotExpired(TOKEN_NAME, this.userService.accessToken)) {
        this.router.navigate(['user']);
        return true;
      }
      this.router.navigate(['login']);
      return false;
    }
  }
}
